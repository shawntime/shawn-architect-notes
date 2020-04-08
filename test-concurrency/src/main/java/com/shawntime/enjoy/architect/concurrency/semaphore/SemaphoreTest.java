package com.shawntime.enjoy.architect.concurrency.semaphore;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

import com.shawntime.enjoy.architect.concurrency.SleepUtils;

/**
 *  Semaphore信号量模拟学生去食堂取餐
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        // 定义餐厅有10个窗口可同时取餐
        int windowNum = 10;
        DiningRoom diningRoom = new DiningRoom(windowNum);

        int studentNum = 100;
        CountDownLatch countDownLatch = new CountDownLatch(studentNum);
        long startTime = System.currentTimeMillis();
        // 定义100个学生取餐
        for (int i = 1; i <= studentNum; ++i) {
            Student student = new Student("student-" + i, diningRoom, countDownLatch);
            student.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(studentNum + "个同学在" + windowNum + "个窗口取餐耗时：" + (System.currentTimeMillis() - startTime));
    }

    private static class Student extends Thread {

        private DiningRoom diningRoom;

        private CountDownLatch countDownLatch;

        public Student(String name, DiningRoom diningRoom, CountDownLatch countDownLatch) {
            super(name);
            this.diningRoom = diningRoom;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                diningRoom.takeFood();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    private static class DiningRoom {

        private Semaphore semaphore;

        public DiningRoom(int windowNum) {
            this.semaphore = new Semaphore(windowNum, false);
        }

        public void takeFood() {

            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "进入食堂，等待取餐");
            try {
                semaphore.acquire();
                System.out.println(threadName + "进入窗口，正在取餐");
                int random = ThreadLocalRandom.current().nextInt(10);
                SleepUtils.sleepBySeconds(random);
                System.out.println(threadName + "取餐完毕，释放窗口，耗时：" + random + "秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }
}
