package com.qm.base.shared.security.datascope.support;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据权限 SQL 拼接辅助工具
 */
public class DataScopeSqlHelper {

    /**
     * 构建部门过滤条件 SQL
     *
     * @param deptAlias     部门表别名（如 t）
     * @param deptIdList    允许访问的部门 ID 集合
     * @return 形如 "t.dept_id in (1,2,3)" 的 SQL 片段
     */
    public static String buildDeptCondition(String deptAlias, List<Long> deptIdList) {
        if (deptIdList == null || deptIdList.isEmpty()) {
            return "1=0"; // 无访问权限
        }
        String alias = deptAlias != null && !deptAlias.isBlank() ? deptAlias + "." : "";
        String ids = deptIdList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return alias + "dept_id in (" + ids + ")";
    }

    /**
     * 构建用户过滤条件 SQL
     *
     * @param userAlias 用户表别名（如 t）
     * @param userId 当前用户ID
     * @return 形如 "t.user_id = 123" 的 SQL 片段
     */
    public static String buildUserCondition(String userAlias, Long userId) {
        String alias = userAlias != null && !userAlias.isBlank() ? userAlias + "." : "";
        return alias + "user_id = " + userId;
    }
}