package com.atguigu.mianshi._02_Season._03_ConcurrenceCollection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集合类不安全的问题
 * @author Kavin
 * @date 2022/11/12 12:34
 */
public class MapNotSafeDemo {

    public static void main(String[] args) {
        // Map<String, Object> map = new HashMap<>();
        // Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, Object> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

}
