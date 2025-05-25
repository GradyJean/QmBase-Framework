package com.qm.base.shared.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QmBase ID 自动配置类
 * <p>
 * 引入 qmbase-id-starter 后，将自动注入一个 SnowflakeIdGenerator
 * 支持通过 application.yml 配置 workerId 和 datacenterId
 */
@Configuration
@EnableConfigurationProperties(QmIdProperties.class)
public class QmIdAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(QmIdAutoConfiguration.class);

    @Bean
    public InitializingBean initQmId(QmIdProperties props) {
        return () -> {
            QmId.init(props.getWorkerId(), props.getDatacenterId());
            log.info("[QmIdAutoConfiguration] Initialized with workerId={}, datacenterId={}", props.getWorkerId(), props.getDatacenterId());
        };
    }
}