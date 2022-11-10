package com.atguigu.mianshi._02_Season._01_Volatile;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kavin
 * @date 2022/11/8 21:38
 */
@Slf4j
public class ResortSeqDemo {

    int a = 0;
    boolean flag = false;

    public void method01() {
        a = 1;
        flag = true;
    }

    public void method02() {
        if (flag) {
            a = a + 5;
            log.info("reValue:" + a);
        }
    }

}
