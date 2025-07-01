package com.qm.base.shared.security.mapping;

import com.qm.base.shared.security.model.DomainMappingEntry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnMissingBean(DomainMappingLoader.class)
public class EmptyDomainMappingLoader implements DomainMappingLoader {
    @Override
    public List<DomainMappingEntry> loadDomainMappings() {
        return List.of();
    }
}
