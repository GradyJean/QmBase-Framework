package com.qm.base.payment.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信支付配置属性。
 */
@ConfigurationProperties(prefix = "qm.payment.wechat")
@Data
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
}
