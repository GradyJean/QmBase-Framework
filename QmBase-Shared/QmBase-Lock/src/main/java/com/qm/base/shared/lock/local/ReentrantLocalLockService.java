package com.qm.base.shared.lock.local;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * 本地锁实现类，基于 JDK ReentrantLock 实现
 * 支持 tryLock + 自动释放机制
 * 支持执行体封装（带或不带返回值）
 */
public class ReentrantLocalLockService implements LocalLockService {

    /**
     * 本地锁容器，按 key 缓存锁对象
     * 建议：key 应规范使用，推荐添加业务前缀，避免锁冲突。
     * 注意：当前未实现锁的清理策略，可能导致锁对象长期积压。
     */
    private final Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    /**
     * 异步定时任务线程池，用于定时释放过期锁
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public boolean tryLock(String key, String value, long expireMillis) {
        return tryLock(key, value, expireMillis, 0);
    }

    @Override
    public boolean tryLock(String key, String value, long expireMillis, long waitMillis) {
        ReentrantLock lock = lockMap.computeIfAbsent(key, k -> new ReentrantLock());
        boolean acquired;
        try {
            acquired = lock.tryLock(waitMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        // 加锁成功后开启定时释放任务（避免死锁）
        // 注意：此处释放锁是基于时间的定时任务，若任务未完成则可能提前释放，
        // 这是非强制超时（非强一致）机制，使用时请注意。
        if (acquired && expireMillis > 0) {
            scheduler.schedule(() -> releaseLock(key, value), expireMillis, TimeUnit.MILLISECONDS);
        }
        return acquired;
    }

    /**
     * 释放锁，只有持有该锁的当前线程才能释放，其他线程调用会失败。
     * 建议：如果调用线程与持锁线程不一致，可以考虑记录警告或抛出异常以便排查。
     */
    @Override
    public boolean releaseLock(String key, String value) {
        ReentrantLock lock = lockMap.get(key);
        if (lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
            return true;
        }
        return false;
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
            // 建议：此处抛出异常，业务端应捕获避免直接崩溃；
            // 也可考虑改为返回 null 或包装异常，根据业务需求调整。
            throw new IllegalStateException("Failed to acquire local lock for key: " + key);
        }
        try {
            return task.get();
        } finally {
            releaseLock(key, value);
        }
    }
}