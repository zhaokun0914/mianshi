package com.atguigu.mianshi._02_Season._04_Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁（也叫递归锁）
 * 指的是同一线程外层函数获得锁之后，内层递归函数仍然能获取到该锁的代码，在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 * <p>
 * 也就是说：线程可以进入任何一个 它持有的锁 所同步的代码块
 *
 * @author Kavin
 * @date 2022/11/12 19:48
 */
public class ReenterLockDemo {


    public static void main(String[] args) {
        Phone phone = new Phone();

        // callSynchronizedMehod(phone);

        callLockMethod(phone);
    }

    private static void callLockMethod(Phone phone) {
        new Thread(phone, "t3").start();
        new Thread(phone, "t4").start();
    }

    private static void callSynchronizedMehod(Phone phone) {
        // 两个线程操作资源列
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}

/**
 * 资源类
 */
class Phone implements Runnable {

    /**
     * 发送短信
     *
     * @throws Exception
     */
    public synchronized void sendSMS() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");

        // 在同步方法中，调用另外一个同步方法
        sendEmail();
    }

    /**
     * 发邮件
     *
     * @throws Exception
     */
    public synchronized void sendEmail() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail()");
    }

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        getLock();
    }

    private void getLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked getLock()");
            setLock();
        } finally {
            lock.unlock();
        }
    }

    private void setLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked setLock()");
        } finally {
            lock.unlock();
        }
    }


}