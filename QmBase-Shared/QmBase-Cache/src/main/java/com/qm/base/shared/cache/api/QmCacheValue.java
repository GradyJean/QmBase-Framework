package com.qm.base.shared.cache.api;

/**
 * QmCacheValue - 泛型缓存值封装类。
 * 可用于统一封装缓存返回值，并携带扩展信息（如命中状态、元信息等）。
 */
public class QmCacheValue<T> {

    private T value;

    // 可选字段：用于后续扩展，如缓存来源、traceId 等
    private String metadata;

    public QmCacheValue() {}

    public QmCacheValue(T value) {
        this.value = value;
    }

    public QmCacheValue(T value, String metadata) {
        this.value = value;
        this.metadata = metadata;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}