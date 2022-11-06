package com.atguigu.mianshi._02_Season._08_ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MyThreadPoolDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,
                                                                       5,
                                                                       1L,
                                                                       TimeUnit.SECONDS,
                                                                       new LinkedBlockingQueue<>(3),
                                                                       Executors.defaultThreadFactory(),
                                                                       new ThreadPoolExecutor.AbortPolicy());
        try {
            for (int i = 0; i < 8; i++) {
                threadPoolExecutor.submit(() -> {
                    log.info("办理业务");
                    //try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
                });
            }
            log.info("==> 当前的基本参数：{}", Runtime.getRuntime().availableProcessors());
            log.info("==> 当前的基本参数：{}", Runtime.getRuntime().freeMemory() / 1024 / 1024);
            log.info("==> 当前的基本参数：{}", Runtime.getRuntime().maxMemory() / 1024 / 1024);
            log.info("==> 当前的基本参数：{}", Runtime.getRuntime().totalMemory() / 1024 / 1024);
        } catch (Exception e) {
            log.error("==> 线程池抛出异常", e);
        } finally {
            threadPoolExecutor.shutdown();
        }


    }

}
