package com.shawntime.enjoy.architect.concurrency.forkjoin.arraysum;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ArraySumTaskTest {

    public static void main(String[] args) {
        int arrayLength = 5000;
        int[] array = ArrayUtils.makeArray(arrayLength);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int minNum = 100;
        ForkJoinTask<Long> task = new ArraySumTask(array, 0, array.length - 1, minNum);
        long startTime = System.currentTimeMillis();
        System.out.println("Task is Running...");
        forkJoinPool.invoke(task);
        Long value = task.join();
        System.out.println("totalNum = " + value);
        System.out.println("time : " + (System.currentTimeMillis() - startTime));

        System.out.println("--------------------");

        long start = System.currentTimeMillis();
        Long sumArray = ArraySumUtils.sumArray(array);
        System.out.println("sumArray : " + sumArray + ", time : " + (System.currentTimeMillis() - start));
    }
}
