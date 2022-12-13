package ru.practicum.shareit.trait;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

class PageToolTest {

    private final PageTool pageTool = new PageTool() {
    };

    @Test
    void getPage() {
        int from = 2;
        int size = 3;
        String sort = "Start";
        Pageable page = pageTool.getPage(from, size, sort, Sort.Direction.DESC);
        assertNotNull(page);
    }
}