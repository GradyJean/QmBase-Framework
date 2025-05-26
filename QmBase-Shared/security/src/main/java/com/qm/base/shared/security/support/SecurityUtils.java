package com.qm.base.shared.security.support;

import com.qm.base.shared.security.context.LoginUser;
import com.qm.base.shared.security.context.SecurityContextHolder;

import java.util.List;

/**
 * 安全工具类，封装用户信息获取与权限判断操作
 */
public class SecurityUtils {

    /**
     * 获取当前用户ID
     */
    public static Long getUserId() {
        LoginUser user = SecurityContextHolder.get();
        return user != null ? user.getUserId() : null;
    }

    /**
     * 获取当前用户名
     */
    public static String getUsername() {
        LoginUser user = SecurityContextHolder.get();
        return user != null ? user.getUsername() : null;
    }

    /**
     * 判断当前用户是否拥有指定权限
     * @param permission 权限标识
     */
    public static boolean hasPermission(String permission) {
        LoginUser user = SecurityContextHolder.get();
        if (user == null || user.getPermissions() == null) {
            return false;
        }
        List<String> permissions = user.getPermissions();
        return permissions.contains(permission);
    }
}