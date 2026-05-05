package com.qm.base.payment.alipay.provider;

import com.alipay.v3.ApiException;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.model.AlipayTradeCloseModel;
import com.alipay.v3.model.AlipayTradeCloseResponseModel;
import com.alipay.v3.model.AlipayTradePrecreateModel;
import com.alipay.v3.model.AlipayTradePrecreateResponseModel;
import com.alipay.v3.model.AlipayTradeQueryModel;
import com.alipay.v3.model.AlipayTradeQueryResponseModel;
import com.alipay.v3.util.AlipaySignature;
import com.qm.base.payment.alipay.config.AlipayPaymentProperties;
import com.qm.base.payment.enums.PaymentProviderType;
import com.qm.base.payment.enums.PaymentStatus;
import com.qm.base.payment.provider.PaymentProvider;
import com.qm.base.payment.schema.PayCloseSchema;
import com.qm.base.payment.schema.PayCreateSchema;
import com.qm.base.payment.schema.PayNotifySchema;
import com.qm.base.payment.schema.PayQuerySchema;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 支付宝支付 provider 实现。
 */
public class AlipayPaymentProvider implements PaymentProvider {

    private static final DateTimeFormatter ALIPAY_NOTIFY_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final AlipayPaymentProperties properties;
    private final AlipayTradeApi alipayTradeApi;
    private final String alipayPublicKey;

    public AlipayPaymentProvider(AlipayPaymentProperties properties, AlipayTradeApi alipayTradeApi) {
        this.properties = properties;
        this.alipayTradeApi = alipayTradeApi;
        this.alipayPublicKey = readAlipayPublicKey(properties.getAlipayPublicKeyPath());
    }

    @Override
    public PaymentProviderType getProvider() {
        return PaymentProviderType.ALIPAY;
    }

    @Override
    public String getName() {
        return "支付宝";
    }

    @Override
    public PayCreateSchema.Output create(PayCreateSchema.Input request) {
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(request.getMerchantOrderNo());
        model.setSubject(request.getSubject());
        model.setTotalAmount(toAmountString(request.getAmount()));
        model.setNotifyUrl(properties.getNotifyUrl());
        model.setPassbackParams(request.getAttach());
        model.setBody(request.getAttach());
        model.setQrCodeTimeoutExpress(toTimeoutExpress(request.getExpireTime()));
        model.setTimeExpire(toTimeExpire(request.getExpireTime()));

        AlipayTradePrecreateResponseModel response = execute(() -> alipayTradeApi.precreate(model));

        PayCreateSchema.Output output = new PayCreateSchema.Output();
        output.setProvider(PaymentProviderType.ALIPAY);
        output.setMerchantOrderNo(response.getOutTradeNo() == null ? request.getMerchantOrderNo() : response.getOutTradeNo());
        output.setStatus(PaymentStatus.CREATED);
        output.setCodeUrl(response.getQrCode());
        output.setExpireTime(request.getExpireTime());
        return output;
    }

    @Override
    public PayQuerySchema.Output query(PayQuerySchema.Input request) {
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(request.getMerchantOrderNo());

        AlipayTradeQueryResponseModel response = execute(() -> alipayTradeApi.query(model));
        return toQueryResult(response);
    }

    @Override
    public PayCloseSchema.Output close(PayCloseSchema.Input request) {
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setOutTradeNo(request.getMerchantOrderNo());
        model.setNotifyUrl(properties.getNotifyUrl());

        AlipayTradeCloseResponseModel response = execute(() -> alipayTradeApi.close(model));

        PayCloseSchema.Output output = new PayCloseSchema.Output();
        output.setProvider(PaymentProviderType.ALIPAY);
        output.setMerchantOrderNo(response.getOutTradeNo() == null ? request.getMerchantOrderNo() : response.getOutTradeNo());
        output.setSuccess(true);
        output.setStatus(PaymentStatus.CLOSED);
        output.setRawStatus("TRADE_CLOSED");
        return output;
    }

