package com.shawntime.architect.notes.concurrency.interview.threadnotice;

import java.util.concurrent.CountDownLatch;

/**
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * 使用CountDownLatch实现
 *
 */
public class CountDownLatchLock {

    private static CountDownLatch countDownLatch1 = new CountDownLatch(1);

    private static CountDownLatch countDownLatch2 = new CountDownLatch(1);

    public static void main(String[] args) {

        new Thread(() -> {
            System.out.println("t2启动");
            // t2等待
            try {
                countDownLatch2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2线程结束");
            // t1继续执行
            countDownLatch1.countDown();
        }, "t2").start();

        new Thread(() -> {
            System.out.println("t1启动");
            for (int i = 1; i <= 10; ++i) {
                System.out.println(i);
                if (i == 5) {
                    // 通知t2继续执行
                    countDownLatch2.countDown();
                    // 等待t2释放
                    try {
                        countDownLatch1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, "t1").start();
    }
}
