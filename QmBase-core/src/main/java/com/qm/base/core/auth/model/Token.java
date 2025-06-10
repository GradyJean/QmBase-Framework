package com.qm.base.core.auth.model;

import java.io.Serializable;

/**
 * 通用 Token 数据结构，用于封装 accessToken 或 refreshToken。
 */
public class Token implements Serializable {

    /**
     * token 字符串内容
     */
    private String token;

    /**
     * token 过期时间（毫秒时间戳，Unix epoch millis）
     */
    private Long expireAt;

    private Token() {
    }

    public Token(String token, Long expireAt) {
        this.token = token;
        this.expireAt = expireAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", expireAt=" + expireAt +
                '}';
    }
}
