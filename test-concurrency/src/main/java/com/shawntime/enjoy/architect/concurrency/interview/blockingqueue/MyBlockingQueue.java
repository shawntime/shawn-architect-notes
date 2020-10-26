package com.shawntime.enjoy.architect.concurrency.interview.blockingqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义一个有界阻塞队列
 */
public class MyBlockingQueue<T> {

    private List<T> dataList = new ArrayList<>();

    private int maxSize;

    private Lock lock = new ReentrantLock();

    private Condition prodCondition = lock.newCondition();

    private Condition customCondition = lock.newCondition();

    public MyBlockingQueue(int size) {
        this.maxSize = size;
    }

    public void add(T data) {
        lock.lock();
        try {
            while (dataList.size() >= maxSize) {
                prodCondition.await();
            }
            dataList.add(data);
            maxSize++;
            customCondition.signalAll();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T remove() {
        T data = null;
        lock.lock();
        try {
            while (dataList.size() <= 0) {
                customCondition.await();
            }
            maxSize--;
            data = dataList.remove(0);
            prodCondition.signalAll();
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return data;
    }

    public int size() {
        lock.lock();
        try {
            return dataList.size();
        } finally {
            lock.unlock();
        }
    }

}
