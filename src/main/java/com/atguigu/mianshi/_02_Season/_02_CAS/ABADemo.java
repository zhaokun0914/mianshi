package com.atguigu.mianshi._02_Season._02_CAS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {

    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        /*System.out.println("==========以下是ABA问题的产生==========");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
            // 暂停1秒钟t2线程，保证上面的t1线程完成了一次ABA操作
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            System.out.println(atomicReference.compareAndSet(100, 2022) + "\t" + atomicReference.get());
        }, "t2").start();

        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {throw new RuntimeException(e);}*/

        System.out.println("==========以下是ABA问题的解决==========");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("s1第一次获取的stamp为：" + stamp);
            // 暂停1秒钟线程s1，使s2获取到stamp
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println("s1第二次获取的stamp为：" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println("s1第三次获取的stamp为：" + atomicStampedReference.getStamp());
        }, "s1").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("s2第一次获取的stamp为：" + stamp);
            // 暂停3秒钟t2线程，保证上面的s1线程完成了一次ABA操作
            try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {throw new RuntimeException(e);}
            boolean result = atomicStampedReference.compareAndSet(100, 2022, stamp, stamp + 1);
            System.out.println("s2" + "\t修改成功与否：" + result + "\t当前最新实际版本号：" + atomicStampedReference.getStamp());
            System.out.println("s2" + "\t当前实际最新值：" + atomicStampedReference.getReference());
        }, "s2").start();

    }

}
