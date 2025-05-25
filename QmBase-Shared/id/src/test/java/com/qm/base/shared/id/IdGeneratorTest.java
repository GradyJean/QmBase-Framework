package com.qm.base.shared.id;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ID 生成器注入与功能测试
 */
@SpringBootTest(classes = QmIdAutoConfiguration.class)
public class IdGeneratorTest {


    @Test
    void shouldGenerateNumericId() {
        Long id = QmId.nextId();
        assertNotNull(id);
        System.out.println("生成的 Long ID: " + id);
    }

    @Test
    void shouldGenerateStringId() {
        String idStr = QmId.nextIdStr();
        assertNotNull(idStr);
        assertTrue(idStr.matches("\\d+")); // 校验是纯数字
        System.out.println("生成的 String ID: " + idStr);
    }
}
