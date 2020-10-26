package com.shawntime.architect.notes.concurrency.interrupt;

public class HasInterruptException {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread("my-thread");
        myThread.start();
        Thread.sleep(500);
        myThread.interrupt();
    }


    private static class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            while (!thread.isInterrupted()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("抛出异常， " + thread.getName() + " interrupt flag is " + thread.isInterrupted());
                    // 当线程抛出InterruptedException异常时，会重置中断标记位为false，需再次调用interrupt()方法才可推出循环
                    thread.interrupt();
                }
                System.out.println(thread.getName() + "运行中...");
            }
            System.out.println("跳出循环后" + thread.getName() + " interrupt flag is " + thread.isInterrupted());
        }
    }
}
