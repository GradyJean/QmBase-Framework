package com.qm.base.payment.wechat.provider;

import com.qm.base.payment.enums.PaymentProviderType;
import com.qm.base.payment.enums.PaymentStatus;
import com.qm.base.payment.provider.PaymentProvider;
import com.qm.base.payment.schema.PayCloseSchema;
import com.qm.base.payment.schema.PayCreateSchema;
import com.qm.base.payment.schema.PayNotifySchema;
import com.qm.base.payment.schema.PayQuerySchema;
import com.qm.base.payment.wechat.config.WechatPaymentProperties;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.CloseOrderRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import com.wechat.pay.java.service.payments.nativepay.model.QueryOrderByOutTradeNoRequest;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * 微信支付 provider 实现。
 */
public class WechatPaymentProvider implements PaymentProvider {

    private static final String HEADER_SERIAL = "Wechatpay-Serial";
    private static final String HEADER_TIMESTAMP = "Wechatpay-Timestamp";
    private static final String HEADER_NONCE = "Wechatpay-Nonce";
    private static final String HEADER_SIGNATURE = "Wechatpay-Signature";
    private static final String HEADER_SIGN_TYPE = "Wechatpay-Signature-Type";

    private final WechatPaymentProperties properties;
    private final NativePayService nativePayService;
    private final NotificationParser notificationParser;

