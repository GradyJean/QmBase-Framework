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
     * Token在请求头中的名称。
     */
    private String header = "Authorization";

    /**
     * 是否启用Cookie方式传递Token。
     */
    private boolean enableCookie = false;

    /**
     * 存储Token的Cookie名称。
     */
    private String cookieName = "AuthToken";

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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public boolean isEnableCookie() {
        return enableCookie;
    }

    public void setEnableCookie(boolean enableCookie) {
        this.enableCookie = enableCookie;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    /**
     * Token有效时间（单位：秒）。
     */
    private long expirationSeconds = 3600;

    /**
     * Token刷新时间（单位：秒），一般小于有效期。
     */
    private long refreshIntervalSeconds = 1800;

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
