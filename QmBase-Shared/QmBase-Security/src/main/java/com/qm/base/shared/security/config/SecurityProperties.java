package com.qm.base.shared.security.config;

import com.qm.base.core.auth.config.TokenProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 安全配置属性类，用于绑定配置文件中以 qm.security 为前缀的属性项。
 * 包括 JWT 相关配置项、权限排除路径，以及完全跳过所有安全机制的路径配置。
 */
@ConfigurationProperties(prefix = "qm.security")
public class SecurityProperties implements TokenProperties {

    /**
     * 默认排除权限校验的 URL 路径列表。
     */
    public static final List<String> DEFAULT_EXCLUDE_PERMISSION_URLS = List.of("/auth/**");

    /**
     * 默认跳过所有安全拦截器的 URL 路径列表（包括上下文和权限等）。
     */
    public static final List<String> DEFAULT_EXCLUDE_ALL_URLS = List.of("/static/**", "/public/**", "/favicon.ico");

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
    private List<String> excludePermissionUrls;

    /**
     * 跳过所有安全机制的 URL 列表，例如静态资源、健康检查等。
     */
    private List<String> excludeAllUrls;

    @Override
    public String getSecret() {
        return this.secret;
    }

    @Override
    public String getIssuer() {
        return this.issuer;
    }

    public List<String> getExcludePermissionUrls() {
        return excludePermissionUrls != null ? excludePermissionUrls : DEFAULT_EXCLUDE_PERMISSION_URLS;
    }

    public void setExcludePermissionUrls(List<String> excludePermissionUrls) {
        this.excludePermissionUrls = excludePermissionUrls;
    }

    public List<String> getExcludeAllUrls() {
        return excludeAllUrls != null ? excludeAllUrls : DEFAULT_EXCLUDE_ALL_URLS;
    }

    public void setExcludeAllUrls(List<String> excludeAllUrls) {
        this.excludeAllUrls = excludeAllUrls;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
