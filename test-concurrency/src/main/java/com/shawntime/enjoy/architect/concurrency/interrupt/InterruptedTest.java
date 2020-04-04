package com.shawntime.enjoy.architect.concurrency.interrupt;

public class InterruptedTest {

    public static void main(String[] args) {

        MyThread myThread = new MyThread("my-thread");

        myThread.start();

        myThread.interrupt();

        Thread.currentThread().interrupt();

        System.out.println("执行前-myThread.isInterrupted() : " + myThread.isInterrupted());

        System.out.println("执行前-myThread.isInterrupted() : " + myThread.isInterrupted());

        System.out.println("执行前-main.isInterrupted() : " + Thread.currentThread().isInterrupted());

        System.out.println("执行前-main.isInterrupted() : " + Thread.currentThread().isInterrupted());

        System.out.println("main interrupted() : " + Thread.interrupted());

        System.out.println("main interrupted() : " + Thread.interrupted());

        System.out.println("执行后-myThread.isInterrupted() : " + myThread.isInterrupted());

        System.out.println("执行后-myThread.isInterrupted() : " + myThread.isInterrupted());

        System.out.println("执行后-main.isInterrupted() : " + Thread.currentThread().isInterrupted());

        System.out.println("执行后-main.isInterrupted() : " + Thread.currentThread().isInterrupted());
    }
}
