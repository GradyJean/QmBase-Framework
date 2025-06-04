package com.qm.base.shared.base.utils;

import java.util.regex.Pattern;

/**
 * 正则工具类，用于校验手机号、邮箱、身份证号等格式。
 */
public class RegexUtils {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern IDCARD_PATTERN = Pattern.compile("^\\d{15}|\\d{17}[0-9Xx]$");
    private static final Pattern CHINESE_NAME_PATTERN = Pattern.compile("^[\\u4e00-\\u9fa5]{2,10}$");
    private static final Pattern URL_PATTERN = Pattern.compile("^(http|https)://[\\w.-]+(:\\d+)?(/[\\w\\-./?%&=]*)?$");
    private static final Pattern IP_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.(?!$)|$){4}$");
    private static final Pattern PLATE_NUMBER_PATTERN = Pattern.compile("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{5}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{3,19}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d\\W_]{8,}$");

    /**
     * 校验是否为合法手机号（中国大陆）
     */
    public static boolean isPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 校验是否为合法邮箱地址
     */
    public static boolean isEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 校验是否为合法身份证号（15位或18位，允许末位为X）
     */
    public static boolean isIdCard(String idCard) {
        return idCard != null && IDCARD_PATTERN.matcher(idCard).matches();
    }

    /**
     * 校验是否为中文姓名（2~10个汉字）
     */
    public static boolean isChineseName(String name) {
        return name != null && CHINESE_NAME_PATTERN.matcher(name).matches();
    }

    /**
     * 校验是否为合法 URL（支持 http/https）
     */
    public static boolean isUrl(String url) {
        return url != null && URL_PATTERN.matcher(url).matches();
    }

    /**
     * 校验是否为合法 IPv4 地址
     */
    public static boolean isIPv4(String ip) {
        return ip != null && IP_PATTERN.matcher(ip).matches();
    }

    /**
     * 校验是否为合法车牌号（简化版，支持新能源/普通车牌）
     */
    public static boolean isPlateNumber(String plate) {
        return plate != null && PLATE_NUMBER_PATTERN.matcher(plate).matches();
    }

    /**
     * 用户名正则：支持字母、数字、下划线，必须以字母开头，长度为4-20位
     */
    public static boolean isUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * 密码正则：最少8位，必须包含大小写字母、数字，允许特殊字符
     */
    public static boolean isPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
}
