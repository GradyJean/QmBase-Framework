package com.qm.base.shared.notifier.service.impl;

import com.qm.base.shared.notifier.handler.NotifySendHandler;
import com.qm.base.shared.notifier.provider.NotifyProvider;
import com.qm.base.shared.notifier.schema.NotifySendSchema;
import com.qm.base.shared.notifier.service.NotifierService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 统一通知服务默认实现。
 */
@Service
public class NotifierServiceImpl implements NotifierService {

    private final Map<String, NotifyProvider> notifyProviderMap;
    private final NotifySendHandler notifySendHandler;

    public NotifierServiceImpl(List<NotifyProvider> notifyProviders,
                               ObjectProvider<NotifySendHandler> notifySendHandlerProvider) {
        this.notifyProviderMap = notifyProviders.stream()
                .collect(Collectors.toMap(provider -> provider.getProvider().toLowerCase(), Function.identity()));
        this.notifySendHandler = notifySendHandlerProvider.getIfAvailable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotifySendSchema.Result send(NotifySendSchema.Input request) {
        NotifySendSchema.Result output = getProvider(request.getProvider()).send(request);
        if (notifySendHandler != null) {
            notifySendHandler.handle(request, output);
        }
        return output;
    }

    /**
     * 获取指定 provider 的通知实现。
     *
     * @param provider provider 编码
     * @return 通知实现
     */
    private NotifyProvider getProvider(String provider) {
        if (provider == null || provider.isBlank()) {
            throw new IllegalArgumentException("Notify provider must not be blank");
        }
        NotifyProvider notifyProvider = notifyProviderMap.get(provider.toLowerCase());
        if (notifyProvider == null) {
            throw new IllegalArgumentException("Unsupported notify provider: " + provider);
        }
        return notifyProvider;
    }
}
