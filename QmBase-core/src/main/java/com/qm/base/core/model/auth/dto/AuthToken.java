package com.qm.base.core.model.auth.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户认证后的 Token 信息封装。
 */
public class AuthToken implements Serializable {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * accessToken 过期时间
     */
    private Long expiration;

    public AuthToken() {
    }

    public AuthToken(String accessToken, String refreshToken, Long expiration) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }

    public static AuthToken of(String accessToken, String refreshToken, Long expiration) {
        AuthToken token = new AuthToken();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setExpiration(expiration);
        return token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}
