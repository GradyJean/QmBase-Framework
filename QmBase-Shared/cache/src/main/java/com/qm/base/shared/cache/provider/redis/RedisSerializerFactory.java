package com.qm.base.shared.cache.provider.redis;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisSerializer - Redis 序列化器工厂类，用于提供 key/value 序列化实现。
 */
public class RedisSerializerFactory {

    /**
     * 获取字符串序列化器（适用于 Redis key）。
     */
    public static RedisSerializer<String> string() {
        return new StringRedisSerializer();
    }

    /**
     * 获取 JSON 序列化器（适用于 Redis value）。
     */
    public static RedisSerializer<Object> json() {
        return new GenericJackson2JsonRedisSerializer();
    }
}