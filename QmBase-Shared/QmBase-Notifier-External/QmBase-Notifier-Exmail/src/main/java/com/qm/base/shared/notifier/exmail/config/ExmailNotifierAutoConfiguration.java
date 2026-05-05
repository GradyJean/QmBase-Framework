package com.qm.base.shared.notifier.exmail.config;

import com.qm.base.shared.notifier.exmail.provider.ExmailNotifyProvider;
import com.qm.base.shared.notifier.provider.NotifyProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.Assert;

import java.util.Properties;

/**
 * 腾讯企业邮箱通知自动配置。
 */
@Configuration
@EnableConfigurationProperties(ExmailNotifierProperties.class)
@ConditionalOnProperty(prefix = "qm.notifier.exmail", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ExmailNotifierAutoConfiguration {

    /**
     * 构建邮件发送器。
     *
     * @param properties 企业邮箱配置
     * @return 邮件发送器
     */
    @Bean
    @ConditionalOnMissingBean(name = "exmailJavaMailSender")
    public JavaMailSender exmailJavaMailSender(ExmailNotifierProperties properties) {
        validateProperties(properties);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(properties.getHost());
        mailSender.setPort(properties.getPort());
        mailSender.setUsername(properties.getUsername());
        mailSender.setPassword(properties.getPassword());
        mailSender.setDefaultEncoding("UTF-8");

        Properties javaMailProperties = mailSender.getJavaMailProperties();
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.ssl.enable", String.valueOf(properties.isSsl()));
        javaMailProperties.put("mail.smtp.starttls.enable", "false");
        javaMailProperties.put("mail.smtp.timeout", "10000");
        javaMailProperties.put("mail.smtp.connectiontimeout", "10000");
        javaMailProperties.put("mail.smtp.writetimeout", "10000");
        return mailSender;
    }

    /**
     * 注册腾讯企业邮箱 provider。
     *
     * @param properties 企业邮箱配置
     * @param mailSender 邮件发送器
     * @return provider
     */
    @Bean
    @ConditionalOnMissingBean(name = "exmailNotifyProvider")
    public NotifyProvider exmailNotifyProvider(ExmailNotifierProperties properties,
                                               JavaMailSender mailSender) {
        return new ExmailNotifyProvider(properties, mailSender);
    }

    private void validateProperties(ExmailNotifierProperties properties) {
        Assert.hasText(properties.getProvider(), "qm.notifier.exmail.provider must not be blank");
        Assert.hasText(properties.getHost(), "qm.notifier.exmail.host must not be blank");
        Assert.notNull(properties.getPort(), "qm.notifier.exmail.port must not be null");
        Assert.hasText(properties.getUsername(), "qm.notifier.exmail.username must not be blank");
        Assert.hasText(properties.getPassword(), "qm.notifier.exmail.password must not be blank");
        Assert.hasText(properties.getFrom(), "qm.notifier.exmail.from must not be blank");
    }
}
