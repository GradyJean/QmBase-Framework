package com.qm.base.shared.security.api;

import java.util.Map;

/**
 * 数据权限决策接口，由业务系统实现，用于生成数据权限过滤条件
 */
public interface DataScopeEvaluator {

    /**
     * 生成数据权限条件
     *
     * @param context 注解中的上下文信息（如别名）
     * @return SQL 片段或其他形式的过滤条件（如 ID 列表）
     */
    Map<String, Object> evaluate(Map<String, Object> context);
}