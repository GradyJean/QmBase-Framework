package com.qm.base.shared.cache.provider.redis;

import com.qm.base.shared.cache.api.QmCache;
import com.qm.base.shared.cache.core.support.CacheValueUtil;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.function.Supplier;

/**
 * RedisQmCache - 基于 Redis 实现的 QmCache 接口。
 * 支持命名空间隔离、TTL 控制与基础缓存操作。
 */
public class RedisQmCache implements QmCache {

    private final String namespace;
    private final RedisTemplate<String, Object> redisTemplate;
    private final long defaultTtlSeconds;

    public RedisQmCache(String namespace, RedisTemplate<String, Object> redisTemplate, long defaultTtlSeconds) {
        this.namespace = namespace;
        this.redisTemplate = redisTemplate;
        this.defaultTtlSeconds = defaultTtlSeconds;
    }

    private String buildKey(String key) {
        return namespace + "::" + key;
    }

    @Override
    public <T> T get(String key) {
        Object raw = redisTemplate.opsForValue().get(buildKey(key));
        return CacheValueUtil.unwrap(raw);
    }

    @Override
    public <T> T get(String key, Supplier<T> loader) {
        T value = get(key);
        if (value == null) {
            value = loader.get();
            put(key, value, defaultTtlSeconds); // 不再判断 null，由 put 封装
        }
        return value;
    }

    @Override
    public <T> void put(String key, T value, long ttlSeconds) {
        Object toStore = CacheValueUtil.wrap(value);
        if (ttlSeconds <= 0) {
            redisTemplate.opsForValue().set(buildKey(key), toStore);
        } else {
            redisTemplate.opsForValue().set(buildKey(key), toStore, java.time.Duration.ofSeconds(ttlSeconds));
        }
    }

    @Override
    public void evict(String key) {
        redisTemplate.delete(buildKey(key));
    }

    @Override
    public void clear() {
        // 构建前缀匹配模式
        String pattern = namespace + "::" + "*";

        // 使用 RedisConnection 进行底层 scan + delete 操作，避免使用 KEYS 指令
        redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(
                    ScanOptions.scanOptions().match(pattern).count(500).build())) {
                while (cursor.hasNext()) {
                    byte[] key = cursor.next();
                    connection.del(key);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to clear redis cache with namespace: " + namespace, e);
            }
            return null;
        });
    }

    @Override
    public boolean contains(String key) {
        return redisTemplate.hasKey(buildKey(key));
    }

    @Override
    public Object getNative() {
        return redisTemplate;
    }
}