package com.shawntime.architect.notes.concurrency.interrupt;

public class ThreadLocalTest {

    private static final ThreadLocal<Integer> COUNT = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    private static class MyRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; ++i) {
                Integer value = COUNT.get();
                COUNT.set(++value);
            }
            System.out.println(Thread.currentThread().getName() + " : " + COUNT.get());
        }
    }

    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        Thread thread1 = new Thread(runnable, "thread1");
        Thread thread2 = new Thread(runnable, "thread2");
        Thread thread3 = new Thread(runnable, "thread3");
        Thread thread4 = new Thread(runnable, "thread4");
        Thread thread5 = new Thread(runnable, "thread5");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}
