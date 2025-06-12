package com.qm.base.auth.provider;

import com.qm.base.core.auth.third.provider.LoginProvider;
import com.qm.base.core.auth.third.provider.LoginProviderRegistry;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的第三方登录平台注册器。
 * <p>
 * 负责注册和管理系统中启用的所有第三方登录平台，如微信、支付宝、抖音等。
 * 提供注册、按平台编码获取、获取全部注册平台等功能。
 */
@Component
public class DefaultLoginProviderRegistry implements LoginProviderRegistry {

    /**
     * 存储所有已注册的第三方登录平台，key 为平台唯一标识码（如 wechat、alipay）
     */
    private final Map<String, LoginProvider> providerMap = new ConcurrentHashMap<>();

    /**
     * 注册一个第三方登录平台。
     *
     * @param provider 第三方平台提供者实例
     */
    @Override
    public void register(LoginProvider provider) {
        if (provider.getLoginProperties().isEnabled()) {
            providerMap.put(provider.getPlatform(), provider);
        }
    }

    /**
     * 根据平台唯一标识码获取对应的登录平台。
     *
     * @param platform 平台唯一标识码
     * @return 对应的 LoginProvider 实例，若未注册返回 null
     */
    @Override
    public LoginProvider getPlatform(String platform) {
        return providerMap.get(platform);
    }

    /**
     * 获取所有已注册的第三方登录平台。
     *
     * @return 注册平台的集合
     */
    @Override
    public Collection<LoginProvider> getAll() {
        return providerMap.values();
    }
}
