package com.xs.other.guava;

import com.google.common.util.concurrent.RateLimiter;
import com.xs.Utils;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 限流
 *
 * @author xs
 * create time:2020-07-13 20:30
 **/
public class RateLimitInterceptor {
    /**
     * 单机全局限流器(限制QPS为1)
     */
    private static final RateLimiter rateLimiter = RateLimiter.create(1);

    public static boolean filter() {
        return rateLimiter.tryAcquire();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = Utils.generatorExecutor(10, "test");
        for (int i=0; i<10; i++) {
            System.out.println(Thread.currentThread().getName() + "-限流-" + (filter() ? "进来了" : "没进来"));
            Thread.sleep(500);
        }
        executor.shutdown();
    }
}
