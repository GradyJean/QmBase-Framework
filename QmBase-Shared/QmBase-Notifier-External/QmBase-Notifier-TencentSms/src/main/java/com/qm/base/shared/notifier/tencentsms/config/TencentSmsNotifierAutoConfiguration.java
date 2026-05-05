package com.qm.base.shared.notifier.tencentsms.config;

import com.qm.base.shared.notifier.provider.NotifyProvider;
import com.qm.base.shared.notifier.tencentsms.provider.TencentSmsNotifyProvider;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * 腾讯云短信通知自动配置。
 */
@Configuration
@EnableConfigurationProperties(TencentSmsNotifierProperties.class)
@ConditionalOnProperty(prefix = "qm.notifier.tencent-sms", name = "enabled", havingValue = "true", matchIfMissing = true)
public class TencentSmsNotifierAutoConfiguration {

    /**
     * 构建腾讯云短信客户端。
     *
     * @param properties 腾讯云短信配置
     * @return 短信客户端
     */
    @Bean
    @ConditionalOnMissingBean
    public SmsClient tencentSmsClient(TencentSmsNotifierProperties properties) {
        validateProperties(properties);
        Credential credential = new Credential(properties.getSecretId(), properties.getSecretKey());

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(properties.getEndpoint());

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        return new SmsClient(credential, properties.getRegion(), clientProfile);
    }

    /**
     * 注册腾讯云短信通知 provider。
     *
     * @param properties 腾讯云短信配置
     * @param smsClient  短信客户端
     * @return provider
     */
    @Bean
    @ConditionalOnMissingBean(name = "tencentSmsNotifyProvider")
    public NotifyProvider tencentSmsNotifyProvider(TencentSmsNotifierProperties properties,
                                                   SmsClient smsClient) {
        return new TencentSmsNotifyProvider(properties, smsClient);
    }

    private void validateProperties(TencentSmsNotifierProperties properties) {
        Assert.hasText(properties.getProvider(), "qm.notifier.tencent-sms.provider must not be blank");
        Assert.hasText(properties.getSecretId(), "qm.notifier.tencent-sms.secret-id must not be blank");
        Assert.hasText(properties.getSecretKey(), "qm.notifier.tencent-sms.secret-key must not be blank");
        Assert.hasText(properties.getEndpoint(), "qm.notifier.tencent-sms.endpoint must not be blank");
        Assert.hasText(properties.getSmsSdkAppId(), "qm.notifier.tencent-sms.sms-sdk-app-id must not be blank");
        Assert.hasText(properties.getSignName(), "qm.notifier.tencent-sms.sign-name must not be blank");
    }
}
