package com.xs.jse.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by xs on 2019/2/7.
 */
public class MutexLockTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开始");
        CountDownLatch latch = new CountDownLatch(5);
        MutexLock lock = new MutexLock();
        for (int i=0;i<5;i++) {
            new Thread(new TestRunnable(latch, i, lock)).start();
        }
        latch.await();
        System.out.println("主线程结束");
    }
}
class TestRunnable implements Runnable {
    private CountDownLatch latch;
    private Integer index;
    private MutexLock lock;

    public TestRunnable(CountDownLatch latch, Integer index, MutexLock lock) {
        this.latch = latch;
        this.index = index;
        this.lock = lock;
    }

    @Override
    public void run() {
        System.out.println("线程" + index + "启动...");
        lock.lock();
        System.out.println("线程" + index + "持有锁，睡5s...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
        System.out.println("线程" + index + "结束...");
        latch.countDown();
    }
}
