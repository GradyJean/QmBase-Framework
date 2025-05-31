package com.qm.base.shared.lock.enums;

/**
 * 支持的分布式锁类型
 */
public enum DistributedLockType {

    /**
     * 基于 Redis 的分布式锁
     */
    REDIS,

    /**
     * 基于 Zookeeper 的分布式锁
     */
    ZOOKEEPER
}
