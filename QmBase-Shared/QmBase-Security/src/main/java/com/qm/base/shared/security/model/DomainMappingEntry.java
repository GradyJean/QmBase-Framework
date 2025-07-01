package com.qm.base.shared.security.model;

/**
 * DomainMappingEntry 类表示一个域映射条目。
 * 它包含资源模式、HTTP 方法、域名和操作等信息。
 */
public class DomainMappingEntry {
    private final String resourcePattern;
    private final String httpMethod;
    private final String domain;
    private final String action;

    /**
     * 构造函数，用于创建一个新的 DomainMappingEntry 实例。
     *
     * @param resourcePattern 资源模式，例如 "/api/v1/resource"
     * @param httpMethod      HTTP 方法，例如 "GET", "POST", "PUT", "DELETE"
     * @param domain          域名，例如 "example.com"
     * @param action          操作名称，例如 "read", "write", "delete"
     */
    public DomainMappingEntry(String resourcePattern, String httpMethod, String domain, String action) {
        this.resourcePattern = resourcePattern;
        this.httpMethod = httpMethod;
        this.domain = domain;
        this.action = action;
    }

    public String getResourcePattern() {
        return resourcePattern;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getDomain() {
        return domain;
    }

    public String getAction() {
        return action;
    }
}
