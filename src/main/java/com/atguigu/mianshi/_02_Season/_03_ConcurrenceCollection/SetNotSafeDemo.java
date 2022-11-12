package com.atguigu.mianshi._02_Season._03_ConcurrenceCollection;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合类不安全的问题
 * @author Kavin
 * @date 2022/11/12 12:22
 */
public class SetNotSafeDemo {

    public static void main(String[] args) {

        // Set<String> set = new HashSet<>();
        // Set<String> set = Collections.synchronizedSet(new HashSet<>());
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

}