    @Override
    public PayNotifySchema.Result parseNotify(PayNotifySchema.Input request) {
        Map<String, String> parameters = request.getParameters() == null ? Map.of() : request.getParameters();
        if (!verifyNotify(parameters)) {
            throw new IllegalArgumentException("Alipay notify signature verification failed");
        }

        PayNotifySchema.Result result = new PayNotifySchema.Result();
        result.setProvider(PaymentProviderType.ALIPAY);
        result.setMerchantOrderNo(parameters.get("out_trade_no"));
        result.setChannelTradeNo(parameters.get("trade_no"));
        result.setStatus(mapStatus(parameters.get("trade_status")));
        result.setAmount(parseAmount(parameters.get("total_amount")));
        result.setPaidTime(parseNotifyTime(parameters.get("gmt_payment")));
        result.setRawPayload(request.getBody());
        return result;
    }

    private PayQuerySchema.Output toQueryResult(AlipayTradeQueryResponseModel response) {
        PayQuerySchema.Output output = new PayQuerySchema.Output();
        output.setProvider(PaymentProviderType.ALIPAY);
        output.setMerchantOrderNo(response.getOutTradeNo());
        output.setChannelTradeNo(response.getTradeNo());
        output.setStatus(mapStatus(response.getTradeStatus()));
        output.setAmount(parseAmount(response.getTotalAmount()));
        output.setPaidTime(parseApiTime(response.getSendPayDate()));
        output.setRawStatus(response.getTradeStatus());
        return output;
    }

    private boolean verifyNotify(Map<String, String> parameters) {
        try {
            return AlipaySignature.rsaCheckV1(
                    parameters,
                    alipayPublicKey,
                    properties.getCharset(),
                    properties.getSignType()
            );
        } catch (ApiException e) {
            throw new IllegalStateException("Failed to verify alipay notify signature", e);
        }
    }

    private String readAlipayPublicKey(String publicKeyPath) {
        try {
            return Files.readString(Path.of(publicKeyPath));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read alipay public key file", e);
        }
    }

    private PaymentStatus mapStatus(String tradeStatus) {
        if (tradeStatus == null || tradeStatus.isBlank()) {
            return PaymentStatus.PAYING;
        }
        return switch (tradeStatus) {
            case "TRADE_SUCCESS", "TRADE_FINISHED" -> PaymentStatus.SUCCESS;
            case "WAIT_BUYER_PAY" -> PaymentStatus.PAYING;
            case "TRADE_CLOSED" -> PaymentStatus.CLOSED;
            default -> PaymentStatus.FAILED;
        };
    }

    private String toAmountString(Long amount) {
        return BigDecimal.valueOf(amount)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                .toPlainString();
    }

    private Long parseAmount(String amount) {
        if (amount == null || amount.isBlank()) {
            return null;
        }
        return new BigDecimal(amount)
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .longValue();
    }

    private LocalDateTime parseNotifyTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalDateTime.parse(value, ALIPAY_NOTIFY_TIME_FORMATTER);
    }

    private LocalDateTime parseApiTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return OffsetDateTime.parse(value).atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        } catch (Exception ignored) {
            return LocalDateTime.parse(value, ALIPAY_NOTIFY_TIME_FORMATTER);
        }
    }

    private String toTimeoutExpress(LocalDateTime expireTime) {
        if (expireTime == null) {
            return null;
        }
        long minutes = java.time.Duration.between(LocalDateTime.now(), expireTime).toMinutes();
        if (minutes <= 0) {
            minutes = 1;
        }
        return minutes + "m";
    }

    private String toTimeExpire(LocalDateTime expireTime) {
        if (expireTime == null) {
            return null;
        }
        return expireTime.atZone(ZoneId.systemDefault()).toOffsetDateTime().toString();
    }

    private <T> T execute(AlipayInvoker<T> invoker) {
        try {
            return invoker.invoke();
        } catch (ApiException e) {
            throw new IllegalStateException("Alipay api request failed", e);
        }
    }

    @FunctionalInterface
    private interface AlipayInvoker<T> {

        /**
         * 执行支付宝 SDK 调用。
         *
         * @return 支付宝返回结果
         * @throws ApiException SDK 异常
         */
        T invoke() throws ApiException;
    }
}
