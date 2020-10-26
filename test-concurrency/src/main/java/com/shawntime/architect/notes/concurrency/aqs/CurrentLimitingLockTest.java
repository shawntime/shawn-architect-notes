package com.shawntime.architect.notes.concurrency.aqs;

import java.util.concurrent.locks.Lock;

public class CurrentLimitingLockTest {

    private static Lock limitLock = new CurrentLimitingLock(10);

    public static void main(String[] args) {

        for (int i = 0; i < 100; ++i) {
            new MyThread("thread-" + i).start();
        }
    }

    private static class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            limitLock.lock();
            try {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                limitLock.unlock();
            }
        }
    }
}
