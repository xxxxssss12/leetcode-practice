package com.xs.other.jse.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        // CountDownLatch是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行。
        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        /*
        与CountDownLatch的第一次交互是主线程等待其他线程。主线程必须在启动其他线程后立即调用CountDownLatch.await()方法。
        这样主线程的操作就会在这个方法上阻塞，直到其他线程完成各自的任务。
        其他N 个线程必须引用闭锁对象，因为他们需要通知CountDownLatch对象，他们已经完成了各自的任务。这种通知机制是通过 CountDownLatch.countDown()方法来完成的；
        每调用一次这个方法，在构造函数中初始化的count值就减1。
        所以当N个线程都调 用了这个方法，count的值等于0，然后主线程就能通过await()方法，恢复执行自己的任务。
         */
        for (int i=0; i<10; i++) {
            executor.execute(new ChildThread(countDownLatch));
        }
        System.out.println("主线程开始等待！");
        countDownLatch.await();
        System.out.println("主线程执行完毕！");
        executor.shutdown();
    }
}
