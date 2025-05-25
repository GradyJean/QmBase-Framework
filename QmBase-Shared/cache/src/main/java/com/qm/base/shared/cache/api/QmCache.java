package com.qm.base.shared.cache.api;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * QmCache 缓存接口，定义统一缓存操作抽象。
 * 支持泛型、TTL控制、批量操作和底层访问。
 */
public interface QmCache {

    /**
     * 根据 key 获取缓存内容。
     *
     * @param key 缓存 key
     * @return 缓存值，可能为 null
     */
    <T> T get(String key);

    /**
     * 获取缓存值，若不存在则使用 loader 生成并写入缓存。
     *
     * @param key    缓存 key
     * @param loader 数据加载逻辑
     * @return 缓存值或 loader 返回值
     */
    <T> T get(String key, Supplier<T> loader);

    /**
     * 写入缓存，带过期时间（单位：秒）。
     *
     * @param key        缓存 key
     * @param value      值
     * @param ttlSeconds 有效期，单位秒
     */
    <T> void put(String key, T value, int ttlSeconds);

    /**
     * 删除指定缓存。
     *
     * @param key 缓存 key
     */
    void evict(String key);

    /**
     * 清空当前缓存命名空间。
     */
    void clear();

    /**
     * 判断缓存中是否存在该 key。
     *
     * @param key 缓存 key
     * @return true 表示命中，false 表示未命中
     */
    boolean contains(String key);

    /**
     * 返回底层原始缓存对象（如 RedisTemplate、Caffeine Cache 等）。
     *
     * @return native 实例
     */
    Object getNative();
}