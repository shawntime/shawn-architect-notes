package com.shawntime.enjoy.architect.concurrency.countdownlatch;

import java.util.concurrent.CountDownLatch;

import com.shawntime.enjoy.architect.concurrency.SleepUtils;

public class DataInitForCountDownLatch {

    private static class InitThread extends Thread {

        private CountDownLatch countDownLatch;

        public InitThread(String name, CountDownLatch countDownLatch) {
            super(name);
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                SleepUtils.sleepByMilliSeconds(100);
                System.out.println(Thread.currentThread().getName() + "线程初始化第一步完成");
            } finally {
                countDownLatch.countDown();
            }
            SleepUtils.sleepByMilliSeconds(100);
            System.out.println(Thread.currentThread().getName() + "线程初始化第二步完成");
        }
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 1; i < 4; ++i) {
            new InitThread("thread-" + i, countDownLatch).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程执行....");
    }
}
