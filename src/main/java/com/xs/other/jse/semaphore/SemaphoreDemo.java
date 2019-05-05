package com.xs.other.jse.semaphore;

import com.xs.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    private static final int Thread_COUNT = 30;
    private static ExecutorService pool = Utils.generatorExecutor(Thread_COUNT, "test");

    private static Semaphore s = new Semaphore(10);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i<Thread_COUNT; i++) {
            pool.execute(() -> {
                try {
                    s.acquire();
                    System.out.println(Thread.currentThread().getName() + " -> save data");
                    Thread.sleep(5000);
                    s.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(30000);
        pool.shutdown();
    }
}
