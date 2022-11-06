package com.atguigu.mianshi._02_Season._08_ThreadPoolExecutor;

import java.util.concurrent.TimeUnit;

class HoldLockThread implements Runnable {

    private final String lockA;
    private final String lockB;

    /**
     * 言下之意就是，启动两个线程，传进来两把锁
     *
     * @param lockA
     * @param lockB
     */
    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有:" + lockA + "\t 尝试获得:" + lockB);

            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {throw new RuntimeException(e);}

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有:" + lockB + "\t 尝试获得:" + lockA);

                try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        }
    }
}

/**
 * 死锁是指，两个或两个以上的进程在执行过程中，
 * 因争夺资源造成的一种相互等待的现象，
 * 若无外力干涉，那他们都将无法推进下去
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA, lockB), "ThreadAAA").start();
        new Thread(new HoldLockThread(lockB, lockA), "ThreadBBB").start();

        // 怎么排查和解除死锁
        /**
         * linux ps -ef|grep xxx
         * windows下的java运行程序，也有类似ps的查看进程的命令，但是目前我们需要查看的只是java
         *
         *  jps=java ps   jps -l
         *      16578 com.atguigu.mianshi._02_Season._08_ThreadPoolExecutor.DeadLockDemo
         *
         *  jstack 16578
         *      Found one Java-level deadlock:
         *      =============================
         *      "ThreadBBB":
         *      waiting to lock monitor 0x00007fc28e81faa8 (object 0x000000076accf818, a java.lang.String),
         *      which is held by "ThreadAAA"
         *      "ThreadAAA":
         *      waiting to lock monitor 0x00007fc28e81d2c8 (object 0x000000076accf850, a java.lang.String),
         *      which is held by "ThreadBBB"
         *
         *      Java stack information for the threads listed above:
         *      ===================================================
         *      "ThreadBBB":
         *      at com.atguigu.mianshi._02_Season._08_ThreadPoolExecutor.HoldLockThread.run(DeadLockDemo.java:29)
         *      - waiting to lock <0x000000076accf818> (a java.lang.String)
         *      - locked <0x000000076accf850> (a java.lang.String)
         *      at java.lang.Thread.run(Thread.java:750)
         *      "ThreadAAA":
         *      at com.atguigu.mianshi._02_Season._08_ThreadPoolExecutor.HoldLockThread.run(DeadLockDemo.java:29)
         *      - waiting to lock <0x000000076accf850> (a java.lang.String)
         *      - locked <0x000000076accf818> (a java.lang.String)
         *      at java.lang.Thread.run(Thread.java:750)
         *
         *      Found 1 deadlock.
         */
    }

}
