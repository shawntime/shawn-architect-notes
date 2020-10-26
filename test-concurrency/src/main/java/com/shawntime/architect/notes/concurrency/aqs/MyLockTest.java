package com.shawntime.architect.notes.concurrency.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class MyLockTest {

    private static Lock lock = new MyLock();

    public static void main(String[] args) {
        System.out.println("main start...");
        List<MyThread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            MyThread myThread = new MyThread("thread" + i);
            threadList.add(myThread);
            myThread.start();
        }
        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("main end...");
    }


    private static class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            lock.lock();
            try {
                for (int i = 0; i < 10; ++i) {
                    System.out.println(Thread.currentThread().getName() + " --> " + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
