package com.qm.base.core.auth.third.provider;

import java.util.Collection;

/**
 * 第三方登录平台注册器接口。
 * <p>
 * 用于集中处理所有 LoginProvider 的注册逻辑。
 * 具体的注册实现由 auth 模块完成，并控制注册哪些平台。
 */
public interface LoginProviderRegistry {

    /**
     * 注册一个第三方登录平台 Provider。
     *
     * @param provider LoginProvider 实例
     */
    void register(LoginProvider provider);

    /**
     * 根据平台编码获取对应的 LoginProvider 实例。
     *
     * @param platform 平台唯一标识符（如 wechat、alipay）
     * @return 对应的 LoginProvider 实例，若不存在可返回 null 或抛出异常（由实现类决定）
     */
    LoginProvider getPlatform(String platform);

    /**
     * 获取所有已注册的第三方登录平台。
     *
     * @return 所有注册的 LoginProvider 实例集合
     */
    Collection<LoginProvider> getAll();
}
