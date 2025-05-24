package com.qm.base.shared.lock.enums;

/**
 * 锁类型枚举
 */
public enum LockType {
    /**
     * 本地线程级锁，单机适用
     */
    LOCAL,
    /**
     * 用于 Redis/Zookeeper 等分布式实现
     */
    DISTRIBUTED
}