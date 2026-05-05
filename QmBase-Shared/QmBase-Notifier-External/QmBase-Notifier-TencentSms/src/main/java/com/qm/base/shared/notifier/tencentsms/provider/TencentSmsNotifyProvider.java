package com.qm.base.shared.notifier.tencentsms.provider;

import com.qm.base.shared.notifier.provider.NotifyProvider;
import com.qm.base.shared.notifier.schema.NotifySendSchema;
import com.qm.base.shared.notifier.tencentsms.config.TencentSmsNotifierProperties;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;

import java.util.List;

/**
 * 腾讯云短信通知 provider。
 * <p>
 * 当前实现面向验证码等模板短信场景：
 * `bizKey` 用于选择模板 ID，`content` 作为模板第一个参数下发。
 * 当前仅支持单手机号发送。
 */
public class TencentSmsNotifyProvider implements NotifyProvider {

    private final TencentSmsNotifierProperties properties;
    private final SmsClient smsClient;

    public TencentSmsNotifyProvider(TencentSmsNotifierProperties properties, SmsClient smsClient) {
        this.properties = properties;
        this.smsClient = smsClient;
    }

    @Override
    public String getProvider() {
        return properties.getProvider();
    }

    @Override
    public String getName() {
        return "腾讯云短信";
    }

    @Override
    public NotifySendSchema.Result send(NotifySendSchema.Input request) {
        String templateId = resolveTemplateId(request.getBizKey());
        List<String> receivers = request.getReceivers();
        if (receivers == null || receivers.isEmpty()) {
            throw new IllegalArgumentException("SMS receivers must not be empty");
        }
        if (receivers.size() != 1) {
            throw new IllegalArgumentException("Tencent SMS currently supports single receiver only");
        }
        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new IllegalArgumentException("SMS content must not be blank");
        }

        SendSmsRequest smsRequest = new SendSmsRequest();
        smsRequest.setSmsSdkAppId(properties.getSmsSdkAppId());
        smsRequest.setSignName(properties.getSignName());
        smsRequest.setTemplateId(templateId);
        smsRequest.setPhoneNumberSet(receivers.toArray(String[]::new));
        smsRequest.setTemplateParamSet(new String[]{request.getContent()});
        smsRequest.setSessionContext(request.getBizKey());

        SendSmsResponse response = execute(smsRequest);
        SendStatus[] sendStatuses = response.getSendStatusSet();
        SendStatus firstStatus = sendStatuses != null && sendStatuses.length > 0 ? sendStatuses[0] : null;

        NotifySendSchema.Result result = new NotifySendSchema.Result();
        result.setProvider(getProvider());
        result.setChannel(request.getChannel());
        result.setRequestId(response.getRequestId());
        result.setMessageId(firstStatus == null ? null : firstStatus.getSerialNo());
        result.setCode(firstStatus == null ? null : firstStatus.getCode());
        result.setMessage(firstStatus == null ? null : firstStatus.getMessage());
        result.setSuccess(firstStatus != null && "Ok".equalsIgnoreCase(firstStatus.getCode()));
        return result;
    }

    private SendSmsResponse execute(SendSmsRequest request) {
        try {
            return smsClient.SendSms(request);
        } catch (TencentCloudSDKException e) {
            throw new IllegalStateException("Failed to send sms by tencent cloud", e);
        }
    }

    private String resolveTemplateId(String bizKey) {
        if (bizKey == null || bizKey.isBlank()) {
            throw new IllegalArgumentException("SMS bizKey must not be blank");
        }
        String templateId = properties.getTemplateIds().get(bizKey);
        if (templateId == null || templateId.isBlank()) {
            throw new IllegalArgumentException("Missing tencent sms templateId for bizKey: " + bizKey);
        }
        return templateId;
    }
}
