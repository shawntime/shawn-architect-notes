package com.shawntime.enjoy.architect.concurrency.forkjoin.arraysum;

import java.util.concurrent.ThreadLocalRandom;

public class ArrayUtils {

    public static int[] makeArray(int length) {
        int[] result = new int[length];
        for (int i = 0; i < length; ++i) {
            int value = ThreadLocalRandom.current().nextInt(length * 10);
            result[i] = value;
        }
        return result;
    }
}
