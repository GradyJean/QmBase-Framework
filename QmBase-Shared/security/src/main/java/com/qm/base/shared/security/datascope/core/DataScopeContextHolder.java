package com.qm.base.shared.security.datascope.core;

import java.util.Map;

/**
 * 数据权限上下文容器，使用 ThreadLocal 传递注解参数供后续处理使用
 */
public class DataScopeContextHolder {

    private static final ThreadLocal<Map<String, Object>> CONTEXT = new ThreadLocal<>();

    public static void set(Map<String, Object> context) {
        CONTEXT.set(context);
    }

    public static Map<String, Object> get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static Object get(String key) {
        Map<String, Object> map = get();
        return map != null ? map.get(key) : null;
    }

    public static String getString(String key) {
        Object value = get(key);
        return value != null ? value.toString() : null;
    }

    public static Boolean getBoolean(String key) {
        Object value = get(key);
        return value instanceof Boolean ? (Boolean) value : false;
    }
}