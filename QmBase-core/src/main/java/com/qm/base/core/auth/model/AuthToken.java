package com.qm.base.core.auth.model;

import java.io.Serializable;

/**
 * 用户认证后的 Token 信息封装。
 *
 * @param accessToken  访问令牌
 * @param refreshToken 刷新令牌
 * @param expiration   accessToken 过期时间
 */
public record AuthToken(String accessToken, String refreshToken, Long expiration) implements Serializable {

    public static AuthToken getInstance(String accessToken, String refreshToken, Long expiration) {
        return new AuthToken(accessToken, refreshToken, expiration);
    }

}
