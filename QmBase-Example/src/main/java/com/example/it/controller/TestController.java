package com.example.it.controller;

import com.qm.base.core.common.model.Result;
import com.qm.base.shared.cache.api.QmCache;
import com.qm.base.shared.cache.core.annotation.QmCacheable;
import com.qm.base.shared.lock.annotation.Lock;
import com.qm.base.shared.lock.enums.LockType;
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
    @QmCacheable(name = "222",key = "#param",ttl = 400,condition = "#param !=null")
    public Result<String> test(String param) {
        QmLog.info("test");
        log.info("test");
        return Result.SUCCESS("index");
    }
}
