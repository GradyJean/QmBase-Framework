package com.qm.base.shared.cache.core.key;

import java.lang.reflect.Method;

/**
 * QmKeyGenerator - 缓存 key 生成策略接口。
 * 支持根据方法签名和参数生成缓存 key，可扩展为自定义实现。
 */
public interface QmKeyGenerator {

    /**
     * 生成缓存 key
     *
     * @param target 目标对象
     * @param method 调用方法
     * @param params 方法参数
     * @return 缓存 key 字符串
     */
    String generate(Object target, Method method, Object... params);
}