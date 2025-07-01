package com.qm.base.shared.security.config;

import com.qm.base.shared.security.mapping.DomainMappingLoader;
import com.qm.base.shared.security.mapping.EmptyDomainMappingLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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
    /**
     * 定义一个 DomainMappingLoader 的 Bean。
     * 如果容器中没有其他 DomainMappingLoader 实现，则使用 EmptyDomainMappingLoader。
     * 这样可以避免在没有配置时出现错误，同时提供一个空的实现。
     *
     * @return 一个 DomainMappingLoader 实例
     */
    @Bean
    @ConditionalOnMissingBean(DomainMappingLoader.class)
    public DomainMappingLoader domainMappingLoader() {
        // 返回一个空的域映射加载器，避免在没有配置时出现错误
        return new EmptyDomainMappingLoader();
    }
}
