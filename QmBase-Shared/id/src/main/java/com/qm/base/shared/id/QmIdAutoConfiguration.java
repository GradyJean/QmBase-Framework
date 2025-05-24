package com.qm.base.shared.id;

import com.qm.base.shared.id.IdGenerator;
import com.qm.base.shared.id.SnowflakeIdGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

    @Bean
    @ConditionalOnMissingBean
    public IdGenerator idGenerator(QmIdProperties props) {
        return new SnowflakeIdGenerator(props.getWorkerId(), props.getDatacenterId());
    }
}