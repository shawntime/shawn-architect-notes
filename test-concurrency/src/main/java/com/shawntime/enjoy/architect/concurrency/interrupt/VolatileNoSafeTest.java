package com.shawntime.enjoy.architect.concurrency.interrupt;

public class VolatileNoSafeTest {

    private static class MyRunnable implements Runnable {

        private volatile int count = 0;

        @Override
        public void run() {
            for (int i = 0; i < 10; ++i) {
                count++;
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread1 = new Thread(myRunnable, "thread1");
        Thread thread2 = new Thread(myRunnable, "thread2");
        Thread thread3 = new Thread(myRunnable, "thread3");
        Thread thread4 = new Thread(myRunnable, "thread4");
        Thread thread5 = new Thread(myRunnable, "thread5");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();

        System.out.println("Main end...");

    }
}
