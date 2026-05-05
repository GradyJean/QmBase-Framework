package com.qm.base.shared.notifier.config;

import com.qm.base.shared.notifier.handler.NotifySendHandler;
import com.qm.base.shared.notifier.provider.NotifyProvider;
import com.qm.base.shared.notifier.provider.support.LogNotifyProvider;
import com.qm.base.shared.notifier.service.NotifierService;
import com.qm.base.shared.notifier.service.impl.NotifierServiceImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 通知模块自动配置。
 */
@Configuration
public class NotifierAutoConfiguration {

    /**
     * 注册默认日志通知 provider。
     *
     * @return 日志通知 provider
     */
    @Bean
    @ConditionalOnMissingBean(name = "logNotifyProvider")
    public NotifyProvider logNotifyProvider() {
        return new LogNotifyProvider();
    }

    /**
     * 注册统一通知服务。
     *
     * @param notifyProviders           通知 provider 列表
     * @param notifySendHandlerProvider 发送后处理 SPI
     * @return 统一通知服务
     */
    @Bean
    @ConditionalOnMissingBean(NotifierService.class)
    public NotifierService notifierService(List<NotifyProvider> notifyProviders,
                                           ObjectProvider<NotifySendHandler> notifySendHandlerProvider) {
        return new NotifierServiceImpl(notifyProviders, notifySendHandlerProvider);
    }
}
