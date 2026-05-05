package com.qm.base.shared.notifier.exmail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 腾讯企业邮箱通知配置。
 */
@ConfigurationProperties(prefix = "qm.notifier.exmail")
public class ExmailNotifierProperties {

    /**
     * 是否启用腾讯企业邮箱 provider。
     */
    private boolean enabled = true;

    /**
     * provider 编码。
     */
    private String provider = "exmail";

    /**
     * SMTP 主机。
     */
    private String host = "smtp.exmail.qq.com";

    /**
     * SMTP 端口。
     */
    private Integer port = 465;

    /**
     * 发件邮箱账号。
     */
    private String username;

    /**
     * 发件邮箱密码或客户端专用密码。
     */
    private String password;

    /**
     * 发件人地址。
     */
    private String from;

    /**
     * 是否启用 SSL。
     */
    private boolean ssl = true;

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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }
}
