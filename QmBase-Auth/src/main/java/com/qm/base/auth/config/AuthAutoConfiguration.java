package com.qm.base.auth.config;

import com.qm.base.auth.token.JwtTokenManager;
import com.qm.base.core.auth.token.TokenManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Auth 模块的自动配置类。
 * 用于在其他模块引用本模块时自动扫描并注册 auth 包下的相关组件，
 * 包括 controller、service 等。
 */
@Configuration
@ComponentScan(basePackages = "com.qm.base.auth")
@EnableConfigurationProperties({AuthProperties.class})
public class AuthAutoConfiguration {
    /**
     * token 管理器 bean
     *
     * @param properties 配置文件
     * @return TokenManager
     */
    @Bean
    public TokenManager tokenManager(AuthProperties properties) {
        return new JwtTokenManager(properties);
    }
}
