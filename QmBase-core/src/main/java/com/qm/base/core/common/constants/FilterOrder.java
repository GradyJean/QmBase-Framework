package com.qm.base.core.common.constants;

/**
 * 定义过滤器在 Spring FilterChain 中的执行顺序。
 * 数值越小，优先级越高，越早执行。
 */
public enum FilterOrder {
    /**
     * BYPASS_ALL_SECURITY：用于排除所有过滤器的执行。
     * 例如在某些情况下需要跳过所有过滤器时使用。
     */
    BYPASS_ALL_SECURITY(Short.MIN_VALUE),
    /**
     * AUTH_HEADER：用于处理认证请求头（如 Authorization）的过滤器。
     * 通常放在过滤链的最前面执行。
     */
    AUTH_HEADER(0),

    /**
     * AUTH_ENTRY_POINT：用于处理认证入口点的过滤器。
     * 该过滤器通常在 AUTH_HEADER 之后执行，确保认证请求头已被处理。
     */
    AUTH_ENTRY_POINT(100),
    /**
     * SECURITY_CONTEXT：用于将 Token 或自定义 Header 解析为安全上下文对象，
     * 设置 userId、traceId 等上下文信息，供后续业务使用。
     */
    SECURITY_CONTEXT(101),
    /**
     * IGNORE_PERMISSION：用于处理跳过权限验证的过滤器。
     * 该过滤器在 SECURITY_CONTEXT 之后执行，确保上下文已准备好。
     */
    IGNORE_PERMISSION(110),
    /**
     * DOMAIN_MAPPING：用于处理域名映射的过滤器。
     * 在 IGNORE_PERMISSION 之后执行，确保域名映射已准备好。
     */
    DOMAIN_MAPPING(111),
    /**
     * PERMISSION：用于处理权限验证的过滤器。
     * 在 SECURITY_CONTEXT 之后执行，确保上下文已准备好。
     */
    CUSTOM_PERMISSION(300),
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
