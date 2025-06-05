package com.qm.base.auth.model.dto;

import java.io.Serializable;

/**
 * 登出请求对象
 */
public class TokenRequest implements Serializable {

    /**
     * accessToken 字符串
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
