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
@ConditionalOnProperty(name = "qm.cache.type", havingValue = "caffeine")
@EnableConfigurationProperties(QmCacheProperties.class)
public class CaffeineCacheAutoConfiguration {

    /**
     * 注册 Caffeine 缓存管理器，当配置 qm.base.cache.type=caffeine 时生效。
     * - ttl > 0：设置为指定秒数
     * - ttl == -1：永久缓存
     * - ttl == 0（默认未配置）：使用默认 300 秒
     */
    @Bean(name = "caffeineQmCacheManager")
    public QmCacheManager caffeineQmCacheManager(QmCacheProperties properties) {
        int configTtl = properties.getTtl();
        int ttl = (configTtl != 0) ? configTtl : 300;
        return new CaffeineQmCacheManager(ttl);
    }
}