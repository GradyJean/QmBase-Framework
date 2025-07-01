package com.qm.base.shared.security.model;

/**
 * DomainEntry 类表示一个域条目。
 * 它包含请求路径、HTTP 方法、域名和操作等信息。
 */
public class DomainEntry {
    private String requestPath;
    private String httpMethod;
    private String domain;
    private String action;

    /**
     * 构造函数，用于创建一个新的 DomainEntry 实例。
     *
     * @param requestPath 请求路径，例如 "/api/v1/resource"
     * @param httpMethod  HTTP 方法，例如 "GET", "POST", "PUT", "DELETE"
     */
    public DomainEntry(String requestPath, String httpMethod) {
        this.requestPath = requestPath;
        this.httpMethod = httpMethod;
    }

    public String getRequestPath() {
        return requestPath;
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

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
