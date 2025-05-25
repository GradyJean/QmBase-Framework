package com.qm.base.shared.base;

import com.qm.base.shared.base.dto.PageDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageDTOTest {

    @Test
    void testPageDTOWithData() {
        List<String> items = Arrays.asList("a", "b", "c");
        PageDTO<String> page = new PageDTO<>(100L, items);

        assertEquals(100L, page.getTotal());
        assertEquals(3, page.getRecords().size());
        assertEquals("a", page.getRecords().get(0));
    }
}