package com.qm.base.payment.enums;

/**
 * 统一支付状态枚举。
 */
public enum PaymentStatus {

    /**
     * 已创建，等待用户支付。
     */
    CREATED,

    /**
     * 支付处理中。
     */
    PAYING,

    /**
     * 支付成功。
     */
    SUCCESS,

    /**
     * 支付单已关闭。
     */
    CLOSED,

    /**
     * 支付失败。
     */
    FAILED
}
