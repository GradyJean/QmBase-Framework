package com.qm.base.core.security.model;

/**
 * 权限处理状态。
 */
public enum PermissionState {
    /**
     * 权限流程尚未得到最终结论，后续过滤器可继续处理。
     */
    PENDING,
    /**
     * 命中忽略权限规则，后续权限过滤器无需继续执行。
     */
    IGNORED,
    /**
     * 权限流程最终放行。
     */
    ALLOWED,
    /**
     * 权限流程最终拒绝。
     */
    DENIED
}
