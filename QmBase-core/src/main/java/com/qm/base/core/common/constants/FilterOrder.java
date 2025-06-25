package com.qm.base.core.common.constants;

/**
 * 定义过滤器在 Spring FilterChain 中的执行顺序。
 * 数值越小，优先级越高，越早执行。
 */
public enum FilterOrder {
    /**
     * AUTH_HEADER：用于处理认证请求头（如 Authorization）的过滤器。
     * 通常放在过滤链的最前面执行。
     */
    AUTH_HEADER(0),

    /**
     * SECURITY_CONTEXT：用于将 Token 或自定义 Header 解析为安全上下文对象，
     * 设置 userId、traceId 等上下文信息，供后续业务使用。
     */
    SECURITY_CONTEXT(100),

    /**
     * PERMISSION：用于处理权限验证的过滤器。
     * 在 SECURITY_CONTEXT 之后执行，确保上下文已准备好。
     */
    PERMISSION(200),
    ;

    /**
     * 执行顺序值。
     * 数值越小，优先级越高，越早执行。
     */
    private final int order;

    /**
     * 构造方法。
     *
     */
    FilterOrder(int order) {
        this.order = order;
    }

    /**
     * 获取过滤器的执行顺序值。
     *
     * @return 执行顺序值
     */
    public int getOrder() {
        return order;
    }
}
