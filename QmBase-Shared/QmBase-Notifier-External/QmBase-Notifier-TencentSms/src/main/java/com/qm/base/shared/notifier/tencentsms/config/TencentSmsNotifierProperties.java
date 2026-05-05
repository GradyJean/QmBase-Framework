package com.qm.base.shared.notifier.tencentsms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯云短信通知配置。
 */
@ConfigurationProperties(prefix = "qm.notifier.tencent-sms")
public class TencentSmsNotifierProperties {

    /**
     * 是否启用腾讯云短信 provider。
     */
    private boolean enabled = true;

    /**
     * provider 编码。
     */
    private String provider = "tencent-sms";

    /**
     * SecretId。
     */
    private String secretId;

    /**
     * SecretKey。
     */
    private String secretKey;

    /**
     * 地域。
     */
    private String region = "";

    /**
     * 接口地址。
     */
    private String endpoint = "sms.tencentcloudapi.com";

    /**
     * 短信应用 ID。
     */
    private String smsSdkAppId;

    /**
     * 短信签名。
     */
    private String signName;

    /**
     * 业务标识到模板 ID 的映射。
     */
    private Map<String, String> templateIds = new HashMap<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getSmsSdkAppId() {
        return smsSdkAppId;
    }

    public void setSmsSdkAppId(String smsSdkAppId) {
        this.smsSdkAppId = smsSdkAppId;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Map<String, String> getTemplateIds() {
        return templateIds;
    }

    public void setTemplateIds(Map<String, String> templateIds) {
        this.templateIds = templateIds;
    }
}
