package com.qm.base.payment.wechat.config;

import com.qm.base.payment.provider.PaymentProvider;
import com.qm.base.payment.wechat.provider.WechatPaymentProvider;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * 微信支付自动配置。
 */
@Configuration
@EnableConfigurationProperties(WechatPaymentProperties.class)
@ConditionalOnProperty(prefix = "qm.payment.wechat", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WechatPaymentAutoConfiguration {

    /**
     * 构建微信支付 APIv3 配置。
     *
     * @param properties 微信支付配置
     * @return 微信支付 API 配置
     */
    @Bean
    @ConditionalOnMissingBean(name = "wechatPaymentConfig")
    public RSAAutoCertificateConfig wechatPaymentConfig(WechatPaymentProperties properties) {
        validateProperties(properties);
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(properties.getMerchantId())
                .privateKeyFromPath(properties.getPrivateKeyPath())
                .merchantSerialNumber(properties.getMerchantSerialNumber())
                .apiV3Key(properties.getApiV3Key())
                .build();
    }

    /**
     * 构建微信 Native 支付服务。
     *
     * @param config 微信支付 API 配置
     * @return Native 支付服务
     */
    @Bean
    @ConditionalOnMissingBean
    public NativePayService nativePayService(RSAAutoCertificateConfig config) {
        return new NativePayService.Builder().config(config).build();
    }

    /**
     * 构建微信支付回调解析器。
     *
     * @param config 微信支付 API 配置
     * @return 回调解析器
     */
    @Bean
    @ConditionalOnMissingBean
    public NotificationParser notificationParser(RSAAutoCertificateConfig config) {
        return new NotificationParser(config);
    }

    /**
     * 注册微信支付 provider。
     *
     * @param properties         微信支付配置
     * @param nativePayService   Native 支付服务
     * @param notificationParser 微信支付回调解析器
     * @return 支付 provider
     */
    @Bean
    @ConditionalOnMissingBean(name = "wechatPaymentProvider")
    public PaymentProvider wechatPaymentProvider(WechatPaymentProperties properties,
                                                 NativePayService nativePayService,
                                                 NotificationParser notificationParser) {
        return new WechatPaymentProvider(properties, nativePayService, notificationParser);
    }

    /**
     * 校验微信支付必填配置。
     *
     * @param properties 微信支付配置
     */
    private void validateProperties(WechatPaymentProperties properties) {
        Assert.hasText(properties.getAppId(), "qm.payment.wechat.app-id must not be blank");
        Assert.hasText(properties.getMerchantId(), "qm.payment.wechat.merchant-id must not be blank");
        Assert.hasText(properties.getMerchantSerialNumber(), "qm.payment.wechat.merchant-serial-number must not be blank");
        Assert.hasText(properties.getPrivateKeyPath(), "qm.payment.wechat.private-key-path must not be blank");
        Assert.hasText(properties.getApiV3Key(), "qm.payment.wechat.api-v3-key must not be blank");
        Assert.hasText(properties.getNotifyUrl(), "qm.payment.wechat.notify-url must not be blank");
    }
}
