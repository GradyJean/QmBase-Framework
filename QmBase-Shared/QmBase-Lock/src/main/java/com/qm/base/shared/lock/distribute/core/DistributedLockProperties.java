package com.qm.base.shared.lock.distribute.core;

import com.qm.base.shared.lock.enums.DistributedLockType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 分布式锁配置项
 * YAML路径：qmbase.lock.distributed.type = redis / zookeeper
 */
@Data
@ConfigurationProperties(prefix = "qm.lock.distributed")
public class DistributedLockProperties {

    /**
     * 分布式锁实现类型（支持 redis 或 zookeeper）
     * 若为 null 且注解使用了 LockType.DISTRIBUTED，将在运行时抛出异常
     */
    private DistributedLockType type;
}