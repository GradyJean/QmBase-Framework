package com.qm.base.payment.handler;

import com.qm.base.payment.schema.PayNotifySchema;

public class DefaultPaymentNotifyHandler implements PaymentNotifyHandler {
    @Override
    public String handle(PayNotifySchema.Result notifyResult) {
        throw new IllegalArgumentException("Not implemented");
    }
}
