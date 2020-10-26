package com.shawntime.enjoy.architect.concurrency.interview.threadnotice;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * 使用LockSupport实现
 *
 */
public class LockSupportLock {

    private static Thread t1 = null;
    private static Thread t2 = null;

    public static void main(String[] args) {

        t2 = new Thread(() -> {
            System.out.println("t2启动");
            t1.start();
            // t2等待
            LockSupport.park();
            System.out.println("t2线程结束");
            // t1继续执行
            LockSupport.unpark(t1);
        }, "t2");

        t1 = new Thread(() -> {
            System.out.println("t1启动");
            for (int i = 1; i <= 10; ++i) {
                System.out.println(i);
                if (i == 5) {
                    // 通知t2继续执行
                    LockSupport.unpark(t2);
                    // 等待t2释放
                    LockSupport.park();
                }

            }
        }, "t1");

        t2.start();
    }
}
