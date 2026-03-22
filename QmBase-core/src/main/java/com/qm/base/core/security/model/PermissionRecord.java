package com.qm.base.core.security.model;

/**
 * 权限信息类，用于表示一个权限，包含权限名称和权限动作。
 */
public record PermissionRecord(String name, String action, String method, String resourceType) {
}
