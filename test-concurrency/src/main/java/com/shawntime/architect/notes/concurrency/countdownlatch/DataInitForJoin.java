package com.shawntime.architect.notes.concurrency.countdownlatch;

import com.shawntime.architect.notes.concurrency.SleepUtils;

/**
 * join方法实现线程阻塞
 */
public class DataInitForJoin {

    private static class InitThread extends Thread {

        public InitThread(String name) {
            super(name);
        }

        @Override
        public void run() {

            SleepUtils.sleepByMilliSeconds(100);
            System.out.println(Thread.currentThread().getName() + "线程初始化第一步完成");

            SleepUtils.sleepByMilliSeconds(100);
            System.out.println(Thread.currentThread().getName() + "线程初始化第二步完成");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        InitThread initThread1 = new InitThread("thread-1");
        InitThread initThread2 = new InitThread("thread-2");
        InitThread initThread3 = new InitThread("thread-3");

        initThread1.start();
        initThread2.start();
        initThread3.start();

        initThread1.join();
        initThread2.join();
        initThread3.join();

        System.out.println("主线程继续执行....");


    }
}
