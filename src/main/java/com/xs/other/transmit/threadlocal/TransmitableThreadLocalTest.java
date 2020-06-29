package com.xs.other.transmit.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.xs.MyThreadFactory;
import com.xs.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xs on 2019/4/29
 */
public class TransmitableThreadLocalTest {
    private static ExecutorService executor = TtlExecutors.getTtlExecutorService(Utils.generatorExecutor(10, "a"));

    public static final TransmittableThreadLocal<String> context = new TransmittableThreadLocal<String>();
    public static final TransmittableThreadLocal<String> context1 = new TransmittableThreadLocal<String>();

    public static void main(String[] args) throws InterruptedException {
        TransmitableThreadLocalTest test = new TransmitableThreadLocalTest();

        new Thread(() -> {
            int i =0;
            for (;;) {
                context.set("t1-" + (++i));
                context1.set("!!!-" + i);
                System.out.println(context.get());
                for (int j = 0; j < 10; j++) {
                    executor.execute(() -> {
                        System.out.println(Thread.currentThread().getName() + "...context=" + context.get() + ";;" + context1.get());
                    });
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.remove();
            }
        }).start();

        new Thread(() -> {
            int i =10000;
            for (;;) {
                context.set("t2-" + (++i));
                context1.set("@@@-" + i);
                System.out.println(context.get());
                for (int j = 0; j < 10; j++) {
                    executor.execute(() -> {
                        System.out.println(Thread.currentThread().getName() + "...context=" + context.get());
                    });
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.remove();
            }
        }).start();
        Thread.interrupted();
    }
}
