package com.qm.base.auth.wechat.config;

import com.qm.base.auth.wechat.provider.WechatProvider;
import com.qm.base.core.auth.third.provider.LoginProviderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Configuration
@ComponentScan(basePackages = "com.qm.base.auth.wechat")
@EnableConfigurationProperties({WechatProperties.class})
public class WechatAutoConfiguration {
    @Autowired
    public void registerProvider(LoginProviderRegistry registry, WechatProvider wechatProvider) {
        registry.register(wechatProvider);
    }

    @Bean(name = "wechatRestTemplate")
    public RestTemplate wechatRestTemplate() {
        // 使用 Spring Boot 默认支持的 SimpleClientHttpRequestFactory（基于 JDK 原生 HTTP）
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);    // 建立连接超时时间（单位：毫秒）
        factory.setReadTimeout(5000);       // 读取数据超时时间（单位：毫秒）
        return new RestTemplate(factory);
    }
}
