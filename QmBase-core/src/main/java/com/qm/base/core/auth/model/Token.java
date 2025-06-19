package com.qm.base.core.auth.model;

import java.io.Serializable;
import java.time.Instant;

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

    /**
     * 判断当前 Token 是否已过期
     *
     * @return true 如果当前时间晚于 expireAt
     */
    public boolean hasExpired() {
        return expireAt != null && System.currentTimeMillis() > expireAt;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", expireAt=" + expireAt +
                '}';
    }
}
