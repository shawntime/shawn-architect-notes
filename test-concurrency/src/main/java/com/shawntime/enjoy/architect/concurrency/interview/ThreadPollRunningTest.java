package com.shawntime.enjoy.architect.concurrency.interview;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程轮询输出
 * 线程1：输出A-Z
 * 线程2：输出1-26
 * 打印出：A1B2C3....Z26
 */
public class ThreadPollRunningTest {

    private static final String LETTER = "ABCDEFGHIGKLMNOPQRSTUVWXYZ";

    private static Thread thread1 = null;

    private static Thread thread2 = null;

    public static void main(String[] args) {

        thread2 = new Thread(() -> {
            int size = 1;
            while (size < 27) {
                LockSupport.park();
                System.out.print(size++);
                LockSupport.unpark(thread1);
            }
        }, "t1");

        thread1 = new Thread(() -> {
            int index = 0;
            do {
                System.out.print(LETTER.charAt(index++));
                LockSupport.unpark(thread2);
                LockSupport.park();
            } while (index < LETTER.length());
        }, "t1");

        thread1.start();
        thread2.start();
    }
}
