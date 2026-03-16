package com.example.it.user.interfaces.controller;

import com.example.it.security.infrastructure.casbin.manager.ExampleApplicationPermissionManager;
import com.example.it.security.infrastructure.casbin.manager.ExampleSystemPermissionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    // 这里可以添加用户相关的请求处理方法
    // 例如：获取用户信息、更新用户资料等
    private final ExampleSystemPermissionManager systemPermissionManager;
    private final ExampleApplicationPermissionManager applicationPermissionManager;

    public UserController(ExampleSystemPermissionManager systemPermissionManager,
                          ExampleApplicationPermissionManager applicationPermissionManager) {
        this.systemPermissionManager = systemPermissionManager;
        this.applicationPermissionManager = applicationPermissionManager;
    }

    // 示例方法
    @RequestMapping("test")
    public String test() {
        systemPermissionManager.notifyPolicyUpdate();
        applicationPermissionManager.notifyPolicyUpdate();
        return "UserController test method";
    }
}
