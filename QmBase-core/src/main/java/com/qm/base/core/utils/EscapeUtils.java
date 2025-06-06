package com.qm.base.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * HTML/XML 字符转义工具类。
 * 主要用于将字符串中的特殊字符转为安全的显示字符，或反转义回原文。
 */
public class EscapeUtils {

    private static final Map<String, String> ESCAPE_MAP = new HashMap<>();
    private static final Map<String, String> UNESCAPE_MAP = new HashMap<>();

    static {
        ESCAPE_MAP.put("&", "&amp;");
        ESCAPE_MAP.put("<", "&lt;");
        ESCAPE_MAP.put(">", "&gt;");
        ESCAPE_MAP.put("\"", "&quot;");
        ESCAPE_MAP.put("'", "&#x27;");

        // 构建反转义映射
        ESCAPE_MAP.forEach((k, v) -> UNESCAPE_MAP.put(v, k));
    }

    /**
     * 转义 HTML/XML 特殊字符
     *
     * @param input 原始字符串
     * @return 转义后的字符串
     */
    public static String escape(String input) {
        if (input == null || input.isEmpty()) return input;
        String result = input;
        for (Map.Entry<String, String> entry : ESCAPE_MAP.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * 反转义 HTML/XML 特殊字符
     *
     * @param input 转义后的字符串
     * @return 原始字符串
     */
    public static String unescape(String input) {
        if (input == null || input.isEmpty()) return input;
        String result = input;
        for (Map.Entry<String, String> entry : UNESCAPE_MAP.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
