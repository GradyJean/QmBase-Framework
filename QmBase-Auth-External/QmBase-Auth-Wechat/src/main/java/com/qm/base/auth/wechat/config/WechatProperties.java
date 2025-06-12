package com.qm.base.auth.wechat.config;

import com.qm.base.core.auth.third.config.LoginProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "qm.auth.wechat")
public class WechatProperties implements LoginProperties {
    /**
     * 是否启用微信登录功能。为 true 时才会加载相关配置并注册 provider。
     */
    private boolean enabled = true;

    /**
     * 微信开放平台分配的应用唯一标识（AppID）。
     */
    private String appId;

    /**
     * 微信开放平台分配的应用密钥（AppSecret）。
     */
    private String appSecret;
    /**
     * 第三方登录完成后的回调地址，微信将在授权成功后重定向到此地址。
     */
    private String redirectUri;

    /**
     * 授权作用域，snsapi_login 用于网页扫码登录，snsapi_userinfo 用于公众号。
     */
    private String scope = "snsapi_login";

    /**
     * 微信扫码登录授权地址，可自定义（一般无需修改）。
     */
    private String authorizeUrl = "https://open.weixin.qq.com/connect/qrconnect";

    /**
     * 用于通过 code 获取 access_token 的接口地址。
     */
    private String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }
}
