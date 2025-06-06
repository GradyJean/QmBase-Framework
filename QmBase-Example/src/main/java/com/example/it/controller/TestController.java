package com.example.it.controller;

import com.qm.base.core.common.model.Result;
import com.qm.base.shared.logger.annotation.Log;
import com.qm.base.shared.logger.core.QmLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class TestController {
    @Log
    @RequestMapping("test")
    public Result<String> test() {
        QmLog.info("test");
        log.info("test");
        return Result.SUCCESS("index");
    }
}
