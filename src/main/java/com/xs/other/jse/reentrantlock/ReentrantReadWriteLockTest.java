package com.xs.other.jse.reentrantlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xs
 * create time:2020-07-14 21:04
 **/
public class ReentrantReadWriteLockTest {

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();
        test.lock.readLock().lock();
        test.lock.writeLock().lock();
    }
}
