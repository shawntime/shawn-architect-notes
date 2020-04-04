package com.shawntime.enjoy.architect.concurrency.interrupt;

public class MyThread extends Thread {

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; ++i) {

            System.out.println("i : " + i);

            boolean interrupted = Thread.currentThread().isInterrupted();
            if (interrupted) {
                // 判断为中断状态时，执行推出操作
                System.out.println("通过this.isInterrupted()检测到中断");
                System.out.println("第一个interrupted()"+Thread.interrupted());
                System.out.println("第二个interrupted()"+Thread.interrupted());
                break;
            }
        }
    }
}
