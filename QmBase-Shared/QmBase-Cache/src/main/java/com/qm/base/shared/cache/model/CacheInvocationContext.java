package com.qm.base.shared.cache.model;

import com.qm.base.shared.cache.core.annotation.QmCacheable;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * CacheInvocationContext - 缓存执行上下文封装类。
 * 用于封装一次缓存调用所涉及的所有参数与状态，用于扩展点或统一传参。
 */
public class CacheInvocationContext {

    /** 当前执行的方法 */
    private Method method;

    /** 方法所属对象（可能为代理对象） */
    private Object target;

    /** 方法参数 */
    private Object[] args;

    /** 缓存注解 */
    private QmCacheable annotation;

    /** 缓存名（对应注解 name 字段） */
    private String cacheName;

    /** 解析出的缓存 key */
    private String cacheKey;

    /** 是否命中缓存 */
    private boolean cacheHit;

    /** 方法返回结果 */
    private Object result;

    /** 执行开始时间（用于耗时计算） */
    private long startTime;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public QmCacheable getAnnotation() {
        return annotation;
    }

    public void setAnnotation(QmCacheable annotation) {
        this.annotation = annotation;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public boolean isCacheHit() {
        return cacheHit;
    }

    public void setCacheHit(boolean cacheHit) {
        this.cacheHit = cacheHit;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "CacheInvocationContext{" +
                "method=" + method +
                ", target=" + target +
                ", args=" + Arrays.toString(args) +
                ", annotation=" + annotation +
                ", cacheName='" + cacheName + '\'' +
                ", cacheKey='" + cacheKey + '\'' +
                ", cacheHit=" + cacheHit +
                ", result=" + result +
                ", startTime=" + startTime +
                '}';
    }
}