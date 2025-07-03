package com.qm.base.shared.security.model;

import com.qm.base.shared.security.constants.SecurityConstant;

/**
 * DomainEntry 类表示一个域条目。
 * 它包含请求路径、HTTP 方法、域名和操作等信息。
 */
public class ScopeEntry {

    /**
     * 请求路径，例如 "/api/v1/resource"
     */
    private String requestPath;

    /**
     * HTTP 方法，例如 "GET", "POST", "PUT", "DELETE"
     */
    private String httpMethod;

    /**
     * 默认的安全域标识，表示所有域。
     * 在没有指定具体域时使用此默认值。
     */
    private String scope = SecurityConstant.SECURITY_SCOPE_DEFAULT;

    /**
     * 默认的安全操作标识，表示所有操作。
     * 在没有指定具体操作时使用此默认值。
     */
    private String action = SecurityConstant.SECURITY_ACTION_DEFAULT;

    /**
     * 构造函数，用于创建一个新的 DomainEntry 实例。
     *
     * @param requestPath 请求路径，例如 "/api/v1/resource"
     * @param httpMethod  HTTP 方法，例如 "GET", "POST", "PUT", "DELETE"
     */
    public ScopeEntry(String requestPath, String httpMethod) {
        this.requestPath = requestPath;
        this.httpMethod = httpMethod;
    }

    public String getRequestPath() {
        return requestPath;
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

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
