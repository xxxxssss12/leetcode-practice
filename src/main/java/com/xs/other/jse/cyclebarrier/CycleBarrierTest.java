package com.xs.other.jse.cyclebarrier;

import com.xs.Utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xs
 * create time:2020-06-19 21:57
 **/
public class CycleBarrierTest {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = Utils.generatorExecutor(10, "cyclicBarrie");
        CyclicBarrier c = new CyclicBarrier(10);
        for (int i=0; i<10; i++) {
            executor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread() + ",,,sleep");
                    Thread.sleep(1000);
                    c.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("gogogo-" + Thread.currentThread().getName());
            });
        }
        Thread.sleep(10000);
        executor.shutdown();
    }
}
