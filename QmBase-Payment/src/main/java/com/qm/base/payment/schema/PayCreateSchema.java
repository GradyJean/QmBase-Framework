package com.qm.base.payment.schema;

import com.qm.base.payment.enums.PaymentProviderType;
import com.qm.base.payment.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
/**
 * 创建支付单协议模型。
 */
public class PayCreateSchema {

    /**
     * 创建支付单输入模型。
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

        /**
         * 订单标题。
         */
        private String subject;

        /**
         * 支付金额，单位为分。
         */
        private Long amount;

        /**
         * 支付单过期时间。
         */
        private LocalDateTime expireTime;

        /**
         * 业务透传附加信息。
         */
        private String attach;
    }

    /**
     * 创建支付单输出模型。
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
         * 用于前端生成二维码的地址内容。
         */
        private String codeUrl;

        /**
         * 支付单过期时间。
         */
        private LocalDateTime expireTime;
    }
}
