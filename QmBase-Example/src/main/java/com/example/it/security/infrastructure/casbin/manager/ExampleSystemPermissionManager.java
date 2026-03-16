package com.example.it.security.infrastructure.casbin.manager;

import com.example.it.common.constants.ScopeEnum;
import com.example.it.security.infrastructure.repository.mapper.ResourcePolicyMapper;
import com.example.it.security.infrastructure.repository.mapper.RoleMappingMapper;
import org.springframework.stereotype.Component;

@Component
public class ExampleSystemPermissionManager extends ExamplePermissionManagerSupport {

    private static final String MODEL_PATH = "SystemPermission.conf";

    public ExampleSystemPermissionManager(RoleMappingMapper roleMappingMapper,
                                          ResourcePolicyMapper resourcePolicyMapper) {
        super(ScopeEnum.SYSTEM.name(), MODEL_PATH, roleMappingMapper, resourcePolicyMapper);
    }
}
