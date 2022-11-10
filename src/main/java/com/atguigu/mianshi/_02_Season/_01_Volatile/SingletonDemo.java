package com.atguigu.mianshi._02_Season._01_Volatile;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kavin
 * @date 2022/11/8 21:43
 */
@Slf4j
public class SingletonDemo {

    private static SingletonDemo instance = null;

    private SingletonDemo() {
        log.info(Thread.currentThread().getName() + "\t 我是构造方法SingletonDemo");
    }

    /*public synchronized static SingletonDemo getInstance() {
        if (instance == null) {
            instance = new SingletonDemo();
        }
        return instance;
    }*/

    public static SingletonDemo getInstance() {
        if (instance == null) {
            // a 双重检查加锁多线程情况下会出现某个线程虽然这里已经为空，但是另外一个线程已经执行到d处
            synchronized (SingletonDemo.class) { // b

                // c不加volitale关键字的话有可能会出现尚未完全初始化就获取到的情况。原因是内存模型允许无序写入
                if (instance == null) {
                    // d 此时才开始初始化
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    /*public static void main(String[] args) {
        // 这里的 == 是比较内存地址
        log.info(SingletonDemo.getInstance() == SingletonDemo.getInstance());
        log.info(SingletonDemo.getInstance() == SingletonDemo.getInstance());
        log.info(SingletonDemo.getInstance() == SingletonDemo.getInstance());
        log.info(SingletonDemo.getInstance() == SingletonDemo.getInstance());
    }*/

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(SingletonDemo::getInstance, String.valueOf(i)).start();
        }
    }

}
