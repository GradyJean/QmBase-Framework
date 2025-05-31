package com.qm.base.shared.cache.enums;

/**
 * 缓存类型枚举：
 * - REDIS：基于 Redis 的远程缓存
 * - CAFFEINE：基于 Caffeine 的本地内存缓存
 * <p>
 * 可通过配置项 qm.base.cache.type 选择使用哪种缓存实现
 */
public enum CacheType {
    /**
     * 基于 Redis 的远程缓存
     */
    REDIS,
    /**
     * 基于 Caffeine 的本地内存缓存
     */
    CAFFEINE
}