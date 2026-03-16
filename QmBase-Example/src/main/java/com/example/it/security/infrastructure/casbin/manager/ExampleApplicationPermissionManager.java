package com.example.it.security.infrastructure.casbin.manager;

import com.example.it.common.constants.ScopeEnum;
import com.example.it.security.infrastructure.repository.mapper.ResourcePolicyMapper;
import com.example.it.security.infrastructure.repository.mapper.RoleMappingMapper;
import org.springframework.stereotype.Component;

@Component
public class ExampleApplicationPermissionManager extends ExamplePermissionManagerSupport {

    private static final String MODEL_PATH = "ApplicationPermission.conf";

    public ExampleApplicationPermissionManager(RoleMappingMapper roleMappingMapper,
                                               ResourcePolicyMapper resourcePolicyMapper) {
        super(ScopeEnum.APPLICATION.name(), MODEL_PATH, roleMappingMapper, resourcePolicyMapper);
    }
}
