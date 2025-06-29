package com.qm.base.shared.security.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * SecurityAutoConfiguration 是一个自动配置类，
 * 用于配置与安全相关的组件和属性。
 * <p>
 * - @Configuration: 表示这是一个配置类。
 * - @ComponentScan: 扫描指定包中的组件，
 * 这里扫描的是 com.qm.base.shared.security 包。
 * - @EnableConfigurationProperties: 启用 SecurityProperties 配置属性。
 */
@Configuration
@ComponentScan(basePackages = "com.qm.base.shared.security")
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {
}
