package com.shawntime.architect.notes.concurrency.thread;


public class MyThread {


    private volatile MyThread myThread = new MyThread();

    public static void main(String[] args) {

    }

    /**
     * 被回调的run方法
     */
    public void run() {
        System.out.println("我是被一个新线程启动....");
    }

    public void start() {
        start0();
    }

    private native void start0();
}
