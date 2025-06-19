package com.qm.base.shared.cache.provider.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.qm.base.shared.cache.api.QmCache;
import com.qm.base.shared.cache.api.QmCacheManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * CaffeineQmCacheManager - 提供基于 Caffeine 的本地缓存管理器实现。
 * 按照缓存名构造具备 TTL 的独立 QmCache 实例。
 */
public class CaffeineQmCacheManager implements QmCacheManager {

    private static final int DEFAULT_MAX_SIZE = 10000;

    private final ConcurrentHashMap<String, QmCache> cacheMap = new ConcurrentHashMap<>();
    private final int defaultTtlSeconds;

    public CaffeineQmCacheManager(int defaultTtlSeconds) {
        this.defaultTtlSeconds = defaultTtlSeconds;
    }

    /**
     * 自定义 Expiry 策略：支持 TimedValue 的 per-entry TTL 管理。
     * 非 TimedValue 实例将使用 defaultTtlSeconds 作为 fallback。
     */
    private record TimedValueExpiry(int defaultTtlSeconds) implements Expiry<String, Object> {

        @Override
        public long expireAfterCreate(String key, Object value, long currentTime) {
            if (value instanceof TimedValue<?> timed) {
                long ttl = timed.getTtlSeconds();
                return ttl == -1 ? Long.MAX_VALUE : TimeUnit.SECONDS.toNanos(ttl);
            }
            return TimeUnit.SECONDS.toNanos(defaultTtlSeconds); // fallback
        }

        @Override
        public long expireAfterUpdate(String key, Object value, long currentTime, long currentDuration) {
            return expireAfterCreate(key, value, currentTime);
        }

        @Override
        public long expireAfterRead(String key, Object value, long currentTime, long currentDuration) {
            return currentDuration;
        }
    }

    @Override
    public QmCache getCache(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Cache name must not be blank");
        }
        // 注意：缓存值应为 TimedValue 实例以支持自定义 TTL 管理
        return cacheMap.computeIfAbsent(name, key -> {
            Cache<String, Object> caffeine = Caffeine.newBuilder()
                    .maximumSize(DEFAULT_MAX_SIZE)
                    .expireAfter(new TimedValueExpiry(defaultTtlSeconds))
                    .build();
            return new CaffeineQmCache(name, caffeine);
        });
    }
}