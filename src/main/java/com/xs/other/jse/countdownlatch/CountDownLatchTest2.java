package com.xs.other.jse.countdownlatch;

import com.xs.MyThreadFactory;

import java.util.concurrent.*;

/**
 * @author xiongshun
 * create time: 2020/6/18 17:21
 */
public class CountDownLatchTest2 {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService executor =
                new ThreadPoolExecutor(12,
                        12,
                        0,
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(12),
                        new MyThreadFactory("test"), new ThreadPoolExecutor.AbortPolicy());
        for (int i=0; i<10; i++) {
            executor.execute(new ChildThread(countDownLatch));
        }
        for (int i=0; i<2; i++) {
            executor.execute(() -> {
                try {
                    System.out.println("等待线程:" + Thread.currentThread().getName() + "...启动...");
                    countDownLatch.await();
                    System.out.println("等待线程:" + Thread.currentThread().getName() + "...结束...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("主线程开始等待！");
        countDownLatch.await();
        System.out.println("主线程执行完毕！");
        executor.shutdown();
    }
}
