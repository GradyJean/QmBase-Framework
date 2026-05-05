package com.qm.base.shared.notifier.provider;

import com.qm.base.shared.notifier.schema.NotifySendSchema;

/**
 * 通知 provider SPI。
 */
public interface NotifyProvider {

    /**
     * 获取 provider 编码。
     *
     * @return provider 编码
     */
    String getProvider();

    /**
     * 获取 provider 名称。
     *
     * @return provider 名称
     */
    String getName();

    /**
     * 发送通知。
     *
     * @param request 发送通知请求
     * @return 发送通知结果
     */
    NotifySendSchema.Result send(NotifySendSchema.Input request);
}
