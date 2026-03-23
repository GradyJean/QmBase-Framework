package com.qm.base.core.security.model;

/**
 * 权限信息类，用于表示一个权限，包含权限名称和权限动作。
 */

public class PermissionRecord {
    private final String path;
    private final String name;
    private final String action;
    private final String method;
    private final String resourceType;

    public PermissionRecord(String path, String method,String name, String action, String resourceType) {
        this.path = path;
        this.name = name;
        this.action = action;
        this.method = method;
        this.resourceType = resourceType;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    public String getMethod() {
        return method;
    }

    public String getResourceType() {
        return resourceType;
    }
}
