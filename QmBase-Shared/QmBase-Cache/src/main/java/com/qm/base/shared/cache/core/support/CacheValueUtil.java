package com.qm.base.shared.cache.core.support;

import com.qm.base.shared.cache.model.NullValue;

public class CacheValueUtil {

    /**
     * 将 null 值包装为 NullValue.INSTANCE，否则原样返回
     */
    public static Object wrap(Object val) {
        return val != null ? val : NullValue.INSTANCE;
    }

    /**
     * 若缓存值是 NullValue.INSTANCE，则还原为 null，否则返回原对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T unwrap(Object raw) {
        return raw instanceof NullValue ? null : (T) raw;
    }
}