package com.qm.base.shared.security.config;

import com.qm.base.core.auth.config.TokenProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 安全配置属性类，用于读取并封装应用中的安全相关配置项。
 * 这些配置可通过 application.yml 或 application.properties 文件中的 qm.security 前缀进行设置。
 * <p>
 * 主要配置项说明：
 * - tokenHeader：HTTP 请求中传递 Token 的请求头名称，默认为 Authorization。
 * - tokenPrefix：Token 前缀，通常为 "Bearer "，用于与实际 Token 拼接。
 * - ignoredPaths：不参与安全拦截的 URL 路径列表，支持通配符，用于配置放行的静态资源或开放接口。
 * - secret：JWT 加密使用的密钥字符串，需妥善保管，避免泄露。
 * - issuer：签发 JWT 的机构标识，用于 Token 校验时验证合法性。
 * <p>
 * 本类实现了 TokenProperties 接口，可供 Token 处理逻辑直接引用配置。
 */
@ConfigurationProperties(prefix = "qm.security")
public class SecurityProperties implements TokenProperties {

    /**
     * HTTP 请求中传递 Token 的请求头名称，默认为 Authorization。
     */
    private String tokenHeader = "Authorization";

    /**
     * Token 前缀，通常为 "Bearer "，用于与实际 Token 拼接。
     */
    private String tokenPrefix = "Bearer ";

    /**
     * 不参与安全拦截的 URL 路径列表，支持通配符，用于配置放行的静态资源或开放接口。
     */
    private List<String> ignoredPaths;

    /**
     * JWT 加密使用的密钥字符串，需妥善保管。
     */
    private String secret;

    /**
     * 签发 JWT 的机构标识。
     */
    private String issuer;

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public List<String> getIgnoredPaths() {
        return ignoredPaths;
    }

    public void setIgnoredPaths(List<String> ignoredPaths) {
        this.ignoredPaths = ignoredPaths;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String getSecret() {
        return this.secret;
    }

    @Override
    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
