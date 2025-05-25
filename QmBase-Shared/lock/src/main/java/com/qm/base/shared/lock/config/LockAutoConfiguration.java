package com.qm.base.shared.lock.config;

import com.qm.base.shared.lock.distribute.core.DistributedLockProperties;
import com.qm.base.shared.lock.local.LocalLockService;
import com.qm.base.shared.lock.local.ReentrantLocalLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 锁自动装配类
 * - 始终注册本地锁实现
 * - 根据配置项 qm.base.lock.distributed.type 注册分布式锁实现
 */
@Configuration
@EnableConfigurationProperties(DistributedLockProperties.class)
public class LockAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(LockAutoConfiguration.class);

    /**
     * 注册默认本地锁实现（始终启用）
     * 你可以考虑是否为 `ReentrantLocalLockService` 提供一个 `@ConditionalOnMissingBean(LocalLockService.class)` 以支持用户自定义实现。
     */
    @Bean
    @ConditionalOnMissingBean(LocalLockService.class)
    public LocalLockService localLockService() {
        return new ReentrantLocalLockService();
    }
}