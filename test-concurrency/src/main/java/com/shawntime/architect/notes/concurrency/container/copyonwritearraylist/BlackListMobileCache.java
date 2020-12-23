package com.shawntime.architect.notes.concurrency.container.copyonwritearraylist;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shawntime.architect.notes.concurrency.SleepUtils;

/**
 * 缓存
 */
public class BlackListMobileCache {

    private CopyOnWriteArrayList<String> cache = new CopyOnWriteArrayList();

    public boolean isBlackList(String userMobile) {
        return cache.contains(userMobile);
    }

    public void addBlackList(String userMobile) {
        SleepUtils.sleepBySeconds(3);
        cache.add(userMobile);
    }

    public void addAll(List<String> userMobileList) {
        cache.addAll(userMobileList);
    }
}
