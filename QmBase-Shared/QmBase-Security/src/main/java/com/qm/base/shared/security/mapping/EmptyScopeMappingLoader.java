package com.qm.base.shared.security.mapping;

import com.qm.base.shared.security.model.ScopeMappingEntry;

import java.util.List;

public class EmptyScopeMappingLoader implements ScopeMappingLoader {
    @Override
    public List<ScopeMappingEntry> loadScopeMappings() {
        return List.of();
    }
}
