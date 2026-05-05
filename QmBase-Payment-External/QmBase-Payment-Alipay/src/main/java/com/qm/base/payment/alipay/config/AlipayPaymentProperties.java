package com.qm.base.payment.alipay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 支付宝支付配置属性。
 */
@ConfigurationProperties(prefix = "qm.payment.alipay")
public class AlipayPaymentProperties {

    /**
     * 是否启用支付宝支付 provider。
     */
    private boolean enabled = true;

    /**
     * 支付宝网关地址。
     */
    private String serverUrl = "https://openapi.alipay.com";

    /**
     * 支付宝应用 ID。
     */
    private String appId;

    /**
     * 商户应用私钥文件路径。
     */
    private String privateKeyPath;

    /**
     * 支付宝公钥文件路径。
     */
    private String alipayPublicKeyPath;

    /**
     * 字符集。
     */
    private String charset = "UTF-8";

    /**
     * 签名算法。
     */
    private String signType = "RSA2";

    /**
     * 支付宝支付异步通知地址。
     */
    private String notifyUrl;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getAlipayPublicKeyPath() {
        return alipayPublicKeyPath;
    }

    public void setAlipayPublicKeyPath(String alipayPublicKeyPath) {
        this.alipayPublicKeyPath = alipayPublicKeyPath;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
