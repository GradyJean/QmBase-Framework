package com.qm.base.shared.lock.distribute.redis;

import com.qm.base.shared.lock.distribute.core.DistributedLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 基于 Redis 的分布式锁实现类
 * 使用 SET NX PX 实现加锁，Lua 脚本实现解锁的原子性
 */
@RequiredArgsConstructor
public class RedisDistributedLockService implements DistributedLockService {

    private final StringRedisTemplate redisTemplate;

    // 解锁脚本：只有当 value 匹配时才允许删除，防止误删他人锁
    // 说明：该脚本适用于 Redis 单节点或主从同步保证一致性的场景。
    // 若 Redis 存在强制 failover 或 proxy，可能需替换为 Redisson 等更强一致性的方案。
    private static final String UNLOCK_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] " +
                    "then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 尝试加锁
     * 采用 Redis 的 SET NX PX 命令实现，注意：在网络分区等极端情况下，可能存在误判锁成功的问题。
     */
    @Override
    public boolean tryLock(String key, String value, long expireMillis) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForValue().setIfAbsent(key, value, expireMillis, TimeUnit.MILLISECONDS)
        );
    }

    @Override
    public boolean tryLock(String key, String value, long expireMillis, long waitMillis) {
        long start = System.currentTimeMillis();
        do {
            if (tryLock(key, value, expireMillis)) {
                return true;
            }
            try {
                // 自旋等待是固定 50ms 间隔，当前不可配置。若需优化性能，可考虑支持 backoff 策略或将间隔设为可配置。
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        } while ((System.currentTimeMillis() - start) < waitMillis);
        return false;
    }

    @Override
    public boolean releaseLock(String key, String value) {
        Long result = redisTemplate.execute(
                new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class),
                Collections.singletonList(key),
                value
        );
        // 返回 0 代表未命中锁 key 或 value 不一致，并非异常，使用者需理解其含义
        return result == 1;
    }

    @Override
    public void execute(String key, Runnable task, long expireMillis) {
        execute(key, UUID.randomUUID().toString(), () -> {
            task.run();
            return null;
        }, expireMillis, 0);
    }

    @Override
    public <T> T execute(String key, Supplier<T> task, long expireMillis) {
        return execute(key, UUID.randomUUID().toString(), task, expireMillis, 0);
    }

    @Override
    public <T> T execute(String key, String value, Supplier<T> task, long expireMillis, long waitMillis) {
        if (!tryLock(key, value, expireMillis, waitMillis)) {
            // 可考虑允许返回 null 或 fallback 行为而非直接抛出异常，提升对高并发业务的容错性
            throw new IllegalStateException("Failed to acquire Redis lock for key: " + key);
        }
        try {
            return task.get();
        } finally {
            releaseLock(key, value);
        }
    }
}