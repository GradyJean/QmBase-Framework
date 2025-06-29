package com.qm.base.shared.security.casbin.storage;

import java.util.List;

public interface PolicyLoader {
    /**
     * 加载指定权限域下的全部策略
     *
     * @param domain 权限域标识（如租户ID、项目ID、模块标识等）
     * @return 多行策略，每行是 List&lt;String&gt;，用于 Casbin 加载
     */
    List<List<String>> loadPolicies(String domain);
}
