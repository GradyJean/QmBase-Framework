package com.qm.base.auth.model.dto;

/**
 * 登出请求对象
 */
public class LogoutRequest {

    /**
     * accessToken 字符串
     */
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
