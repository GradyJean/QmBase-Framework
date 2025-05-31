package com.qm.base.shared.cache.api;

/**
 * QmCacheManager 统一缓存管理器接口。
 * 根据缓存名获取对应的 QmCache 实例。
 */
public interface QmCacheManager {

    /**
     * 根据缓存名获取对应的 QmCache 实例。
     *
     * @param name 缓存名称
     * @return QmCache 实例
     */
    QmCache getCache(String name);
}