package com.qm.base.core.security.model;

/**
 * 权限信息类，用于表示一个权限，包含权限名称和权限动作。
 */

public record PermissionRecord(String pathPaten, String method, String name, String action, String resourceType) {
}
