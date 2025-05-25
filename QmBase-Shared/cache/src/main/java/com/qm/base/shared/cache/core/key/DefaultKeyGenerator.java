package com.qm.base.shared.cache.core.key;

import java.lang.reflect.Method;
import java.util.StringJoiner;

/**
 * DefaultKeyGenerator - 默认缓存 key 生成策略。
 * 生成规则为：类名 + 方法名 + 参数值拼接。
 */
public class DefaultKeyGenerator implements QmKeyGenerator {

    @Override
    public String generate(Object target, Method method, Object... params) {
        StringJoiner joiner = new StringJoiner(":", target.getClass().getSimpleName(), "");
        joiner.add(method.getName());
        if (params != null && params.length > 0) {
            for (Object param : params) {
                joiner.add(param == null ? "null" : param.toString());
            }
        }
        return joiner.toString();
    }
}