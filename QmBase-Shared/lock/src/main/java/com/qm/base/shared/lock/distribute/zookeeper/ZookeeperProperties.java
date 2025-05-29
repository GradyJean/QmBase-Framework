package com.qm.base.shared.lock.distribute.zookeeper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Zookeeper 配置项映射类，对应配置路径 qm.base.lock.zookeeper
 */
@Data
@ConfigurationProperties(prefix = "qm.lock.zookeeper")
public class ZookeeperProperties {

    /**
     * Zookeeper 连接地址（如：localhost:2181,host2:2181）
     */
    private String connectString;
}
