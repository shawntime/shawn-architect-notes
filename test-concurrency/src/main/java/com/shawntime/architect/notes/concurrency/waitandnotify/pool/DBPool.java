package com.shawntime.architect.notes.concurrency.waitandnotify.pool;

import java.util.LinkedList;

/**
 * 自定义实现线程池
 */
public class DBPool {

    private LinkedList<MyConnection> DB_POOL = new LinkedList<>();

    private static final int INITIALIZE_SIZE = 10;

    private Object lock = new Object();

    public DBPool(int initializeSize) {
        int size = initializeSize;
        if (initializeSize <= 0) {
            size = INITIALIZE_SIZE;
        }
        for (int i = 0; i < size; ++i) {
            MyConnection connection = new MyConnection();
            DB_POOL.add(connection);
        }
    }

    /**
     * 获取链接
     */
    public MyConnection getConnection(long waitTime) throws InterruptedException {
        synchronized (lock) {
            if (waitTime <= 0) {
                while (DB_POOL.isEmpty()) {
                    lock.wait();
                }
                return DB_POOL.removeFirst();
            } else {
                long endTimeStamp = System.currentTimeMillis() + waitTime;
                long remainTime = waitTime;
                while (DB_POOL.isEmpty() && remainTime > 0) {
                    lock.wait();
                    remainTime = endTimeStamp - System.currentTimeMillis();
                }
                if (DB_POOL.isEmpty()) {
                    return null;
                }
                return DB_POOL.removeFirst();
            }
        }
    }

    /**
     * 释放连接
     */
    public void releaseConnection(MyConnection connection) {
        if (connection == null) {
            return;
        }
        synchronized (lock) {
            DB_POOL.addLast(connection);
            lock.notifyAll();
        }
    }
}
