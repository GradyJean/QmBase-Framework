package com.qm.base.shared.notifier.provider.support;

import com.qm.base.shared.notifier.provider.NotifyProvider;
import com.qm.base.shared.notifier.schema.NotifySendSchema;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 默认日志通知 provider。
 * <p>
 * 用于本地开发、调试或尚未接入真实通知厂商时的兜底发送实现。
 */
@Slf4j
public class LogNotifyProvider implements NotifyProvider {

    /**
     * 默认 provider 编码。
     */
    public static final String PROVIDER = "log";

    @Override
    public String getProvider() {
        return PROVIDER;
    }

    @Override
    public String getName() {
        return "日志通知";
    }

    @Override
    public NotifySendSchema.Result send(NotifySendSchema.Input request) {
        String requestId = UUID.randomUUID().toString();
        log.info("Notifier send requestId={}, provider={}, channel={}, bizKey={}, receivers={}, subject={}, content={}",
                requestId,
                request.getProvider(),
                request.getChannel(),
                request.getBizKey(),
                request.getReceivers(),
                request.getSubject(),
                request.getContents());

        NotifySendSchema.Result output = new NotifySendSchema.Result();
        output.setProvider(getProvider());
        output.setChannel(request.getChannel());
        output.setSuccess(true);
        output.setRequestId(requestId);
        output.setMessageId(requestId);
        output.setCode("SUCCESS");
        output.setMessage("Logged successfully");
        return output;
    }
}
