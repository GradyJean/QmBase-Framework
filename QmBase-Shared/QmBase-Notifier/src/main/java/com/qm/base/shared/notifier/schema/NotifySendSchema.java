package com.qm.base.shared.notifier.schema;

import com.qm.base.shared.notifier.enums.NotifyChannelType;
import lombok.Data;

import java.util.List;

/**
 * 发送通知协议模型。
 */
public class NotifySendSchema {

    /**
     * 发送通知输入模型。
     */
    @Data
    public static class Input {

        /**
         * provider 编码。
         */
        private String provider;

        /**
         * 通知通道类型。
         */
        private NotifyChannelType channel;

        /**
         * 业务标识。
         */
        private String bizKey;

        /**
         * 标题。
         */
        private String subject;

        /**
         * 内容。
         */
        private String[] contents;

        /**
         * 接收人集合。
         */
        private List<String> receivers;
    }

    /**
     * 发送通知结果模型。
     */
    @Data
    public static class Result {

        /**
         * provider 编码。
         */
        private String provider;

        /**
         * 通知通道类型。
         */
        private NotifyChannelType channel;

        /**
         * 是否发送成功。
         */
        private boolean success;

        /**
         * 请求标识。
         */
        private String requestId;

        /**
         * 消息标识。
         */
        private String messageId;

        /**
         * provider 原始响应码。
         */
        private String code;

        /**
         * provider 原始响应信息。
         */
        private String message;
    }
}
