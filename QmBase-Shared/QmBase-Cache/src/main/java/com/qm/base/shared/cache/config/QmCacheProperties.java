package com.qm.base.shared.cache.config;

import com.qm.base.shared.cache.enums.CacheType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * QmCacheProperties - 缓存配置属性类。
 * 用于读取 application.yml 中的配置项 qm.base.cache.*
 */
@ConfigurationProperties(prefix = "qm.cache")
public class QmCacheProperties {

    /**
     * 缓存类型（redis / caffeine），默认 redis
     */
    private CacheType type = CacheType.REDIS;

    /**
     * 默认缓存过期时间（单位：秒）
     */
    private int ttl = 300;

    public CacheType getType() {
        return type;
    }

    public void setType(CacheType type) {
        this.type = type;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}