package com.qm.base.core.auth.model;

import java.io.Serializable;

/**
 * 用户认证后的 Token 信息封装。
 */
public class AuthToken implements Serializable {

    /**
     * 访问令牌
     */
    private final String accessToken;

    /**
     * 刷新令牌
     */
    private final String refreshToken;

    /**
     * accessToken 过期时间
     */
    private final Long expiration;

    private AuthToken(String accessToken, String refreshToken, Long expiration) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }

    public static AuthToken getInstance(String accessToken, String refreshToken, Long expiration) {
        return new AuthToken(accessToken, refreshToken, expiration);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getExpiration() {
        return expiration;
    }

}
