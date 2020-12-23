package com.shawntime.architect.notes.concurrency.container.copyonwritearraylist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.shawntime.architect.notes.concurrency.SleepUtils;

/**
 *
 */
public class CopyOnWriteArrayListTest {

    private static final List<String> blackList = new ArrayList<>();

    static {
        blackList.add("13511013271");
        blackList.add("13511013642");
        blackList.add("13511012673");
        blackList.add("13511012614");
        blackList.add("13511011675");
        blackList.add("13511011626");
        blackList.add("13511011627");
        blackList.add("13511018678");
        blackList.add("13511019979");
        blackList.add("13511014660");
        blackList.add("13511015681");
    }

    public static void main(String[] args) {

        final BlackListMobileCache cache = new BlackListMobileCache();

        for (int i = 0; i < 30; ++i) {
            new Thread(() -> {
                while (true) {
                    String userMobile = blackList.get(ThreadLocalRandom.current().nextInt(blackList.size()));
                    boolean blackList = cache.isBlackList(userMobile);
                    SleepUtils.sleepBySeconds(1);
                    System.out.println(userMobile + " : " + blackList);
                }
            }, "read-" + i).start();
        }

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            String userMobile = blackList.get(ThreadLocalRandom.current().nextInt(blackList.size()));
            cache.addBlackList(userMobile);
        }, 1000, 100, TimeUnit.MICROSECONDS);
    }
}
