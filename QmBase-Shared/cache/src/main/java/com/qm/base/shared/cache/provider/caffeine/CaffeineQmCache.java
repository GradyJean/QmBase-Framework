package com.qm.base.shared.cache.provider.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.qm.base.shared.cache.api.QmCache;
import com.qm.base.shared.cache.core.support.CacheValueUtil;


import java.util.function.Supplier;

/**
 * CaffeineQmCache - 基于本地 Caffeine 实现的 QmCache。
 */
public class CaffeineQmCache implements QmCache {

    private final String namespace;
    private final Cache<String, Object> cache;

    public CaffeineQmCache(String namespace, Cache<String, Object> cache) {
        this.namespace = namespace;
        this.cache = cache;
    }

    private String buildKey(String key) {
        return namespace + "::" + key;
    }

    @Override
    public <T> T get(String key) {
        Object obj = cache.getIfPresent(buildKey(key));
        if (obj instanceof TimedValue<?> timed) {
            return CacheValueUtil.unwrap(timed.getValue());
        }
        return CacheValueUtil.unwrap(obj);
    }

    @Override
    public <T> T get(String key, Supplier<T> loader) {
        Object obj = cache.get(buildKey(key), k -> new TimedValue<>(CacheValueUtil.wrap(loader.get()), -1));
        if (obj instanceof TimedValue<?> timed) {
            return CacheValueUtil.unwrap(timed.getValue());
        }
        return CacheValueUtil.unwrap(obj);
    }

    @Override
    public <T> void put(String key, T value, int ttlSeconds) {
        cache.put(buildKey(key), new TimedValue<>(CacheValueUtil.wrap(value), ttlSeconds));
    }

    @Override
    public void evict(String key) {
        cache.invalidate(buildKey(key));
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }

    @Override
    public boolean contains(String key) {
        return cache.getIfPresent(buildKey(key)) != null;
    }

    @Override
    public Object getNative() {
        return cache;
    }
}