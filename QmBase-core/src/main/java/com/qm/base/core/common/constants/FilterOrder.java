package com.qm.base.core.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义过滤器在 Spring FilterChain 中的执行顺序。
 * 数值越小，优先级越高，越早执行。
 */
public enum FilterOrder {
    /**
     * 用于处理认证请求头（如 Authorization）的过滤器。
     * 通常放在过滤链的最前面执行。
     */
    HEADER(0, 99),

    /**
     * 用于将 Token 或自定义 Header 解析为安全上下文对象，
     * 设置 userId、traceId 等上下文信息，供后续业务使用。
     */
    CONTEXT(100, 199),
    /**
     * 用于处理权限验证的过滤器。
     * 主要用于检查用户是否有权限访问特定资源。
     */
    PERMISSION(200, 299),
    /**
     * ;
     * 用于处理异常的过滤器。
     * 主要用于捕获和处理在请求处理过程中发生的异常。
     */
    EXCEPTION(300, 399),
    /**
     * 用于处理请求日志的过滤器。
     * 主要用于记录请求的相关信息，如请求路径、参数等。
     */
    LOG(400, 499),
    /**
     * 用于处理响应日志的过滤器。
     * 主要用于记录响应的相关信息，如响应状态码、响应时间等。
     */
    RESPONSE_LOG(500, 599),
    /**
     * 用于处理跨域请求的过滤器。
     * 主要用于设置响应头，允许跨域访问。
     */
    CORS(600, 699),
    /**
     * 用于处理请求限流的过滤器。
     * 主要用于限制特定 IP 或用户在一定时间内的请求次数。
     */
    RATE_LIMIT(700, 799),
    /**
     * 用于处理请求缓存的过滤器。
     * 主要用于缓存特定请求的响应结果，以提高性能。
     */
    CACHE(800, 899),
    /**
     * 用于处理请求重定向的过滤器。
     * 主要用于将请求重定向到其他 URL。
     */
    REDIRECT(900, 999),
    /**
     * 用于处理请求的最后一个过滤器。
     * 通常用于清理资源或执行一些收尾工作。
     */
    LAST(1000, Integer.MAX_VALUE);
    /**
     * 执行顺序值。
     * 数值越小，优先级越高，越早执行。
     */
    private final int minOrder;
    /**
     * 最大执行顺序值。
     * 这里假设最大值为 Integer.MAX_VALUE，表示没有限制。
     */
    private final int maxOrder;

    private final Map<FilterOrder, Integer> orderCounter = new HashMap<>();

    /**
     * 构造方法。
     *
     */
    FilterOrder(int minOrder, int maxOrder) {
        this.minOrder = minOrder;
        this.maxOrder = maxOrder;
    }

    /**
     * 获取下一个执行顺序值，并将其存储在静态 Map 中。
     * 每次调用都会返回当前的顺序值，并将下一个顺序值加 1。
     *
     * @return 下一个执行顺序值
     */
    public int getNextOrder() {
        Integer nextOrder = orderCounter.get(this);
        // 如果当前 Map 中没有值，则初始化为最小顺序值
        if (nextOrder == null) {
            nextOrder = minOrder;
        }
        // 检查是否超过最大值
        if (nextOrder >= maxOrder) {
            throw new IllegalStateException("Filter order exceeded maximum limit for " + this.name());
        }
        // 更新下一个顺序值
        // 并将当前值存储到 Map 中
        orderCounter.put(this, nextOrder + 1);
        return nextOrder;
    }
}
