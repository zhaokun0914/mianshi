package com.atguigu.mianshi._02_Season._02_CAS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子引用引起ABA问题
 * @author Kavin
 * @date 2022/11/10 22:59
 */
@Slf4j
public class ABADemo {

    // 普通的原子引用包装类
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    // 传递两个值，一个是初始值，一个是初始版本号
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        log.info("==========以下是ABA问题的产生==========");
        ABACause();
        try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {throw new RuntimeException(e);}

        //log.info("==========以下是ABA问题的解决==========");
        //ABASolve();
        //try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {throw new RuntimeException(e);}
    }

    private static void ABASolve() {

        new Thread(() -> {
            // 获取版本号
            int stamp = atomicStampedReference.getStamp();
            log.info("s1第一次获取的stamp为：" + stamp);

            // 暂停s1线程1秒钟，使s2获取到stamp
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}

            // 传入4个值，期望值，更新值，期望版本号，更新版本号
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            log.info("s1第二次获取的stamp为：" + atomicStampedReference.getStamp());

            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            log.info("s1第三次获取的stamp为：" + atomicStampedReference.getStamp());

        }, "s1").start();



        new Thread(() -> {
            // 获取版本号
            int stamp = atomicStampedReference.getStamp();
            log.info("s2第一次获取的stamp为：" + stamp);

            // 暂停t2线程3秒钟，保证上面的s1线程完成了一次ABA操作
            try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {throw new RuntimeException(e);}

            boolean result = atomicStampedReference.compareAndSet(100, 2022, stamp, stamp + 1);

            log.info("s2" + "\t修改成功与否：" + result + "\t当前最新实际版本号：" + atomicStampedReference.getStamp());
            log.info("s2" + "\t当前实际最新值：" + atomicStampedReference.getReference());

        }, "s2").start();
    }

    private static void ABACause() {
        new Thread(() -> {
            // 把100 改成 101 然后在改成100，也就是ABA
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
            // 暂停1秒钟t2线程，保证上面的t1线程完成了一次ABA操作
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}

            // 把100 改成 101 然后在改成100，也就是ABA
            log.info(atomicReference.compareAndSet(100, 2022) + "\t" + atomicReference.get());
        }, "t2").start();

    }

}
