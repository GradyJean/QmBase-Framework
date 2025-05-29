package com.qm.base.shared.cache.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;

/**
 * NullValue 是缓存中用于占位的特殊对象，表示方法返回值为 null。
 * 支持被 Redis 与 Caffeine 等序列化器识别和替换为 null。
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public final class NullValue implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final NullValue INSTANCE = new NullValue();

    private final String marker = "null"; // 用于防止 Jackson 空对象异常

    private NullValue() {
    }

    public String getMarker() {
        return marker;
    }

    @Override
    public String toString() {
        return "NullValue";
    }
}
