package com.atguigu.mianshi._02_Season._03_ConcurrenceCollection;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 集合类不安全的问题
 * @author Kavin
 * @date 2022/11/11 22:10
 */
@Slf4j
public class ListNotSafeDemo {

    public static void main(String[] args) {

        // List<String> list = new ArrayList<>();
        // List<String> list = Collections.synchronizedList(new ArrayList<>());
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

        /**
         * 1、故障现象
         *      java.util.ConcurrentModificationException
         * 2、导致原因
         *
         * 3、解决方案
         *      3.1 new Vector<>();
         *      3.2 Collections.synchronizedList(new ArrayList<>());
         *      3.3 new CopyOnWriteArrayList();
         *
         * 4、优化建议（同样的错误不犯第2次）
         */

    }


}
