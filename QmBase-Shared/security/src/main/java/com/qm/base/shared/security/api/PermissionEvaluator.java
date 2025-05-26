package com.qm.base.shared.security.api;

/**
 * 权限判断接口，由业务系统实现，用于判断当前用户是否具备指定权限
 */
public interface PermissionEvaluator {

    /**
     * 判断当前用户是否拥有指定权限
     *
     * @param permission 权限标识（如 "user:delete"）
     * @return true 表示有权限，false 表示无权限
     */
    boolean hasPermission(String permission);
}