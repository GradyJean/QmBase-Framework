package com.qm.base.shared.web.config;

import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.proxy.QmFilterChainProxy;
import com.qm.base.shared.web.registry.QmFilterRegistry;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.List;

@ComponentScan(basePackages = "com.qm.base.shared.web")
@Configuration
public class QmFilterChainAutoConfiguration {


    /**
     * 注册 QmFilterChainProxy 作为主拦截器链代理。
     * 默认会收集所有实现 QmFilterChain 接口的 Bean，构建为过滤链。
     */
    @Bean
    @ConditionalOnMissingBean
    public QmFilterChainProxy qmFilterChainProxy(QmFilterRegistry registry) {
        List<QmFilter> filters = registry.getFilters();
        return new QmFilterChainProxy(filters);
    }

    @Bean
    public FilterRegistrationBean<Filter> qmFilterChainRegistration(QmFilterChainProxy proxy) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(proxy);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE); // 可以自定义顺序
        registration.addUrlPatterns("/*"); // 拦截所有路径
        registration.setName("QmFilterChainProxy");
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean
    public QmFilterRegistry qmFilterRegistry() {
        return new QmFilterRegistry();
    }
}
