package com.shawntime.architect.notes.concurrency.interview.threadnotice;

/**
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * 使用wait/notify实现
 *
 */
public class NotifyHoldingLock {

    private static Object object = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            System.out.println("t2启动");
            synchronized (object) {
                // 等待t1释放
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2线程结束");
                // 通知t1继续执行
                object.notify();
            }
        }, "t2").start();

        new Thread(() -> {
            System.out.println("t1启动");
            synchronized (object) {
                for (int i = 1; i <= 10; ++i) {
                    System.out.println(i);
                    if (i == 5) {
                        // 通知t2继续执行
                        object.notify();
                        // 等待t2释放
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, "t1").start();
    }
}
