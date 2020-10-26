package com.shawntime.architect.notes.concurrency.waitandnotify.pool;

import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBPoolTest {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger get = new AtomicInteger(0);
        AtomicInteger noGet = new AtomicInteger(0);
        int threadCount = 20;
        int count = 50;
        DBPool dbPool = new DBPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; ++i) {
            Worker worker = new Worker("worker-" + i, count, get, noGet, dbPool, countDownLatch);
            worker.start();
        }
        countDownLatch.await();

        System.out.println("共尝试获取连接次数: " + (get.get() + noGet.get()));
        System.out.println("成功次数: " + get.get());
        System.out.println("失败次数: " + noGet.get());
    }

    private static class Worker extends Thread {

        private int count;

        private AtomicInteger get;

        private AtomicInteger noGet;

        private DBPool dbPool;

        private CountDownLatch countDownLatch;

        public Worker(String threadName, int count,
                      AtomicInteger get, AtomicInteger noGet, DBPool dbPool, CountDownLatch countDownLatch) {
            super(threadName);
            this.count = count;
            this.get = get;
            this.noGet = noGet;
            this.dbPool = dbPool;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < count; ++i) {
                    MyConnection connection = dbPool.getConnection(1000);
                    System.out.println(Thread.currentThread().getName() + "获取连接...");
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            System.out.println(Thread.currentThread().getName() + "释放连接...");
                            dbPool.releaseConnection(connection);
                            get.incrementAndGet();
                        }
                    } else {
                        noGet.incrementAndGet();
                    }
                }
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
