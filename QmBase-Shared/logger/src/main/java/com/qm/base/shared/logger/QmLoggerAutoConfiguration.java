package com.qm.base.shared.logger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QmBase Logger 自动配置
 * <p>
 * 引入 qmbase-logger-starter 后，将自动注入 LogAspect 切面
 * 支持通过 application.yml 配置日志打印行为
 */
@Configuration
@EnableConfigurationProperties(QmLoggerProperties.class)
public class QmLoggerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LogAspect logAspect(QmLoggerProperties props) {
        LogAspect aspect = new LogAspect();
        aspect.setLogParams(props.isLogParams());
        aspect.setLogResult(props.isLogResult());
        aspect.setLevel(props.getLevel());
        return aspect;
    }
}
