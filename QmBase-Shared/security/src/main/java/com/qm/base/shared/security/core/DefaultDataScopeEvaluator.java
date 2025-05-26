package com.qm.base.shared.security.core;

import com.qm.base.shared.security.api.DataScopeEvaluator;

import java.util.Collections;
import java.util.Map;

/**
 * 默认数据权限解析器，返回空过滤条件（业务系统可替换实现）
 */
public class DefaultDataScopeEvaluator implements DataScopeEvaluator {

    @Override
    public Map<String, Object> evaluate(Map<String, Object> context) {
        // 默认实现：返回空条件，表示不做数据权限过滤
        return Collections.emptyMap();
    }
}