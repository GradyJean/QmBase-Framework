package com.qm.base.payment.config;

import com.qm.base.payment.controller.PaymentNotifyController;
import com.qm.base.payment.handler.PaymentNotifyHandler;
import com.qm.base.payment.provider.PaymentProvider;
import com.qm.base.payment.service.PaymentNotifyService;
import com.qm.base.payment.service.PaymentService;
import com.qm.base.payment.service.impl.PaymentServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 支付模块自动配置。
 */
@Configuration
public class PaymentAutoConfiguration {

    /**
     * 注册统一支付服务。
     *
     * @param paymentProviders 支付 provider 列表
     * @return 统一支付服务
     */
    @Bean
    @ConditionalOnMissingBean(PaymentService.class)
    public PaymentService paymentService(List<PaymentProvider> paymentProviders) {
        return new PaymentServiceImpl(paymentProviders);
    }

    /**
     * 注册支付回调处理服务。
     *
     * @param paymentService       统一支付服务
     * @param paymentNotifyHandler 回调业务处理器
     * @return 支付回调处理服务
     */
    @Bean
    @ConditionalOnMissingBean(PaymentNotifyService.class)
    public PaymentNotifyService paymentNotifyService(PaymentService paymentService,
                                                     PaymentNotifyHandler paymentNotifyHandler) {
        return new PaymentNotifyService(paymentService, paymentNotifyHandler);
    }

    /**
     * 注册统一支付回调控制器。
     *
     * @param paymentNotifyService 支付回调处理服务
     * @return 统一支付回调控制器
     */
    @Bean
    @ConditionalOnMissingBean(PaymentNotifyController.class)
    public PaymentNotifyController paymentNotifyController(PaymentNotifyService paymentNotifyService) {
        return new PaymentNotifyController(paymentNotifyService);
    }
}
