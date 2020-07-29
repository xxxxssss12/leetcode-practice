package com.xs;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xs on 2019/4/29
 */
public class MyThreadFactory implements ThreadFactory {
    private String preffix = "default-";
    private static AtomicInteger threadCount = new AtomicInteger(0);

    public MyThreadFactory() {}

    public MyThreadFactory(String name) {
        this.preffix = name + "-";
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(r, preffix + threadCount.incrementAndGet());
    }
}
