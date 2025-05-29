package com.qm.base.shared.cache.config;

import com.qm.base.shared.cache.api.QmCacheManager;
import com.qm.base.shared.cache.provider.redis.RedisQmCacheManager;
import com.qm.base.shared.cache.provider.redis.RedisSerializerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisCacheAutoConfiguration - Redis 缓存自动装配类。
 * 当 qm.base.cache.type=redis 时生效，注册 RedisQmCacheManager。
 */
@Configuration
@ConditionalOnProperty(name = "qm.cache.type", havingValue = "redis")
@EnableConfigurationProperties(QmCacheProperties.class)
public class RedisCacheAutoConfiguration {

    /**
     * 默认 RedisTemplate<String, Object> 实例注册。
     * 用于 QmCache 统一缓存操作，若用户未自定义，则自动提供。
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(RedisSerializerFactory.string());
        template.setValueSerializer(RedisSerializerFactory.json());
        template.setHashKeySerializer(RedisSerializerFactory.string());
        template.setHashValueSerializer(RedisSerializerFactory.json());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public QmCacheManager redisQmCacheManager(RedisTemplate<String, Object> redisTemplate, QmCacheProperties properties) {
        if (redisTemplate == null || redisTemplate.getConnectionFactory() == null) {
            throw new IllegalStateException("[QmCache] Redis 缓存已启用，但未配置 RedisTemplate 或连接工厂");
        }

        int ttl = properties.getTtl() > 0 ? properties.getTtl() : 300;
        return new RedisQmCacheManager(redisTemplate, ttl);
    }
}