package com.qm.base.shared.security.mapping;

import com.qm.base.shared.security.model.DomainMappingEntry;

import java.util.List;

public class EmptyDomainMappingLoader implements DomainMappingLoader {
    @Override
    public List<DomainMappingEntry> loadDomainMappings() {
        return List.of();
    }
}
