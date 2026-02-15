package com.qm.base.shared.id.api;

/**
 * 通用 ID 生成器接口，支持分布式主键生成策略。
 */
public interface IdGenerator {
    /**
     * 生成 Long 类型主键 ID
     *
     * @return 全局唯一 ID
     */
    String nextId();
}