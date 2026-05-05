package com.qm.base.payment.schema;

import com.qm.base.payment.enums.PaymentProviderType;
import com.qm.base.payment.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 查询支付单协议模型。
 */
public class PayQuerySchema {

    /**
     * 查询支付单输入模型。
     */
    @Data
    public static class Input {

        /**
         * 支付渠道。
         */
        private PaymentProviderType provider;

        /**
         * 商户侧支付单号。
         */
        private String merchantOrderNo;
    }

    /**
     * 查询支付单输出模型。
     */
    @Data
    public static class Output {

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
         * 渠道原始状态。
         */
        private String rawStatus;
    }
}
