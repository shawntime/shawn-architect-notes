package com.shawntime.enjoy.architect.concurrency.forkjoin.arraysum;

import com.shawntime.enjoy.architect.concurrency.SleepUtils;

public class ArraySumUtils {

    public static Long sumArray(int[] array) {
        Long result = 0L;
        for (int i = 0; i < array.length; ++i) {
            SleepUtils.sleepByMilliSeconds(1);
            result += array[i];
        }
        return result;
    }
}
