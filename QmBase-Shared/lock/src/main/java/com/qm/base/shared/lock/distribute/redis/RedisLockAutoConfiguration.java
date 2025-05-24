package com.qm.base.shared.lock.distribute.redis;

import com.qm.base.shared.lock.distribute.DistributedLockService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis 分布式锁自动配置类
 * 当配置 qm.base.lock.distributed.type=redis 且存在 RedisTemplate 时启用
 */
@Configuration
@ConditionalOnClass(StringRedisTemplate.class)
@ConditionalOnProperty(name = "qm.base.lock.distributed.type", havingValue = "redis")
public class RedisLockAutoConfiguration {
    /**
     * 注入基于 Redis 的分布式锁实现
     */
    @Bean
    public DistributedLockService distributedLockService(StringRedisTemplate redisTemplate) {
        return new RedisDistributedLockService(redisTemplate);
    }
}