package com.shawntime.architect.notes.proto.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author mashaohua
 * @menu
 */
public interface Lock {

    void lock();

    boolean tryLock();

    boolean tryLock(int time, TimeUnit timeUnit);

    void unLock();
}
