package com.qm.base.auth.wechat.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.qm.base.auth.wechat")
@EnableConfigurationProperties({WechatProperties.class})
public class WechatAutoConfiguration {
}
