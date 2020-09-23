package com.shawntime.enjoy.architect.concurrency.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 实现限流锁，同时最多只有N个线程处理，其他线程等待
 * 获取到锁的条件：N > 0
 * 阻塞条件 N <= 0
 */
public class CurrentLimitingLock implements Lock {

    private Sync sync;

    public CurrentLimitingLock(int limitNum) {
        sync = new Sync(limitNum);
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    private static final class Sync extends AbstractQueuedSynchronizer {

        public Sync(int maxNum) {
            setState(maxNum);
        }

        /**
         * 尝试去获取锁
         */
        @Override
        protected int tryAcquireShared(int acquireNum) {
            for (;;) {
                int state = getState();
                int newState = state - acquireNum;
                if (newState < 0) {
                    // 不足
                    return -1;
                }
                if (compareAndSetState(state, newState)) {
                    return newState;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int releaseNum) {
            for (;;) {
                int state = getState();
                int newState = state + releaseNum;
                if (compareAndSetState(state, newState)) {
                    return true;
                }
            }
        }

        protected Condition newCondition() {
            return new ConditionObject();
        }
    }
}
