package com.xs.other.jse.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by xs on 2019/2/6.
 */
public class MutexLock {
    static class Sync extends AbstractQueuedSynchronizer {

        public Sync() {setState(100);}

        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(100, 1);
        }

        @Override
        protected boolean tryRelease(int arg) {
            setState(100);
            return true;
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(0);
    }

    public void unlock() {
        sync.release(0);
    }
}
