package com.qm.base.shared.security.context;

import com.qm.base.context.AbstractContextHolder;

/**
 * 用于存储和管理当前线程的 SecurityContext，上下文包括用户信息
 * 基于 ThreadLocal 实现，每个线程独立，线程结束时应调用 clearContext() 释放资源。
 */
public class SecurityContextHolder extends AbstractContextHolder<SecurityContext> {
    private static final SecurityContextHolder INSTANCE = new SecurityContextHolder();

    /**
     * 设置当前线程的安全上下文
     *
     * @param context 当前线程的安全上下文
     */
    public static void setContext(SecurityContext context) {
        INSTANCE.set(context);
    }

    /**
     * 获取当前线程的安全上下文
     *
     * @return 当前线程的 SecurityContext 实例，若未设置则为 null
     */
    public static SecurityContext getContext() {
        return INSTANCE.get();
    }

    /**
     * 清除当前线程的安全上下文，并移除 MDC 中的 traceId，防止线程复用导致信息泄露。
     */
    public static void clearContext() {
        INSTANCE.clear();
    }

    /**
     * 获取当前线程上下文中的 userId
     *
     * @return 当前登录用户的 userId，若上下文未设置则返回 null
     */
    public static Long getUserId() {
        SecurityContext context = getContext();
        return context == null ? null : context.getUserId();
    }
}
