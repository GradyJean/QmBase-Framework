package com.qm.base.payment.schema;

import com.qm.base.payment.enums.PaymentProviderType;
import com.qm.base.payment.enums.PaymentStatus;
import lombok.Data;

/**
 * 关闭支付单协议模型。
 */
public class PayCloseSchema {

    /**
     * 关闭支付单输入模型。
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
     * 关闭支付单输出模型。
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
         * 是否关闭成功。
         */
        private boolean success;

        /**
         * 当前统一支付状态。
         */
        private PaymentStatus status;

        /**
         * 渠道原始状态。
         */
        private String rawStatus;
    }
}
