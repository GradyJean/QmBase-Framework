package com.qm.base.shared.notifier.exmail.provider;

import com.qm.base.shared.notifier.exmail.config.ExmailNotifierProperties;
import com.qm.base.shared.notifier.provider.NotifyProvider;
import com.qm.base.shared.notifier.schema.NotifySendSchema;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.UUID;

/**
 * 腾讯企业邮箱通知 provider。
 */
public class ExmailNotifyProvider implements NotifyProvider {

    private final ExmailNotifierProperties properties;
    private final JavaMailSender mailSender;

    public ExmailNotifyProvider(ExmailNotifierProperties properties, JavaMailSender mailSender) {
        this.properties = properties;
        this.mailSender = mailSender;
    }

    @Override
    public String getProvider() {
        return properties.getProvider();
    }

    @Override
    public String getName() {
        return "腾讯企业邮箱";
    }

    @Override
    public NotifySendSchema.Result send(NotifySendSchema.Input request) {
        List<String> receivers = request.getReceivers();
        if (receivers == null || receivers.isEmpty()) {
            throw new IllegalArgumentException("Email receivers must not be empty");
        }
        if (request.getSubject() == null || request.getSubject().isBlank()) {
            throw new IllegalArgumentException("Email subject must not be blank");
        }

        String requestId = UUID.randomUUID().toString();
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setFrom(properties.getFrom());
            helper.setTo(receivers.toArray(String[]::new));
            helper.setSubject(request.getSubject());
            helper.setText(request.getContent(), false);
            mailSender.send(mimeMessage);

            NotifySendSchema.Result result = new NotifySendSchema.Result();
            result.setProvider(getProvider());
            result.setChannel(request.getChannel());
            result.setSuccess(true);
            result.setRequestId(requestId);
            result.setMessageId(requestId);
            result.setCode("SUCCESS");
            result.setMessage("Sent successfully");
            return result;
        } catch (MessagingException | MailException e) {
            throw new IllegalStateException("Failed to send mail by exmail", e);
        }
    }
}
