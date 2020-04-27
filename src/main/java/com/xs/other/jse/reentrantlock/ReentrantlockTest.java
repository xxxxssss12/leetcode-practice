package com.xs.other.jse.reentrantlock;

import com.xs.MyThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantlockTest {

    private static Object lock = new Object();

    private static final ExecutorService pool = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new MyThreadFactory("test"), new ThreadPoolExecutor.AbortPolicy());
    private static int count = 0;
    private static CountDownLatch countDownLatch = new CountDownLatch(10);
    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock(false);
        for (int i=0; i<10; i++) {
            pool.submit(() -> {
                try {
                    lock.lock();
                    count += 5;
                    System.out.println("Thread=" + Thread.currentThread().getName() + ";..." + count);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        pool.shutdown();
    }
}
