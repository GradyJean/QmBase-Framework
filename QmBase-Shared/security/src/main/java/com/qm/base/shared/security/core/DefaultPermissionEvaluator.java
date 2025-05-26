package com.qm.base.shared.security.core;

import com.qm.base.shared.security.api.PermissionEvaluator;
import com.qm.base.shared.security.context.LoginUser;
import com.qm.base.shared.security.context.SecurityContextHolder;

import java.util.List;

/**
 * 默认权限判断器，从当前登录用户的权限列表中查找指定权限标识
 */
public class DefaultPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(String permission) {
        LoginUser user = SecurityContextHolder.get();
        if (user == null || user.getPermissions() == null) {
            return false;
        }
        List<String> permissions = user.getPermissions();
        return permissions.contains(permission);
    }
}