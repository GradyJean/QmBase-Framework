package com.qm.base.shared.lock.distribute.zookeeper;

import com.qm.base.shared.lock.distribute.DistributedLockService;
import lombok.RequiredArgsConstructor;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 基于 Zookeeper + Curator 的分布式锁实现
 * <p>
 * 注意：
 * - 锁的路径前缀为 "/lock/" + key，避免与其他 ZooKeeper 应用路径冲突。
 * - tryLock 方法中，waitMillis 为 0 时表示非阻塞获取锁。
 * - releaseLock 方法中，isAcquiredInThisProcess() 仅检查当前 JVM 是否持有锁，可能存在误判。
 */
@RequiredArgsConstructor
public class ZookeeperDistributedLockService implements DistributedLockService {

    private final CuratorFramework client;

    @Override
    public boolean tryLock(String key, String value, long expireMillis) {
        return tryLock(key, value, expireMillis, 0);
    }

    @Override
    public boolean tryLock(String key, String value, long expireMillis, long waitMillis) {
        InterProcessMutex mutex = new InterProcessMutex(client, "/lock/" + key);
        try {
            // waitMillis 为 0 表示非阻塞
            return mutex.acquire(waitMillis, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to acquire Zookeeper lock", e);
        }
    }

    @Override
    public boolean releaseLock(String key, String value) {
        InterProcessMutex mutex = new InterProcessMutex(client, "/lock/" + key);
        try {
            // 注意：isAcquiredInThisProcess() 仅检查当前 JVM 是否持有锁，可能误判
            if (mutex.isAcquiredInThisProcess()) {
                mutex.release();
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to release Zookeeper lock", e);
        }
        return false;
    }

    @Override
    public void execute(String key, Runnable task, long expireMillis) {
        execute(key, "", () -> {
            task.run();
            return null;
        }, expireMillis, 0);
    }

    @Override
    public <T> T execute(String key, Supplier<T> task, long expireMillis) {
        return execute(key, "", task, expireMillis, 0);
    }

    @Override
    public <T> T execute(String key, String value, Supplier<T> task, long expireMillis, long waitMillis) {
        InterProcessMutex mutex = new InterProcessMutex(client, "/lock/" + key);
        boolean locked = false;
        try {
            locked = mutex.acquire(waitMillis, TimeUnit.MILLISECONDS);
            if (!locked) {
                throw new IllegalStateException("Failed to acquire Zookeeper lock for key: " + key);
            }
            // 若 task 抛出异常，将进入 finally，考虑是否记录异常或降级处理
            return task.get();
        } catch (Exception e) {
            throw new RuntimeException("Error during Zookeeper lock execution", e);
        } finally {
            if (locked) {
                try {
                    mutex.release();
                } catch (Exception e) {
                    // 建议记录释放失败的 key，避免日志丢失 debug 线索
                    throw new RuntimeException("Failed to release Zookeeper lock for key: " + key, e);
                }
            }
        }
    }
}