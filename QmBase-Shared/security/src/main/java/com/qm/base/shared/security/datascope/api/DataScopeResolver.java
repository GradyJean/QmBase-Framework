package com.qm.base.shared.security.datascope.api;

import java.util.Map;

/**
 * 数据权限 SQL 解析器接口
 * 用于根据上下文构建对应的数据范围 SQL 条件片段
 */
public interface DataScopeResolver {

    /**
     * 构建数据权限 SQL 条件
     *
     * @param context 数据权限注解与用户信息上下文
     * @return 拼接的 where 条件（不含 where 关键字）
     */
    String resolveCondition(Map<String, Object> context);
}