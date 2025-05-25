package com.qm.base.shared.logger;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
public class DemoService {
    @Log
    public String testLog(Map msg) {
        System.out.println("我是 testLog()");
        return "test11111";
    }
}
