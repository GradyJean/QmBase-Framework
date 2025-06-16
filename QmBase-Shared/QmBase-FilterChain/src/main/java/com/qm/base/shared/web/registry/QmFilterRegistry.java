package com.qm.base.shared.web.registry;

import com.qm.base.shared.web.filter.QmFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * QmFilterRegistry 用于注册和管理所有 QmFilter 的实例。
 * 可在系统初始化阶段统一注册所有自定义过滤器，并在 QmFilterChainProxy 中统一调用。
 */
public class QmFilterRegistry {

    private final List<QmFilter> filters = new ArrayList<>();

    /**
     * 注册过滤器
     * @param filter QmFilter 实例
     */
    public void register(QmFilter filter) {
        this.filters.add(filter);
    }

    /**
     * 获取所有已注册的过滤器
     * @return 不可变过滤器列表
     */
    public List<QmFilter> getFilters() {
        return Collections.unmodifiableList(filters);
    }
}
