package com.qm.base.payment.service.impl;

import com.qm.base.payment.enums.PaymentProviderType;
import com.qm.base.payment.provider.PaymentProvider;
import com.qm.base.payment.schema.PayCloseSchema;
import com.qm.base.payment.schema.PayCreateSchema;
import com.qm.base.payment.schema.PayNotifySchema;
import com.qm.base.payment.schema.PayQuerySchema;
import com.qm.base.payment.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 统一支付服务默认实现。
 * <p>
 * 负责按支付渠道路由到具体的支付提供者实现。
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private final Map<PaymentProviderType, PaymentProvider> paymentProviderMap;

    public PaymentServiceImpl(List<PaymentProvider> paymentProviders) {
        this.paymentProviderMap = paymentProviders.stream()
                .collect(Collectors.toMap(PaymentProvider::getProvider, Function.identity()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayCreateSchema.Output create(PayCreateSchema.Input request) {
        return getProvider(request.getProvider()).create(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayQuerySchema.Output query(PayQuerySchema.Input request) {
        return getProvider(request.getProvider()).query(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayCloseSchema.Output close(PayCloseSchema.Input request) {
        return getProvider(request.getProvider()).close(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayNotifySchema.Result parseNotify(PaymentProviderType provider, PayNotifySchema.Input request) {
        return getProvider(provider).parseNotify(request);
    }

    /**
     * 获取指定渠道的支付提供者。
     *
     * @param provider 支付渠道
     * @return 支付提供者实现
     */
    private PaymentProvider getProvider(PaymentProviderType provider) {
        PaymentProvider paymentProvider = paymentProviderMap.get(provider);
        if (paymentProvider == null) {
            throw new IllegalArgumentException("Unsupported payment provider: " + provider);
        }
        return paymentProvider;
    }
}
