package com.qm.base.shared.cache.model;

import java.io.Serializable;

/**
 * NullValue 是缓存中用于占位的特殊对象，表示方法返回值为 null。
 * 支持被 Redis 与 Caffeine 等序列化器识别和替换为 null。
 */
public final class NullValue implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final NullValue INSTANCE = new NullValue();

    private NullValue() {
    }

    @Override
    public String toString() {
        return "NullValue";
    }
}
