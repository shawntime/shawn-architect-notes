package com.shawntime.enjoy.architect.concurrency.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 整型计数
 *
 */
public class TestAtomicInteger {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        List<MyThread> myThreads = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; ++i) {
            MyThread myThread = new MyThread("myThread" + i, countDownLatch);
            myThread.start();
            myThreads.addAll(myThreads);
        }
        countDownLatch.await();
        System.out.println("atomic : " + atomicInteger.get());
        System.out.println("count : " + count);
    }

    private static class MyThread extends Thread {

        private CountDownLatch countDownLatch;

        public MyThread(String name, CountDownLatch countDownLatch) {
            super(name);
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 1000; ++i) {
                    unSafe();
                    safe();
                }
                Thread.yield();
            } finally {
                countDownLatch.countDown();
            }

        }

        private void unSafe() {
            count++;
        }

        private void safe() {
            atomicInteger.incrementAndGet();
        }

    }
}
