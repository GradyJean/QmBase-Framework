package com.qm.base.payment.handler;

import com.qm.base.payment.schema.PayNotifySchema;

/**
 * 支付回调业务处理接口。
 * <p>
 * 业务系统实现此接口后，可在支付模块完成验签和解析后，
 * 接收统一的支付回调结果并执行入库、订单更新、消息投递等业务动作。
 */
public interface PaymentNotifyHandler {

    /**
     * 处理统一支付回调结果。
     *
     * @param notifyResult 支付回调统一结果
     */
    String handle(PayNotifySchema.Result notifyResult);
}
