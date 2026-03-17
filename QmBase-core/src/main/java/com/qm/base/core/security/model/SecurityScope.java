package com.qm.base.core.security.model;

import com.qm.base.core.security.constants.SecurityConstant;

/**
 * SecurityScope 类表示一个域条目。
 * 它包含请求路径、HTTP 方法、域名和操作等信息。
 */
public class SecurityScope {

    /**
     * 请求路径，例如 "/api/v1/resource"
     */
    private String resourcePattern;

    /**
     * 当前请求的实际 HTTP 方法，例如 "GET", "POST", "PUT", "DELETE"。
     * 该值来自 HttpServletRequest#getMethod()。
     * 注意：映射规则中的方法可配置为 "*" 或 "ALL"，表示匹配任意请求方法。
     */
    private String httpMethod;

    /**
     * 默认的安全域标识，表示所有域。
     * 在没有指定具体域时使用此默认值。
     */
    private String scope;

    public SecurityScope(String resourcePattern, String httpMethod, String scope) {
        this.resourcePattern = resourcePattern;
        this.httpMethod = httpMethod;
        this.scope = scope;
    }

    public String getResourcePattern() {
        return resourcePattern;
    }

    public void setResourcePattern(String resourcePattern) {
        this.resourcePattern = resourcePattern;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
