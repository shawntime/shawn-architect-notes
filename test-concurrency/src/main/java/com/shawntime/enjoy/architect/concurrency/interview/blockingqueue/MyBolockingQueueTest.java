package com.shawntime.enjoy.architect.concurrency.interview.blockingqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.shawntime.enjoy.architect.concurrency.SleepUtils;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生产者线程以及10个消费者线程的阻塞调用
 */
public class MyBolockingQueueTest {

    public static void main(String[] args) {
        MyBlockingQueue<String> queue = new MyBlockingQueue<>(3);

        AtomicInteger atomicInteger = new AtomicInteger(0);
        //启动消费者线程
        List<Thread> threads = new ArrayList<>();
        for(int i=0; i<10; i++) {
            Thread thread =new Thread(()-> {
                for(int j = 0; j < 5; j++) {
                    System.out.println(queue.remove());
                    atomicInteger.incrementAndGet();
                }
            }, "c" + i);
            threads.add(thread);
            thread.start();
        }

        SleepUtils.sleepBySeconds(2);

        //启动生产者线程
        for(int i=0; i<2; i++) {
            new Thread(()->{
                for(int j=0; j<25; j++) {
                    queue.add(Thread.currentThread().getName() + " " + j);
                }
            }, "p" + i).start();
        }


    }
}
