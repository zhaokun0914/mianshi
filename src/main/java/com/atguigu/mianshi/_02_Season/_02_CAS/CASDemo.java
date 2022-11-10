package com.atguigu.mianshi._02_Season._02_CAS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Kavin
 * @date 2022/11/8 22:18:56
 *
 * 1、CAS是什么？ ==> compareAndSet
 *      比较并交换
 * 2、
 */
@Slf4j
public class CASDemo {

    public static void main(String[] args) {
        // 创建一个原子类
        AtomicInteger atomicInteger = new AtomicInteger(5);

        /**
         * 一个是期望值，一个是更新值，但期望值和原来的值相同时，才能够更改
         * 假设三秒前，我拿的是5，也就是expect为5，然后我需要更新成 2019
         */
        log.info(atomicInteger.compareAndSet(5, 2019) + "\t current data: " + atomicInteger.get());
        log.info(atomicInteger.compareAndSet(5, 1024) + "\t current data: " + atomicInteger.get());
    }
}