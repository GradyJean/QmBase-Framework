package com.qm.base.core.auth.third.config;

/**
 * 第三方登录平台配置抽象接口。
 * <p>
 * 所有用于描述第三方平台配置项的类（如微信、支付宝、抖音等）应实现本接口，
 * 以统一暴露平台是否启用的开关字段，用于在系统初始化阶段筛选有效的登录平台。
 * <p>
 * 配合 {@code @ConfigurationProperties} 注解实现自动配置绑定。
 */
public interface LoginProperties {

    /**
     * 判断该第三方平台配置是否启用。
     *
     * @return true 表示该平台启用，false 表示禁用。
     */
    boolean isEnabled();
}
