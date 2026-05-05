package com.qm.base.shared.notifier.handler;

import com.qm.base.shared.notifier.schema.NotifySendSchema;

/**
 * 通知发送后处理 SPI。
 * <p>
 * 业务系统可选实现该接口，在通知发送完成后执行落库、审计、埋点等动作。
 */
public interface NotifySendHandler {

    /**
     * 处理通知发送结果。
     *
     * @param request 发送请求
     * @param result  发送结果
     */
    void handle(NotifySendSchema.Input request, NotifySendSchema.Result result);
}
