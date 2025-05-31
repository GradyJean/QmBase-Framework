package com.qm.base.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 认证配置属性类。
 * 用于配置Token相关参数，如过期时间、刷新时间、是否启用缓存等。
 */
@ConfigurationProperties(prefix = "qm.auth")
public class AuthProperties {

    /**
     * 是否启用 Token 缓存。
     * 默认关闭（无状态认证）。
     */
    private boolean cacheEnabled = false;

    /**
     * Token 过期时间（秒）。
     * 默认2小时。
     */
    private long tokenExpireSeconds = 7200L;

    /**
     * RefreshToken 过期时间（秒）。
     * 默认7天。
     */
    private long refreshTokenExpireSeconds = 604800L;

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public long getTokenExpireSeconds() {
        return tokenExpireSeconds;
    }

    public void setTokenExpireSeconds(long tokenExpireSeconds) {
        this.tokenExpireSeconds = tokenExpireSeconds;
    }

    public long getRefreshTokenExpireSeconds() {
        return refreshTokenExpireSeconds;
    }

    public void setRefreshTokenExpireSeconds(long refreshTokenExpireSeconds) {
        this.refreshTokenExpireSeconds = refreshTokenExpireSeconds;
    }
}
