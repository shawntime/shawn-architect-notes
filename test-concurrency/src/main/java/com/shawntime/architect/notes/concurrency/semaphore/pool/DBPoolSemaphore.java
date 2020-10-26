package com.shawntime.architect.notes.concurrency.semaphore.pool;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class DBPoolSemaphore {

    private LinkedList<Connection> DB_POOL;

    private static final int DEFAULT_INIT_NUM = 10;

    private Semaphore semaphore;

    public DBPoolSemaphore(int initNum) {
        if (initNum <= 0) {
            initNum = DEFAULT_INIT_NUM;
        }
        DB_POOL = new LinkedList<>();
        for (int i = 0; i < initNum; ++i) {
            MyConnection connection = new MyConnection();
            DB_POOL.add(connection);
        }
        semaphore = new Semaphore(initNum);
    }

    public Connection getConnection() throws InterruptedException {

        semaphore.acquire();
        synchronized (this) {
            return DB_POOL.removeFirst();
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        System.out.println("当前有"+semaphore.getQueueLength()+"个线程等待数据库连接！！"
                +"可用连接数:" + semaphore.availablePermits());
        synchronized (this) {
            DB_POOL.addLast(connection);
        }
        semaphore.release();

    }
}
