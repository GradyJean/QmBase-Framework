package com.qm.base.auth.model.vo;

/**
 * 第三方平台信息模型。
 * 表示系统中支持的外部登录平台（如微信、支付宝、抖音等）的基础信息结构。
 */
public class Platform {

    /**
     * 平台名称（如：微信、支付宝）
     */
    private String name;

    /**
     * 平台唯一编码（如：wechat、alipay）
     */
    private String code;

    /**
     * 平台图标 URL（用于前端展示）
     */
    private String icon;

    /**
     * 平台描述（可选说明文字）
     */
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
