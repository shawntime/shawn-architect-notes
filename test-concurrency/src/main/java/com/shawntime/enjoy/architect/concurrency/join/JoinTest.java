package com.shawntime.enjoy.architect.concurrency.join;

import com.shawntime.enjoy.architect.concurrency.SleepUtils;

/**
 * join()方法测试
 */
public class JoinTest {

    private static class MyThread extends Thread {

        private Thread thread;

        public MyThread(String name, Thread thread) {
            super(name);
            this.thread = thread;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " waiting for " + thread.getName());
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完成...");
        }
    }

    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        for (int i = 0; i < 10; ++i) {
            MyThread myThread = new MyThread("my-thread" + i, thread);
            myThread.start();
            thread = myThread;
        }

        for (int i = 0; i < 3; ++i) {
            System.out.println("主线程睡眠" + (i + 1) + "秒");
            SleepUtils.sleepBySeconds(1);
        }

        System.out.println("Main方法执行完成...");
    }
}
