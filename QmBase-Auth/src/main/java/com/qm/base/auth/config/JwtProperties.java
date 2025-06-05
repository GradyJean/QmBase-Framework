package com.qm.base.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT配置属性类。
 * 用于配置密钥、签名算法、Issuer等JWT核心参数。
 * 支持通过HTTP Header或Cookie方式传递Token。
 */
@ConfigurationProperties(prefix = "qm.auth.jwt")
public class JwtProperties {

    /**
     * 签名密钥。
     * 要求长度至少为32字节（256位），用于支持HMAC-SHA256及以上的JWT签名算法。
     * 推荐配置方式：在 application.yml 中设置 qm.auth.jwt.secret，长度不少于32个字符。
     */
    private String secret;

    /**
     * 签发者（Issuer）。
     */
    private String issuer = "QmBase";

    /**
     * Token前缀，一般为 "Bearer "。
     */
    private String tokenPrefix = "Bearer ";

    /**
     * Token有效时间（单位：秒）。
     */
    private long expirationSeconds = 3600;

    /**
     * Token刷新时间（单位：秒），一般小于有效期。
     */
    private long refreshIntervalSeconds = 1800;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }

    public void setExpirationSeconds(long expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }

    public long getRefreshIntervalSeconds() {
        return refreshIntervalSeconds;
    }

    public void setRefreshIntervalSeconds(long refreshIntervalSeconds) {
        this.refreshIntervalSeconds = refreshIntervalSeconds;
    }
}
