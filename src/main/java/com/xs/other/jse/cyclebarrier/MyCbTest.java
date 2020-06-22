package com.xs.other.jse.cyclebarrier;

import com.xs.Utils;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xs
 * create time:2020-06-20 17:41
 **/
public class MyCbTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = Utils.generatorExecutor(10, "cbTest");
        MyCb2 cb = new MyCb2(10, false);
        for (int i=0; i<10; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "----start");
                cb.await();
                System.out.println(Thread.currentThread().getName() + "----end");
            });
        }
        Thread.sleep(5000);
        cb.signAll();
        Thread.sleep(1000);
        executor.shutdownNow();
    }
}
