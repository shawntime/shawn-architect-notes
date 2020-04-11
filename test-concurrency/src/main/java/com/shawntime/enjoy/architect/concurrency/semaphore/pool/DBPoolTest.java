package com.shawntime.enjoy.architect.concurrency.semaphore.pool;

import java.sql.Connection;
import java.util.concurrent.ThreadLocalRandom;

import com.shawntime.enjoy.architect.concurrency.SleepUtils;

public class DBPoolTest {

    private static class BusinessThread extends Thread {

        private DBPoolSemaphore dbPool;

        public BusinessThread(String name, DBPoolSemaphore dbPool) {
            super(name);
            this.dbPool = dbPool;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            try {
                Connection connect = dbPool.getConnection();
                System.out.println("Thread_"+Thread.currentThread().getId()
                        +"_获取数据库连接共耗时【"+(System.currentTimeMillis()-start)+"】ms.");
                //模拟业务操作，线程持有连接查询数据
                SleepUtils.sleepByMilliSeconds(100 + ThreadLocalRandom.current().nextInt(1000));
                System.out.println("查询数据完成，归还连接！");
                dbPool.releaseConnection(connect);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) {
        DBPoolSemaphore dbPool = new DBPoolSemaphore(10);
        for (int i = 0; i < 50; i++) {
            Thread thread = new BusinessThread("thread-" + i, dbPool);
            thread.start();
        }
    }
}
