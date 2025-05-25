package com.qm.base.shared.cache.config;

import com.qm.base.shared.cache.api.QmCacheManager;
import com.qm.base.shared.cache.provider.caffeine.CaffeineQmCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CaffeineCacheAutoConfiguration - 本地缓存自动装配。
 * 在配置 qm.base.cache.type=caffeine 时激活。
 */
@Configuration
@ConditionalOnProperty(name = "qm.base.cache.type", havingValue = "caffeine")
@EnableConfigurationProperties(QmCacheProperties.class)
public class CaffeineCacheAutoConfiguration {

    @Bean
    public QmCacheManager caffeineQmCacheManager(QmCacheProperties properties) {
        int ttl = properties.getTtl() > 0 ? properties.getTtl() : 300;
        return new CaffeineQmCacheManager(ttl);
    }
}