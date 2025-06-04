package com.qm.base.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Auth 模块的自动配置类。
 * 用于在其他模块引用本模块时自动扫描并注册 auth 包下的相关组件，
 * 包括 controller、service 等。
 */
@Configuration
@ComponentScan(basePackages = "com.qm.base.auth")
@EnableConfigurationProperties({AuthProperties.class, JwtProperties.class})
public class AuthAutoConfiguration {
}
