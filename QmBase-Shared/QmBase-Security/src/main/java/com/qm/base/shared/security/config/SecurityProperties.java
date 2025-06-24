package com.qm.base.shared.security.config;

import com.qm.base.core.auth.config.TokenProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 安全配置属性类，用于绑定配置文件中以 qm.security 为前缀的属性项。
 * 包括 JWT 相关配置项和权限排除路径。
 */
@ConfigurationProperties(prefix = "qm.security")
public class SecurityProperties implements TokenProperties {

    /**
     * 默认排除权限校验的 URL 路径列表。
     */
    public static final List<String> DEFAULT_EXCLUDE_URLS = List.of("/auth/**", "/static/**", "/public/**", "/login/**", "/logout/**");

    /**
     * JWT 加密密钥，对称加密时用于生成和验证 token。
     */
    private String secret;

    /**
     * JWT 签发者标识，一般用于标记 token 的颁发主体。
     */
    private String issuer;

    /**
     * 排除权限校验的 URL 列表，例如登录、注册等开放接口。
     */
    private List<String> excludeUrls;

    @Override
    public String getSecret() {
        return this.secret;
    }

    @Override
    public String getIssuer() {
        return this.issuer;
    }

    public List<String> getExcludeUrls() {
        return excludeUrls != null ? excludeUrls : DEFAULT_EXCLUDE_URLS;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
