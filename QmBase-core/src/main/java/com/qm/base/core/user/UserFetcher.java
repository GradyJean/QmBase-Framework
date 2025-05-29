package com.qm.base.core.user;

/**
 * 用户信息获取器接口。
 * 一般由框架注入，用于从上下文中获取当前登录用户。
 */
public interface UserFetcher {

    /**
     * 获取当前用户信息。
     *
     * @return 当前用户，如果未登录可返回匿名用户对象或 null
     */
    User getCurrentUser();
}
