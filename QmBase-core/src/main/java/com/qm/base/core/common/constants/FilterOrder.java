package com.qm.base.core.common.constants;

/**
 * 定义过滤器执行顺序的枚举类。
 * 用于控制不同过滤器在 Spring FilterChain 中的执行顺序。
 */
public enum FilterOrder {
    /**
     * 用于处理认证头部信息的过滤器顺序。
     * 通常位于最前端。
     */
    AUTH_HEADER(0);
    /**
     * 过滤器的执行顺序值，值越小越优先执行。
     */
    private final int order;

    /**
     * 构造方法。
     *
     * @param order 执行顺序
     */
    FilterOrder(int order) {
        this.order = order;
    }

    /**
     * 获取过滤器的执行顺序。
     *
     * @return 执行顺序值
     */
    public int getOrder() {
        return order;
    }
}
