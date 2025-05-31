package com.qm.base.shared.logger.core;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志上下文对象，用于在一次日志记录中传递 traceId、spanId、起始时间及自定义属性等信息。
 */
public class LogContext {

    /**
     * 分布式调用链的 traceId，用于日志追踪。
     */
    private String traceId;

    /**
     * 可选的 spanId，用于细粒度的调用跟踪。
     */
    private String spanId;

    /**
     * 当前方法调用或请求开始的时间戳，用于统计耗时。
     */
    private Instant startTime;

    /**
     * 附加的上下文属性，用于扩展记录额外信息。
     */
    private Map<String, Object> attributes = new HashMap<>();

    /**
     * 构造方法，初始化开始时间为当前时间。
     */
    public LogContext() {
        this.startTime = Instant.now();
    }

    /**
     * 获取 traceId。
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * 设置 traceId。
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     * 获取 spanId。
     */
    public String getSpanId() {
        return spanId;
    }

    /**
     * 设置 spanId。
     */
    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    /**
     * 获取调用开始时间。
     */
    public Instant getStartTime() {
        return startTime;
    }

    /**
     * 设置调用开始时间。
     */
    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取上下文属性集合。
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 向上下文中添加一个属性。
     *
     * @param key   属性名
     * @param value 属性值
     */
    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    /**
     * 获取指定 key 对应的属性值。
     *
     * @param key 属性名
     * @return 属性值
     */
    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }
}
