package com.atguigu.mianshi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MianshiApplicationTests {

    @Test
    void contextLoads() {
        log.debug("1");
        log.info("2");
        log.warn("3");
        log.error("4");
        try {
            int i = 10 / 0;
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
