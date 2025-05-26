// SecurityContextHolder.java (空类，用于结构搭建)

package com.qm.base.shared.security.context;

/**
 * 登录用户上下文容器，基于 ThreadLocal 实现线程隔离
 */
public class SecurityContextHolder {

    private static final ThreadLocal<LoginUser> CONTEXT = new ThreadLocal<>();

    /**
     * 设置当前登录用户
     * @param user LoginUser 对象
     */
    public static void set(LoginUser user) {
        CONTEXT.set(user);
    }

    /**
     * 获取当前登录用户
     * @return LoginUser 对象或 null
     */
    public static LoginUser get() {
        return CONTEXT.get();
    }

    /**
     * 清除当前线程中的登录用户信息
     */
    public static void clear() {
        CONTEXT.remove();
    }
}