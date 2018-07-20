package com.xs;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by xs on 2018/5/9
 */
public class FixThreadPoolTest {
    static Executor executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        for (int i=0; i<10;i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("thread=" + Thread.currentThread().getName() + ";执行开始！");
                        Thread.sleep(5000);
                        System.out.println("thread=" + Thread.currentThread().getName() + ";执行完毕！");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("i=" + i + "execute执行完毕！！");
        }
        Thread.sleep(1000000);
    }
}
