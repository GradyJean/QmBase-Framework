package com.qm.base.shared.cache.provider.caffeine;

/**
 * TimedValue - 包装缓存值与其对应 TTL（秒）的容器类。
 * 用于配合 Caffeine 的 Expiry 接口实现 per-entry TTL 支持。
 * 特殊规则：
 * - ttlSeconds == -1 表示永久缓存
 * - ttlSeconds == 0 表示立即过期（不推荐）
 *
 * @param <T> 缓存值类型
 */
public class TimedValue<T> {

    private final T value;
    private final long ttlSeconds;

    public TimedValue(T value, long ttlSeconds) {
        if (ttlSeconds < -1) {
            throw new IllegalArgumentException("ttlSeconds must be >= -1");
        }
        this.value = value;
        this.ttlSeconds = ttlSeconds;
    }

    public T getValue() {
        return value;
    }

    public long getTtlSeconds() {
        return ttlSeconds;
    }
}