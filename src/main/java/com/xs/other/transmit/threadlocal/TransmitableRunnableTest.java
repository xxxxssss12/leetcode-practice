package com.xs.other.transmit.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

/**
 * @author xs
 * create time:2020-06-23 17:36
 **/
public class TransmitableRunnableTest {
    public static final TransmittableThreadLocal<String> tl = new TransmittableThreadLocal<String>();

    public static void main(String[] args) throws InterruptedException {
        tl.set("fuck");
        Runnable runnable = TtlRunnable.get(() -> System.out.println(tl.get()));
        Thread thread = new Thread(runnable);
        thread.start();
//        thread.run();
        tl.set("fff");
        Thread.sleep(1000);
//        thread.
//        thread.start();
        Thread.sleep(1000);
    }
}
