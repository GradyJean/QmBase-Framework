package com.qm.base.payment.provider;

import com.qm.base.payment.enums.PaymentProviderType;
import com.qm.base.payment.schema.PayCloseSchema;
import com.qm.base.payment.schema.PayCreateSchema;
import com.qm.base.payment.schema.PayNotifySchema;
import com.qm.base.payment.schema.PayQuerySchema;

/**
 * 支付渠道提供者接口。
 */
public interface PaymentProvider {

    /**
     * 获取当前支付渠道类型。
     *
     * @return 支付渠道类型
     */
    PaymentProviderType getProvider();

    /**
     * 获取渠道展示名称。
     *
     * @return 渠道名称
     */
    String getName();

    /**
     * 创建支付单。
     *
     * @param request 创建支付单请求
     * @return 创建支付单响应
     */
    PayCreateSchema.Output create(PayCreateSchema.Input request);

    /**
     * 查询支付单状态。
     *
     * @param request 查询支付单请求
     * @return 查询支付单响应
     */
    PayQuerySchema.Output query(PayQuerySchema.Input request);

    /**
     * 关闭未支付订单。
     *
     * @param request 关闭支付单请求
     * @return 关闭支付单响应
     */
    PayCloseSchema.Output close(PayCloseSchema.Input request);

    /**
     * 解析并校验支付回调。
     *
     * @param request 支付回调请求
     * @return 支付回调统一结果
     */
    PayNotifySchema.Result parseNotify(PayNotifySchema.Input request);

}
