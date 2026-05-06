package com.qm.base.payment.config;

import com.qm.base.payment.handler.DefaultPaymentNotifyHandler;
import com.qm.base.payment.handler.PaymentNotifyHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 支付模块自动配置。
 */
@Configuration
@ComponentScan(basePackages = "com.qm.base.payment")
public class PaymentAutoConfiguration {
    /**
     * 默认注册支付回调处理服务。
     *
     * @return 支付回调处理服务
     */
    @Bean
    @ConditionalOnMissingBean(PaymentNotifyHandler.class)
    public PaymentNotifyHandler paymentNotifyHandler() {
        return new DefaultPaymentNotifyHandler();
    }
}
