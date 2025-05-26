package com.qm.base.shared.security.config;

import com.qm.base.shared.security.jwt.JwtProperties;
import com.qm.base.shared.security.jwt.SecurityContextFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 安全上下文自动配置类，自动注册 JWT 解析过滤器
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "security.jwt.secret")
    public FilterRegistrationBean<Filter> securityContextFilter(JwtProperties properties) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SecurityContextFilter(properties.getSecret()));
        registration.setOrder(0); // 放最前面
        registration.addUrlPatterns("/*");
        return registration;
    }
}