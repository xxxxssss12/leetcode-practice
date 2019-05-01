package com.xs.other.transmit.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.xs.MyThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xs on 2019/4/29
 */
public class TransmitableThreadLocalTest {
    private static ExecutorService executor = Executors.newFixedThreadPool(10, new MyThreadFactory());

//    public static final TransmittableThreadLocal<String> context = new TransmittableThreadLocal<String>();
//    public static final ThreadLocal<String> context = new ThreadLocal<String>();
    public static final InheritableThreadLocal<String> context = new InheritableThreadLocal<String>();
    public static void main(String[] args) throws InterruptedException {
        TransmitableThreadLocalTest test = new TransmitableThreadLocalTest();
        int i =0;
        while (true) {
            context.set("test-" + (++i));
            System.out.println(context.get());
            for (int j = 0; j< 20; j++) {
                executor.execute(() -> System.out.println(Thread.currentThread().getName() + "...context=" + context.get()));
            }
            Thread.sleep(10000);
            context.remove();
        }
    }
}
