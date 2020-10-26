package com.shawntime.architect.notes.concurrency.interrupt;

public class InterruptTest {

    public static void main(String[] args) {
        MyThread myThread = new MyThread("my-thread");
        myThread.start();

        myThread.setPriority(6);

        // 中断myThread线程，myThread线程的中断标记位设置为true
        myThread.interrupt();

        System.out.println("myThread.isInterrupted() : " + myThread.isInterrupted());
        System.out.println("myThread.isInterrupted() : " + myThread.isInterrupted());
        System.out.println("myThread.isAlive() : " + myThread.isAlive());

        System.out.println("main.isInterrupted() : " + Thread.currentThread().isInterrupted());
    }
}
