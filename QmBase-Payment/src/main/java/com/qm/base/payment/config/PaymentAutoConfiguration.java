package com.qm.base.payment.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 支付模块自动配置。
 */
@Configuration
@ComponentScan(basePackages = "com.qm.base.payment")
public class PaymentAutoConfiguration {
}
