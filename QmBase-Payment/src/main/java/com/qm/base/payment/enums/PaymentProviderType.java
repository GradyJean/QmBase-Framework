package com.qm.base.payment.enums;

import java.util.Arrays;

/**
 * 支付渠道类型枚举。
 */
public enum PaymentProviderType {

    /**
     * 微信支付。
     */
    WECHAT("wechat", "微信"),

    /**
     * 支付宝支付。
     */
    ALIPAY("alipay", "支付宝");

    /**
     * 渠道编码。
     */
    private final String code;

    /**
     * 渠道名称。
     */
    private final String name;

    PaymentProviderType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 获取渠道编码。
     *
     * @return 渠道编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取渠道名称。
     *
     * @return 渠道名称
     */
    public String getName() {
        return name;
    }

    /**
     * 根据渠道编码解析支付渠道枚举。
     *
     * @param code 渠道编码
     * @return 支付渠道枚举
     */
    public static PaymentProviderType fromCode(String code) {
        return Arrays.stream(values())
                .filter(item -> item.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported payment provider: " + code));
    }
}
