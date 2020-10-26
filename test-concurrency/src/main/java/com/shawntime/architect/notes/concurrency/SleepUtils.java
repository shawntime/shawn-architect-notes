package com.shawntime.architect.notes.concurrency;

import java.util.concurrent.TimeUnit;

public class SleepUtils {

    public static void sleepByMilliSeconds(long time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepBySeconds(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepByMinutes(long time) {
        try {
            TimeUnit.MINUTES.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
