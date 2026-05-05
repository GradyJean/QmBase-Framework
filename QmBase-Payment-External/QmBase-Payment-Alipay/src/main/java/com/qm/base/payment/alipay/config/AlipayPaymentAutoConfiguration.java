package com.qm.base.payment.alipay.config;

import com.alipay.v3.ApiClient;
import com.alipay.v3.ApiException;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.util.model.AlipayConfig;
import com.qm.base.payment.alipay.provider.AlipayPaymentProvider;
import com.qm.base.payment.provider.PaymentProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 支付宝支付自动配置。
 */
@Configuration
@EnableConfigurationProperties(AlipayPaymentProperties.class)
@ConditionalOnProperty(prefix = "qm.payment.alipay", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AlipayPaymentAutoConfiguration {

    /**
     * 构建支付宝 SDK 配置。
     *
     * @param properties 支付宝支付配置
     * @return 支付宝 SDK 配置
     */
    @Bean
    @ConditionalOnMissingBean
    public AlipayConfig alipayConfig(AlipayPaymentProperties properties) {
        validateProperties(properties);
        AlipayConfig config = new AlipayConfig();
        config.setServerUrl(properties.getServerUrl());
        config.setAppId(properties.getAppId());
        config.setPrivateKey(readFile(properties.getPrivateKeyPath()));
        config.setAlipayPublicKey(readFile(properties.getAlipayPublicKeyPath()));
        return config;
    }

    /**
     * 构建支付宝 API 客户端。
     *
     * @param config 支付宝 SDK 配置
     * @return 支付宝 API 客户端
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiClient alipayApiClient(AlipayConfig config) {
        ApiClient apiClient = new ApiClient();
        try {
            apiClient.setAlipayConfig(config);
        } catch (ApiException e) {
            throw new IllegalStateException("Failed to initialize alipay api client", e);
        }
        return apiClient;
    }

    /**
     * 构建支付宝交易 API。
     *
     * @param apiClient 支付宝 API 客户端
     * @return 支付宝交易 API
     */
    @Bean
    @ConditionalOnMissingBean
    public AlipayTradeApi alipayTradeApi(ApiClient apiClient) {
        return new AlipayTradeApi(apiClient);
    }

    /**
     * 注册支付宝支付 provider。
     *
     * @param properties     支付宝配置
     * @param alipayTradeApi 支付宝交易 API
     * @return 支付 provider
     */
    @Bean
    @ConditionalOnMissingBean(name = "alipayPaymentProvider")
    public PaymentProvider alipayPaymentProvider(AlipayPaymentProperties properties,
                                                 AlipayTradeApi alipayTradeApi) {
        return new AlipayPaymentProvider(properties, alipayTradeApi);
    }

    private void validateProperties(AlipayPaymentProperties properties) {
        Assert.hasText(properties.getServerUrl(), "qm.payment.alipay.server-url must not be blank");
        Assert.hasText(properties.getAppId(), "qm.payment.alipay.app-id must not be blank");
        Assert.hasText(properties.getPrivateKeyPath(), "qm.payment.alipay.private-key-path must not be blank");
        Assert.hasText(properties.getAlipayPublicKeyPath(), "qm.payment.alipay.alipay-public-key-path must not be blank");
        Assert.hasText(properties.getCharset(), "qm.payment.alipay.charset must not be blank");
        Assert.hasText(properties.getSignType(), "qm.payment.alipay.sign-type must not be blank");
        Assert.hasText(properties.getNotifyUrl(), "qm.payment.alipay.notify-url must not be blank");
    }

    private String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read file: " + path, e);
        }
    }
}
