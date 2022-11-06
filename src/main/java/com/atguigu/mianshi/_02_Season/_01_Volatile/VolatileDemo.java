package com.atguigu.mianshi._02_Season._01_Volatile;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
class MyData {
    volatile int number = 0;

    /**
     *  创建一个原子Integer包装类，默认为0
     */
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addTo60() {
        this.number = 60;

        // 输出修改后的值
        System.out.println(Thread.currentThread().getName() + "更新 number 值:" + number);
    }

    // 请注意，此时number前面是加了volatile关键字修饰的，volatile不保证原子性
    public void addPlusPlus() {
        this.number++;
    }

    public void addAtomic() {
        // 相当于 atomicInter ++
        atomicInteger.getAndIncrement();
    }
}

/**
 * Volatile Java虚拟机提供的轻量级同步机制<br/>
 * <p>
 * 可见性（及时通知）<br/>
 * 不保证原子性<br/>
 * 禁止指令重排<br/>
 * <p>
 * 1 验证volatile的可见性<br/>
 *  1.1 假如int number = 0; number变量前面没有添加volatile关键字修饰，没有可见性。<br/>
 *  1.2 添加了volatile，可以解决可见性问题。
 * <p>
 * 2 验证volatile不保证原子性<br/>
 *  2.1 原子性指的是什么意思？<br/>
 *      不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割。需要整体完整<br/>
 *      要么同时成功，要么同时失败<br/>
 *  2.2 volatile不保证原子性的案例演示<br/>
 *  2.3 为什么不能保证原子性?<br/>
 *
 */
@Slf4j
public class VolatileDemo {

    public static void main(String[] args) {
        // volatileForSeeTest();
        volatileForAtomicity();
    }

    /**
     * 验证volatile不保证原子性
     */
    private static void volatileForAtomicity() {
        MyData myData = new MyData();

        // 正常情况下20个1000相加应该等于20000，当20个线程都执行完，main线程获取的结果应该是20000
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            }, String.valueOf(i)).start();
        }

        // 需要等待上面20个线程都全部计算完成后，再用main线程取得最终的结果值看是多少？
        // 这里判断线程数是否大于2，为什么是2？因为默认是有两个线程的，一个main线程，一个gc线程
        while (Thread.activeCount() > 2) {
            // yield表示放弃当前对处理器的使用
            Thread.yield();
        }

        // 查看最终的值
        // 假设volatile保证原子性，那么输出的值应该为：  20 * 1000 = 20000
        log.info(Thread.currentThread().getName() + "线程, 最终 number的值为" + myData.number);
        log.info(Thread.currentThread().getName() + "线程, 最终 number的值为" + myData.atomicInteger);
    }

    /**
     * 此方法验证了以下场景
     *
     * volatile 可以保证可见性，物理内存的值被修改后，及时通知其他线程。
     */
    private static void volatileForSeeTest() {

        // 资源类
        MyData myData = new MyData();

        // AAA线程 实现了Runnable接口的，lambda表达式
        new Thread(() -> {

            log.info("进入" + Thread.currentThread().getName() + "线程");

            // 线程睡眠3秒，假设在进行运算
            try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {throw new RuntimeException(e);}

            // 修改number的值
            myData.addTo60();

        }, "AAA").start();

        log.info("进入" + Thread.currentThread().getName() + "线程, 获取到初始 number 值为:" + myData.number);

        while (myData.number == 0) {
            // main线程就一直在这里等待循环，直到number的值不等于零
        }

        // 按道理这个值是不可能打印出来的，因为主线程运行的时候，number的值为0，所以一直在循环
        // 如果能输出这句话，说明AAA线程在睡眠3秒后，更新的number的值，重新写入到主内存，并被main线程感知到了
        log.info("进入" + Thread.currentThread().getName() + "线程, 线程A修改之后 number 值为:" + myData.number);

        /*
         * 最后的输出为：
         *
         * 进入AAA线程
         * 进入main线程, 获取到初始 number 值为:0
         * AAA 更新 number 值:60
         * 进入main线程, 线程A修改之后 number 值为:60
         */
    }

}
