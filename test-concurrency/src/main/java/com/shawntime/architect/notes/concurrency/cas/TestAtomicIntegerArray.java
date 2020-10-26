package com.shawntime.architect.notes.concurrency.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 原子数组
 */
public class TestAtomicIntegerArray {

    private static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);

    private static int[] array = new int[10];

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; ++i) {
            MyThread myThread = new MyThread("thread-" + i, countDownLatch);
            myThread.start();
        }

        countDownLatch.await();

        for (int i = 0; i < 10; ++i) {
            System.out.println("safe : " + atomicIntegerArray.get(i));
            System.out.println("unsafe : " + array[i]);
        }

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
                for (int i = 0; i < 10; ++i) {
                    for (int j = 0; j < 10000; ++j) {
                        safe(i);
                        unSafe(i);
                    }
                }
                Thread.yield();
            } finally {
                countDownLatch.countDown();
            }
        }

        private void safe(int index) {
            atomicIntegerArray.incrementAndGet(index);
        }

        private void unSafe(int index) {
            array[index] = array[index] + 1;
        }
    }
}
