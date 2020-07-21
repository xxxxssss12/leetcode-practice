package com.xs.other;

import com.xs.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    private static ExecutorService executor = Utils.generatorExecutor(2, "test");

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<4;i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName() + " sleep over ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
//            Thread.sleep(4000 + Utils.random(2000, false));
        }
        executor.shutdown();
    }
}
