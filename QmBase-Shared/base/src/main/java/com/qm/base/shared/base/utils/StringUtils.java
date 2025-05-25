package com.qm.base.shared.base.utils;

/**
 * 字符串处理工具类，封装常用字符串判断和格式转换逻辑。
 */
public class StringUtils {

    /**
     * 判断字符串是否为 null 或全是空白字符
     *
     * @param str 输入字符串
     * @return true 表示为空或全空白
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断字符串是否非空白
     *
     * @param str 输入字符串
     * @return true 表示非空白
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 将驼峰命名转为下划线命名（如: userName -> user_name）
     *
     * @param camelStr 驼峰格式字符串
     * @return 下划线格式字符串
     */
    public static String camelToUnderline(String camelStr) {
        if (isBlank(camelStr)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < camelStr.length(); i++) {
            char c = camelStr.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    sb.append('_');
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将下划线命名转为驼峰命名（如: user_name -> userName）
     *
     * @param underlineStr 下划线格式字符串
     * @return 驼峰格式字符串
     */
    public static String underlineToCamel(String underlineStr) {
        if (isBlank(underlineStr)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCaseNext = false;
        for (char c : underlineStr.toCharArray()) {
            if (c == '_') {
                upperCaseNext = true;
            } else if (upperCaseNext) {
                sb.append(Character.toUpperCase(c));
                upperCaseNext = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
