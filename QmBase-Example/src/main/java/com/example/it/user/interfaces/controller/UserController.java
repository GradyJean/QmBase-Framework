package com.example.it.user.interfaces.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    // 这里可以添加用户相关的请求处理方法
    // 例如：获取用户信息、更新用户资料等

    // 示例方法
    @RequestMapping("test")
    public String test() {
        return "UserController test method";
    }
}
