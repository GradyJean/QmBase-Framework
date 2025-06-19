package com.qm.base.shared.cache.provider.redis;

import com.qm.base.shared.cache.api.QmCache;
import com.qm.base.shared.cache.api.QmCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisQmCacheManager - 提供基于 Redis 的 QmCache 实例管理。
 * 每个 cacheName 构造一个带命名空间的 RedisQmCache 实例。
 */
public class RedisQmCacheManager implements QmCacheManager {

    private final RedisTemplate<String, Object> redisTemplate;
    private final long defaultTtlSeconds;

    public RedisQmCacheManager(RedisTemplate<String, Object> redisTemplate, long defaultTtlSeconds) {
        this.redisTemplate = redisTemplate;
        this.defaultTtlSeconds = defaultTtlSeconds;
    }

    @Override
    public QmCache getCache(String name) {
        return new RedisQmCache(name, redisTemplate, defaultTtlSeconds);
    }
}