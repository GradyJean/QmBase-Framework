package com.qm.base.auth.model.request;

/**
 * 登录请求对象，封装多种登录方式的通用参数结构。
 * 可支持用户名/手机号/邮箱/第三方平台标识等作为登录入口。
 */
public class LoginRequest extends CredentialRequest {
    /**
     * 设备 ID 用于区分登录环境
     */
    private String deviceId;
    /**
     * 是否为验证码登录（如短信验证码或邮箱验证码），默认为 false。
     */
    private boolean verifyCodeLogin = false;

    public boolean isVerifyCodeLogin() {
        return verifyCodeLogin;
    }

    public void setVerifyCodeLogin(boolean verifyCodeLogin) {
        this.verifyCodeLogin = verifyCodeLogin;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
