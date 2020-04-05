package com.shawntime.enjoy.architect.concurrency.forkjoin.arraysum;

import java.util.concurrent.RecursiveTask;

import com.shawntime.enjoy.architect.concurrency.SleepUtils;

public class ArraySumTask extends RecursiveTask<Long> {

    private int[] array;

    private int startIndex;

    private int endIndex;

    private int minNum;

    public ArraySumTask(int[] array, int startIndex, int endIndex, int minNum) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.minNum = minNum;
    }

    @Override
    protected Long compute() {
        if (endIndex - startIndex > minNum) {
            // 已经是最小了
            Long result = 0L;
            for (int i = 0; i < array.length; ++i) {
                SleepUtils.sleepByMilliSeconds(1);
                result += array[i];
            }
            return result;
        } else {
            // 继续拆分
            int middle = (endIndex - startIndex) / 2;
            ArraySumTask leftTask = new ArraySumTask(array, startIndex, middle, minNum);
            ArraySumTask rightTask = new ArraySumTask(array, middle + 1, endIndex, minNum);
            // 提交任务
            invokeAll(leftTask, rightTask);
            return leftTask.join() + rightTask.join();
        }
    }
}