    public WechatPaymentProvider(WechatPaymentProperties properties,
                                 NativePayService nativePayService,
                                 NotificationParser notificationParser) {
        this.properties = properties;
        this.nativePayService = nativePayService;
        this.notificationParser = notificationParser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentProviderType getProvider() {
        return PaymentProviderType.WECHAT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "微信";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayCreateSchema.Output create(PayCreateSchema.Input request) {
        PrepayRequest prepayRequest = new PrepayRequest();
        prepayRequest.setAppid(properties.getAppId());
        prepayRequest.setMchid(properties.getMerchantId());
        prepayRequest.setDescription(request.getSubject());
        prepayRequest.setOutTradeNo(request.getMerchantOrderNo());
        prepayRequest.setNotifyUrl(properties.getNotifyUrl());
        prepayRequest.setAttach(request.getAttach());
        if (request.getExpireTime() != null) {
            prepayRequest.setTimeExpire(request.getExpireTime().atOffset(OffsetDateTime.now().getOffset()).toString());
        }

        Amount amount = new Amount();
        amount.setTotal(Math.toIntExact(request.getAmount()));
        prepayRequest.setAmount(amount);

        PrepayResponse response = nativePayService.prepay(prepayRequest);
        PayCreateSchema.Output output = new PayCreateSchema.Output();
        output.setProvider(PaymentProviderType.WECHAT);
        output.setMerchantOrderNo(request.getMerchantOrderNo());
        output.setStatus(PaymentStatus.CREATED);
        output.setCodeUrl(response.getCodeUrl());
        output.setExpireTime(request.getExpireTime());
        return output;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayQuerySchema.Output query(PayQuerySchema.Input request) {
        QueryOrderByOutTradeNoRequest queryRequest = new QueryOrderByOutTradeNoRequest();
        queryRequest.setMchid(properties.getMerchantId());
        queryRequest.setOutTradeNo(request.getMerchantOrderNo());
        return toQueryResult(nativePayService.queryOrderByOutTradeNo(queryRequest));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayCloseSchema.Output close(PayCloseSchema.Input request) {
        CloseOrderRequest closeOrderRequest = new CloseOrderRequest();
        closeOrderRequest.setMchid(properties.getMerchantId());
        closeOrderRequest.setOutTradeNo(request.getMerchantOrderNo());
        nativePayService.closeOrder(closeOrderRequest);

        PayCloseSchema.Output output = new PayCloseSchema.Output();
        output.setProvider(PaymentProviderType.WECHAT);
        output.setMerchantOrderNo(request.getMerchantOrderNo());
        output.setSuccess(true);
        output.setStatus(PaymentStatus.CLOSED);
        output.setRawStatus(Transaction.TradeStateEnum.CLOSED.name());
        return output;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayNotifySchema.Result parseNotify(PayNotifySchema.Input request) {
        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(getRequiredHeader(request, HEADER_SERIAL))
                .timestamp(getRequiredHeader(request, HEADER_TIMESTAMP))
                .nonce(getRequiredHeader(request, HEADER_NONCE))
                .signature(getRequiredHeader(request, HEADER_SIGNATURE))
                .signType(getHeader(request, HEADER_SIGN_TYPE))
                .body(request.getBody())
                .build();
        Transaction transaction = notificationParser.parse(requestParam, Transaction.class);

        PayNotifySchema.Result result = new PayNotifySchema.Result();
        result.setProvider(PaymentProviderType.WECHAT);
        result.setMerchantOrderNo(transaction.getOutTradeNo());
        result.setChannelTradeNo(transaction.getTransactionId());
        result.setStatus(mapStatus(transaction.getTradeState()));
        if (transaction.getAmount() != null && transaction.getAmount().getTotal() != null) {
            result.setAmount(transaction.getAmount().getTotal().longValue());
        }
        result.setPaidTime(parseTime(transaction.getSuccessTime()));
        result.setRawPayload(request.getBody());
        return result;
    }

    /**
     * 将微信交易结果转换为统一查询结果。
     *
     * @param transaction 微信交易对象
     * @return 统一查询结果
     */
    private PayQuerySchema.Output toQueryResult(Transaction transaction) {
        PayQuerySchema.Output output = new PayQuerySchema.Output();
        output.setProvider(PaymentProviderType.WECHAT);
        output.setMerchantOrderNo(transaction.getOutTradeNo());
        output.setChannelTradeNo(transaction.getTransactionId());
        output.setStatus(mapStatus(transaction.getTradeState()));
        if (transaction.getAmount() != null && transaction.getAmount().getTotal() != null) {
            output.setAmount(transaction.getAmount().getTotal().longValue());
        }
        output.setPaidTime(parseTime(transaction.getSuccessTime()));
        output.setRawStatus(transaction.getTradeState() == null ? null : transaction.getTradeState().name());
        return output;
    }

    /**
     * 映射微信原始交易状态到统一支付状态。
     *
     * @param tradeState 微信交易状态
     * @return 统一支付状态
     */
    private PaymentStatus mapStatus(Transaction.TradeStateEnum tradeState) {
        if (tradeState == null) {
            return PaymentStatus.PAYING;
        }
        return switch (tradeState) {
            case SUCCESS -> PaymentStatus.SUCCESS;
            case NOTPAY, USERPAYING, ACCEPT -> PaymentStatus.PAYING;
            case CLOSED, REVOKED -> PaymentStatus.CLOSED;
            case PAYERROR -> PaymentStatus.FAILED;
            case REFUND -> PaymentStatus.SUCCESS;
        };
    }

    /**
     * 解析微信支付时间。
     *
     * @param value 微信支付时间字符串
     * @return 本地时间
     */
    private LocalDateTime parseTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return OffsetDateTime.parse(value).toLocalDateTime();
    }

    /**
     * 获取请求头值。
     *
     * @param request    回调请求
     * @param headerName 请求头名称
     * @return 请求头值
     */
    private String getHeader(PayNotifySchema.Input request, String headerName) {
        if (request.getHeaders() == null) {
            return null;
        }
        return request.getHeaders().entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(headerName))
                .findFirst()
                .map(entry -> entry.getValue().isEmpty() ? null : entry.getValue().get(0))
                .orElse(null);
    }

    /**
     * 获取必填请求头值。
     *
     * @param request    回调请求
     * @param headerName 请求头名称
     * @return 请求头值
     */
    private String getRequiredHeader(PayNotifySchema.Input request, String headerName) {
        String value = getHeader(request, headerName);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing wechat notify header: " + headerName);
        }
        return value;
    }
}
