package com.qm.base.payment.wechat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信支付配置属性。
 */
@ConfigurationProperties(prefix = "qm.payment.wechat")
public class WechatPaymentProperties {

    /**
     * 是否启用微信支付 provider。
     */
    private boolean enabled = true;

    /**
     * 微信应用 ID。
     */
    private String appId;

    /**
     * 微信商户号。
     */
    private String merchantId;

    /**
     * 商户证书序列号。
     */
    private String merchantSerialNumber;

    /**
     * 商户 API 私钥文件路径。
     */
    private String privateKeyPath;

    /**
     * APIv3 密钥。
     */
    private String apiV3Key;

    /**
     * 微信支付异步通知地址。
     */
    private String notifyUrl;

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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantSerialNumber() {
        return merchantSerialNumber;
    }

    public void setMerchantSerialNumber(String merchantSerialNumber) {
        this.merchantSerialNumber = merchantSerialNumber;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getApiV3Key() {
        return apiV3Key;
    }

    public void setApiV3Key(String apiV3Key) {
        this.apiV3Key = apiV3Key;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
