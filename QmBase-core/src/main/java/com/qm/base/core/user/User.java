package com.qm.base.core.user;

import java.io.Serializable;

/**
 * 通用用户信息接口。
 * 可用于认证上下文、权限判断、日志追踪等场景。
 */
public interface User extends Serializable {

    /**
     * 获取用户ID。
     *
     * @return 用户唯一标识
     */
    Long getUserId();

    /**
     * 判断用户是否为匿名身份。
     *
     * @return true 表示未登录用户
     */
    default boolean isAnonymous() {
        return getUserId() == null || getUserId() <= 0;
    }

    /**
     * 判断用户是否启用
     *
     * @return true 表示启用
     */
    default boolean isEnabled() {
        return true;
    }
}
