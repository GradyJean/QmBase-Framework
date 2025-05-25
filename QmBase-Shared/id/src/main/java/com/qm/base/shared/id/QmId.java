package com.qm.base.shared.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ID生成器统一入口，基于 Snowflake 算法。
 * 支持通过配置初始化后全局静态调用。
 */
public class QmId {

    private static final Logger log = LoggerFactory.getLogger(QmId.class);

    private static volatile SnowflakeIdGenerator snowflake;

    /**
     * 初始化 ID 生成器（只初始化一次，线程安全）
     * @param workerId 工作节点 ID
     * @param datacenterId 数据中心 ID
     */
    public static void init(long workerId, long datacenterId) {
        if (snowflake == null) {
            synchronized (QmId.class) {
                if (snowflake == null) {
                    snowflake = new SnowflakeIdGenerator(workerId, datacenterId);
                    log.info("[QmId] Initialized with workerId={}, datacenterId={}", workerId, datacenterId);
                }
            }
        }
    }

    /**
     * 判断 ID 生成器是否已初始化
     * @return true 表示已初始化
     */
    public static boolean isInitialized() {
        return snowflake != null;
    }

    /**
     * 重置 ID 生成器（可重复初始化）
     * @param workerId 工作节点 ID
     * @param datacenterId 数据中心 ID
     */
    public static void reset(long workerId, long datacenterId) {
        synchronized (QmId.class) {
            snowflake = new SnowflakeIdGenerator(workerId, datacenterId);
        }
    }

    /**
     * 生成下一个 Long 类型 ID
     * @return long 类型的全局唯一 ID
     */
    public static long nextId() {
        if (snowflake == null) {
            throw new IllegalStateException("QmId is not initialized.");
        }
        return snowflake.nextId();
    }

    /**
     * 生成下一个 String 类型 ID（toString 形式）
     * @return 字符串形式的全局唯一 ID
     */
    public static String nextIdStr() {
        if (snowflake == null) {
            throw new IllegalStateException("QmId is not initialized.");
        }
        return snowflake.nextIdStr();
    }
}