package com.qm.base.auth.wechat.config;

import com.qm.base.auth.wechat.provider.WechatProvider;
import com.qm.base.core.auth.third.provider.LoginProviderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.qm.base.auth.wechat")
@EnableConfigurationProperties({WechatProperties.class})
public class WechatAutoConfiguration {
    @Autowired
    public void registerProvider(LoginProviderRegistry registry, WechatProvider wechatProvider) {
        registry.register(wechatProvider);
    }
}
