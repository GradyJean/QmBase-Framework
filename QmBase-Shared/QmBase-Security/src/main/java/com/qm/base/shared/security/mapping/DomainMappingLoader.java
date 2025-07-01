package com.qm.base.shared.security.mapping;

import com.qm.base.shared.security.model.DomainMappingEntry;

import java.util.List;

/**
 * DomainMappingLoader 接口用于加载域映射信息。
 * 提供了一个方法 loadDomainMappings()，用于获取域映射条目列表。
 */
public interface DomainMappingLoader {

    /**
     * 加载域映射信息。
     *
     * @return 域映射条目列表
     */
    List<DomainMappingEntry> loadDomainMappings();
}
