package com.qm.base.shared.cache.provider.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.qm.base.shared.cache.api.QmCache;

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
        return (T) cache.getIfPresent(buildKey(key));
    }

    @Override
    public <T> T get(String key, Supplier<T> loader) {
        return (T) cache.get(buildKey(key), k -> loader.get());
    }

    @Override
    public <T> void put(String key, T value, int ttlSeconds) {
        cache.put(buildKey(key), value); // TTL 由 cache 初始化时统一配置
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