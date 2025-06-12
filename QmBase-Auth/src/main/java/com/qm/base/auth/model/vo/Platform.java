package com.qm.base.auth.model.vo;

/**
 * 第三方平台信息模型。
 * 表示系统中支持的外部登录平台（如微信、支付宝、抖音等）的基础信息结构。
 */
public class Platform {

    /**
     * 平台名称（如：微信、支付宝）
     */
    private final String name;

    /**
     * 平台唯一编码（如：wechat、alipay）
     */
    private final String platform;

    public Platform(String name, String platform) {
        this.name = name;
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }
}
