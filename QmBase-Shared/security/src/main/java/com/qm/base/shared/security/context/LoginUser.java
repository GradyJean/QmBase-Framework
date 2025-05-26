package com.qm.base.shared.security.context;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 登录用户上下文结构，支持权限控制、扩展信息挂载
 */
public class LoginUser implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名（登录标识）
     */
    private String username;

    /**
     * 拥有的角色列表
     */
    private List<String> roles;

    /**
     * 拥有的权限标识列表
     */
    private List<String> permissions;

    /**
     * 扩展字段（如租户ID、组织信息等）
     */
    private Map<String, Object> extra;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}