package com.shawntime.architect.notes.concurrency.semaphore.mutex;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

import com.shawntime.architect.notes.concurrency.SleepUtils;

public class MutexTest {

    public static void main(String[] args) {
        Mutex mutex = new Mutex();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 5; ++i) {
            MyThread myThread = new MyThread("thread-" + i, mutex, cyclicBarrier);
            myThread.start();
        }

    }

    private static class MyThread extends Thread {

        private Mutex mutex;

        private CyclicBarrier cyclicBarrier;

        public MyThread(String name, Mutex mutex, CyclicBarrier cyclicBarrier) {
            super(name);
            this.mutex = mutex;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "准备就绪...");
                cyclicBarrier.await();
                mutex.acquire();
                System.out.println(Thread.currentThread().getName() + "工作开始...");
                int random = ThreadLocalRandom.current().nextInt(1000);
                SleepUtils.sleepByMilliSeconds(random);
                System.out.println(Thread.currentThread().getName() + "工作结束...");
                mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
