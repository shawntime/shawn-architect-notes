package com.shawntime.enjoy.architect.concurrency.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

import com.shawntime.enjoy.architect.concurrency.SleepUtils;

/**
 * countDownLatch：
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 4; ++i) {
            InitThread initThread = new InitThread("init-thread-" + i, countDownLatch);
            initThread.start();
        }

        BusinessThread businessThread = new BusinessThread("business-thread", countDownLatch);
        businessThread.start();

        new Thread(() -> {
            {
                try {
                    SleepUtils.sleepBySeconds(1);
                    System.out.println(Thread.currentThread().getName() + " really init worker step 1...");
                } finally {
                    countDownLatch.countDown();
                }
                try {
                    SleepUtils.sleepBySeconds(1);
                    System.out.println(Thread.currentThread().getName() + " really init worker step 2...");
                } finally {
                    countDownLatch.countDown();
                }

            }
        }, "single-thread").start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main do work....");
    }


    /**
     * 初始化线程
     */
    private static class InitThread extends Thread {

        private CountDownLatch countDownLatch;

        public InitThread(String name, CountDownLatch countDownLatch) {
            super(name);
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting really init...");
                int time = ThreadLocalRandom.current().nextInt(1000);
                SleepUtils.sleepByMilliSeconds(time);
            } finally {
                countDownLatch.countDown();
            }
            for (int i = 0; i < 3; ++i) {
                System.out.println(Thread.currentThread().getName() + " init continue working...");
            }
        }
    }

    /**
     * 业务线程
     */
    private static class BusinessThread extends Thread {

        private CountDownLatch countDownLatch;

        public BusinessThread(String name, CountDownLatch countDownLatch) {
            super(name);
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 3; ++i) {
                System.out.println(Thread.currentThread().getName() + " business do working...");
            }
        }
    }
}
