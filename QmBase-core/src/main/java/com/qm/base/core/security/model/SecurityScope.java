package com.qm.base.core.security.model;

/**
 * 权限域路由定义。
 * 用于将请求路径映射到具体的权限域。
 */
public interface SecurityScope {

    String getPathPattern();

    String getScope();

    String getName();
}
