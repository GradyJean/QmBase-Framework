package com.qm.base.shared.security.config;

import com.qm.base.auth.token.JwtTokenManager;
import com.qm.base.core.auth.token.TokenManager;
import com.qm.base.shared.security.filter.TokenAuthenticationFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

/**
 * 安全模块自动配置类，提供 Token 鉴权过滤器、权限评估器等核心组件。
 */
@Configuration
@ComponentScan(basePackages = "com.qm.base.shared.security")
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, TokenAuthenticationFilter tokenAuthenticationFilter) throws Exception {
        http
                // 开启跨域
                .cors(Customizer.withDefaults())
                // 开启 CSRF 防护（如使用 JWT 可 disable）
                .csrf(AbstractHttpConfigurer::disable)
                // 安全响应头
                .headers(headers -> headers
                        .contentSecurityPolicy(csp ->
                                csp.policyDirectives("default-src 'self'; script-src 'self'; object-src 'none';")
                        )
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                // 会话控制（默认启用）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 可选：最多一个 session
                )
                // 所有请求默认放行（你用 HandlerInterceptor 做权限判断）
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()
                )
                .addFilterBefore(tokenAuthenticationFilter, SecurityContextHolderAwareRequestFilter.class);

        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenManager tokenManager(SecurityProperties properties) {
        return new JwtTokenManager(properties);
    }
}
