package com.qm.base.payment.service;

import com.qm.base.payment.enums.PaymentProviderType;
import com.qm.base.payment.handler.PaymentNotifyHandler;
import com.qm.base.payment.schema.PayNotifySchema;
import org.springframework.stereotype.Service;

/**
 * 支付回调处理服务。
 * <p>
 * 负责组织支付回调主流程，包括渠道回调解析、业务处理以及渠道响应内容返回。
 */
@Service
public class PaymentNotifyService {

    private final PaymentService paymentService;
    private final PaymentNotifyHandler paymentNotifyHandler;

    public PaymentNotifyService(PaymentService paymentService, PaymentNotifyHandler paymentNotifyHandler) {
        this.paymentService = paymentService;
        this.paymentNotifyHandler = paymentNotifyHandler;
    }

    /**
     * 处理支付渠道回调。
     *
     * @param provider 支付渠道
     * @param input    支付回调输入参数
     * @return 渠道要求的响应内容
     */
    public String handleNotify(PaymentProviderType provider, PayNotifySchema.Input input) {
        PayNotifySchema.Result result = paymentService.parseNotify(provider, input);
        return paymentNotifyHandler.handle(result);
    }
}
