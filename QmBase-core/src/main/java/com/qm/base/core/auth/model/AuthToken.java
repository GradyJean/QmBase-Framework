package com.qm.base.core.auth.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户认证后的 Token 信息封装。
 * 封装 accessToken 与 refreshToken 的 token 值和过期时间。
 */
public class AuthToken implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌结构
     */
    private Token accessToken;

    /**
     * 刷新令牌结构
     */
    private Token refreshToken;

    public AuthToken() {
    }

    public AuthToken(Token accessToken, Token refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Token getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(Token accessToken) {
        this.accessToken = accessToken;
    }

    public Token getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(Token refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "accessToken=" + accessToken +
                ", refreshToken=" + refreshToken +
                '}';
    }
}
