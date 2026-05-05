package com.qm.base.shared.notifier.service;

import com.qm.base.shared.notifier.schema.NotifySendSchema;

/**
 * 统一通知服务接口。
 */
public interface NotifierService {

    /**
     * 发送通知。
     *
     * @param request 发送通知请求
     * @return 发送通知结果
     */
    NotifySendSchema.Result send(NotifySendSchema.Input request);
}
