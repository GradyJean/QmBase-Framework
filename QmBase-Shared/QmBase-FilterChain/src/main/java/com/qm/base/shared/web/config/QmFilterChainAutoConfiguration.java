package com.qm.base.shared.web.config;

import com.qm.base.shared.web.proxy.QmFilterChainProxy;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@ComponentScan(basePackages = "com.qm.base.shared.web")
@Configuration
public class QmFilterChainAutoConfiguration {
    @Bean
    public FilterRegistrationBean<Filter> qmFilterChainRegistration(QmFilterChainProxy proxy) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(proxy);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE); // 可以自定义顺序
        registration.addUrlPatterns("/*"); // 拦截所有路径
        registration.setName("QmFilterChainProxy");
        return registration;
    }
}
