package com.qm.base.shared.security.datascope.support;

/**
 * 数据权限范围枚举
 */
public enum DataScopeType {

    /**
     * 全部数据
     */
    ALL,

    /**
     * 本人数据
     */
    SELF,

    /**
     * 所在部门数据
     */
    DEPT,

    /**
     * 所在部门及子部门数据
     */
    DEPT_AND_SUB,

    /**
     * 指定部门数据（角色配置时设定）
     */
    CUSTOM
}