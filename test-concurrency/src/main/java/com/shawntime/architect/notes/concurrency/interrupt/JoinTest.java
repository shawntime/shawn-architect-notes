package com.shawntime.architect.notes.concurrency.interrupt;

public class JoinTest {

    private static class MyThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; ++i) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + "运行结束....");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.setName("my-thread");
        myThread.start();

        Thread.sleep(300);

        myThread.join();

        System.out.println(Thread.currentThread().getName() + "运行结束....");
    }
}
