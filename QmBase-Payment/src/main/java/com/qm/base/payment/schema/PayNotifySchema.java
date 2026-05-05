package com.qm.base.payment.schema;

import com.qm.base.payment.enums.PaymentProviderType;
import com.qm.base.payment.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 支付回调协议模型。
 */
public class PayNotifySchema {

    /**
     * 支付回调输入模型。
     */
    @Data
    public static class Input {

        /**
         * 回调请求头信息。
         */
        private Map<String, List<String>> headers;

        /**
         * 回调参数集合。
         * <p>
         * Servlet 会将 URL 参数与表单参数统一暴露为 parameter map，
         * 支付渠道实现侧按需从该参数集合中取值即可。
         */
        private Map<String, String> parameters;

        /**
         * 回调原始请求体。
         */
        private String body;
    }

    /**
     * 支付回调统一结果模型。
     */
    @Data
    public static class Result {

        /**
         * 支付渠道。
         */
        private PaymentProviderType provider;

        /**
         * 商户侧支付单号。
         */
        private String merchantOrderNo;

        /**
         * 渠道交易号。
         */
        private String channelTradeNo;

        /**
         * 当前统一支付状态。
         */
        private PaymentStatus status;

        /**
         * 支付金额，单位为分。
         */
        private Long amount;

        /**
         * 支付成功时间。
         */
        private LocalDateTime paidTime;

        /**
         * 原始回调报文。
         */
        private String rawPayload;
    }
}
