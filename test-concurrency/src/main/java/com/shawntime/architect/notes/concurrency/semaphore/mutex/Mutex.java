package com.shawntime.architect.notes.concurrency.semaphore.mutex;

import java.util.concurrent.Semaphore;

/**
 * semaphore实现互斥锁
 */
public class Mutex {

    private Semaphore semaphore = new Semaphore(1, true);

    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }

    public void release() {
        semaphore.release();
    }

    public boolean attempt(int ms) throws InterruptedException {
        return semaphore.tryAcquire(ms);
    }
}
