package com.qm.base.auth.config;

import com.qm.base.core.auth.config.TokenProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT配置属性类。
 * 用于配置密钥、签名算法、Issuer等JWT核心参数。
 * 支持通过HTTP Header或Cookie方式传递Token。
 */
@ConfigurationProperties(prefix = "qm.auth")
public class AuthProperties implements TokenProperties {

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
     * Token有效时间（单位：秒，默认 30 分钟，即 1800 秒）。
     */
    private long expirationSeconds = 30 * 60;

    /**
     * Token刷新时间（单位：秒，默认 7 天，即 604800 秒）。
     */
    private long refreshIntervalSeconds = 7 * 24 * 60 * 60;

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
