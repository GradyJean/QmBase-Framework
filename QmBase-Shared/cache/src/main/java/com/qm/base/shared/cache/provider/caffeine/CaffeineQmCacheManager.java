package com.qm.base.shared.cache.provider.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.qm.base.shared.cache.api.QmCache;
import com.qm.base.shared.cache.api.QmCacheManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * CaffeineQmCacheManager - 提供基于 Caffeine 的本地缓存管理器实现。
 * 按照缓存名构造具备 TTL 的独立 QmCache 实例。
 */
public class CaffeineQmCacheManager implements QmCacheManager {

    private final ConcurrentHashMap<String, QmCache> cacheMap = new ConcurrentHashMap<>();
    private final int defaultTtlSeconds;

    public CaffeineQmCacheManager(int defaultTtlSeconds) {
        this.defaultTtlSeconds = defaultTtlSeconds;
    }

    @Override
    public QmCache getCache(String name) {
        return cacheMap.computeIfAbsent(name, key -> {
            Cache<String, Object> caffeine = Caffeine.newBuilder()
                    .expireAfterWrite(defaultTtlSeconds, TimeUnit.SECONDS)
                    .maximumSize(10000)
                    .build();
            return new CaffeineQmCache(name, caffeine);
        });
    }
}