package com.qm.base.shared.lock.distribute.core;

import java.util.function.Supplier;

public class EmptyDistributedLockService implements DistributedLockService {
    @Override
    public boolean tryLock(String key, String value, long expireMillis) {
        throw new IllegalArgumentException("Not has distributedLock config");
    }

    @Override
    public boolean tryLock(String key, String value, long expireMillis, long waitMillis) {
        throw new IllegalArgumentException("Not has distributedLock config");
    }

    @Override
    public boolean releaseLock(String key, String value) {
        throw new IllegalArgumentException("Not has distributedLock config");
    }

    @Override
    public void execute(String key, Runnable task, long expireMillis) {
        throw new IllegalArgumentException("Not has distributedLock config");
    }

    @Override
    public <T> T execute(String key, Supplier<T> task, long expireMillis) {
        throw new IllegalArgumentException("Not has distributedLock config");
    }

    @Override
    public <T> T execute(String key, String value, Supplier<T> task, long expireMillis, long waitMillis) {
        throw new IllegalArgumentException("Not has distributedLock config");
    }
}
