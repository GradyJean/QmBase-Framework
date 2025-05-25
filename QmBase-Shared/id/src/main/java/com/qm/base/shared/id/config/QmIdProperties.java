package com.qm.base.shared.id.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * QmBase ID 生成器配置项
 * <p>
 * 可通过 application.yml 配置以下参数：
 * qmbase.id.worker-id
 * qmbase.id.datacenter-id
 */
@Data
@ConfigurationProperties(prefix = "qm.base.id")
public class QmIdProperties {

    /**
     * 工作节点 ID（0 ~ 31），默认值为 0
     */
    private long workerId = 0L;

    /**
     * 数据中心 ID（0 ~ 31），默认值为 0
     */
    private long datacenterId = 0L;
}