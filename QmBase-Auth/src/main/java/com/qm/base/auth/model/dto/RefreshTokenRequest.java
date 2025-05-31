package com.qm.base.auth.model.dto;

import java.io.Serializable;

/**
 * 刷新令牌请求对象
 */
public class RefreshTokenRequest implements Serializable {

    /**
     * refresh token
     */
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}