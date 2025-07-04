package com.qm.base.shared.security.config;

import com.qm.base.shared.security.propagation.resttemplate.SecurityContextRestTemplateInterceptor;
import com.qm.base.shared.security.util.TrustedServiceChecker;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

/**
 * 安全上下文传播自动配置：
 * 1. 为 RestTemplate 添加安全上下文拦截器。
 * 2. 注册 TrustedServiceChecker 用于判断服务可信性。
 * <p>
 * 仅在存在注册中心（如 Nacos、Eureka）时启用，用于微服务间调用的安全上下文传递。
 */
@Configuration
@ConditionalOnClass(name = "org.springframework.cloud.client.discovery.DiscoveryClient")
public class SecurityPropagationAutoConfiguration {

    /**
     * RestTemplate 拦截器注册器。
     * 该 BeanPostProcessor 会在所有 Bean 初始化完成后检查是否为 RestTemplate，
     * 若是则自动添加 SecurityContextRestTemplateInterceptor 拦截器，用于传递安全上下文。
     * <p>
     * 仅在容器中存在 TrustedServiceChecker 时才启用（即存在注册中心的场景）。
     */
    @Bean
    @ConditionalOnBean(TrustedServiceChecker.class)
    public static BeanPostProcessor restTemplateInterceptorProcessor(TrustedServiceChecker trustedServiceChecker) {
        return new BeanPostProcessor() {
            /**
             * 在 RestTemplate 初始化后添加安全上下文拦截器。
             * 该拦截器会在请求头中注入当前线程的安全上下文信息（如用户ID、角色等）。
             */
            @Override
            public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) {
                if (bean instanceof RestTemplate restTemplate) {
                    restTemplate.getInterceptors().add(new SecurityContextRestTemplateInterceptor(trustedServiceChecker));
                }
                return bean;
            }
        };
    }

    /**
     * 注册 TrustedServiceChecker 实例。
     * 用于判断某个 host 是否为注册中心中的服务，用于微服务调用安全上下文传递判断。
     * 仅在容器中存在 DiscoveryClient（如 Nacos、Eureka、Consul）时才注册。
     */
    @Bean
    @ConditionalOnBean(DiscoveryClient.class)
    @ConditionalOnClass(DiscoveryClient.class)
    public TrustedServiceChecker trustedServiceChecker(DiscoveryClient discoveryClient) {
        return new TrustedServiceChecker(discoveryClient);
    }
}
