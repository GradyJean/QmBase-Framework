package com.qm.base.shared.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT 配置属性类，支持从 application.yml 中读取
 */
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    /**
     * HTTP 请求头名称，默认为 Authorization
     */
    private String header = "Authorization";

    /**
     * Token 前缀，默认为 Bearer
     */
    private String prefix = "Bearer ";

    /**
     * 签名密钥
     */
    private String secret;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}