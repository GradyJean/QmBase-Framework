package com.qm.base.shared.lock.distribute.zookeeper;

import com.qm.base.shared.lock.distribute.core.DistributedLockService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基于 Zookeeper 的分布式锁自动配置类
 */
@Configuration
@ConditionalOnProperty(name = "qm.lock.distributed.type", havingValue = "zookeeper")
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperLockAutoConfiguration {

    @Bean
    public CuratorFramework curatorFramework(ZookeeperProperties props) {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(props.getConnectString())
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();
        return client;
    }

    @Bean
    public DistributedLockService distributedLockService(CuratorFramework client) {
        return new ZookeeperDistributedLockService(client);
    }
}
