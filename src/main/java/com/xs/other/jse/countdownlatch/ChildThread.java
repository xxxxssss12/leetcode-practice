package com.xs.other.jse.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class ChildThread implements Runnable {
    private CountDownLatch latch;
    public ChildThread(CountDownLatch latch) {
        this.latch = latch;
    }
    @Override
    public void run() {
        System.out.println("线程:" + Thread.currentThread().getName() + "...启动...");
        try {
            Thread.sleep(10000);
            System.out.println("线程:" + Thread.currentThread().getName() + "...结束...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }
}
