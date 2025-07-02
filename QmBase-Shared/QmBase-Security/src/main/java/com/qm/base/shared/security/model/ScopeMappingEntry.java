package com.qm.base.shared.security.model;

/**
 * DomainMappingEntry 类表示一个域映射条目。
 * 它包含资源模式、HTTP 方法、域名和操作等信息。
 */
public class ScopeMappingEntry {
    private final String resourcePattern;
    private final String httpMethod;
    private final String scope;
    private final String action;

    /**
     * 构造函数，用于创建一个新的 DomainMappingEntry 实例。
     *
     * @param resourcePattern 资源模式，例如 "/api/v1/resource"
     * @param httpMethod      HTTP 方法，例如 "GET", "POST", "PUT", "DELETE"
     * @param scope           域名，例如 "example.com"
     * @param action          操作名称，例如 "read", "write", "delete"
     */
    public ScopeMappingEntry(String resourcePattern, String httpMethod, String scope, String action) {
        this.resourcePattern = resourcePattern;
        this.httpMethod = httpMethod;
        this.scope = scope;
        this.action = action;
    }

    public String getResourcePattern() {
        return resourcePattern;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getScope() {
        return scope;
    }

    public String getAction() {
        return action;
    }
}
