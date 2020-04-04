package com.shawntime.enjoy.architect.concurrency.interrupt;

public class DoemonThreadTest {

    private static class MyThread extends Thread {

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "执行中....");
                }

                System.out.println(Thread.currentThread().getName() + "线程执行结束...");
            } finally {
                System.out.println(Thread.currentThread().getName() + " finally...");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.setName("my-thread");
        myThread.setDaemon(true);
        myThread.start();
        Thread.sleep(100);
//        myThread.interrupt();
    }
}
