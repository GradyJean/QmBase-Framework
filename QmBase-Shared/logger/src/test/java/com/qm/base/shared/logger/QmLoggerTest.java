package com.qm.base.shared.logger;

import com.qm.base.shared.logger.core.QmLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

/**
 * QmLog 功能测试类：验证结构化日志与普通日志输出
 */
@SpringBootTest(classes = LoggerTestApp.class)
@ActiveProfiles("test")
@EnableAutoConfiguration
public class QmLoggerTest {
    @Autowired
    DemoService demoService;

    @Test
    void testLogAnnoAspect() {
        Map<String, String> map = new HashMap<>();
        map.put("test", "test");
        map.put("test2", "test2");
        map.put("test3", "test3");
        String log = demoService.testLog(map); // ✅ 使用代理对象调用，切面才能生效
    }

    @Test
    void testInfoWithModuleAction() {
        QmLog.info("用户模块", "创建用户", "userId=1001", "userName=张三");
    }

    @Test
    void testErrorWithException() {
        try {
            simulateError();
        } catch (Exception e) {
            QmLog.error("支付模块", "支付异常", e);
        }
    }

    @Test
    void testDebugWithSimpleMessage() {
        QmLog.debug("调试信息：当前状态 = {}", "READY");
    }

    @Test
    void testWarnWithModuleAction() {
        QmLog.warn("库存模块", "库存不足", "商品ID=SKU123", "当前库存=1");
    }

    @Test
    void testErrorWithMessage() {
        QmLog.error("系统级错误：{}", "无法连接 Redis");
    }

    @Test
    void testInfoPlainText() {
        QmLog.info("系统启动成功，端口号: {}", 8080);
    }

    private void simulateError() {
        throw new IllegalStateException("模拟异常：数据库连接失败");
    }
}