package com.qm.base.shared.cache.config;

import com.qm.base.shared.cache.core.key.DefaultKeyGenerator;
import com.qm.base.shared.cache.core.key.QmKeyGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QmCacheAutoConfiguration - 缓存基础组件自动配置类。
 * 注册默认 Key 生成器等核心依赖。
 */
@Configuration
public class QmCacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(QmKeyGenerator.class)
    public QmKeyGenerator defaultQmKeyGenerator() {
        return new DefaultKeyGenerator();
    }
}