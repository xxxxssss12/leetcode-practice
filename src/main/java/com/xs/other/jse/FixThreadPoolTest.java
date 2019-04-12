package com.xs.other.jse;


import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Created by xs on 2018/5/9
 */
public class FixThreadPoolTest {
    static Executor executor;

    public static void main(String[] args) throws InterruptedException {
//        HashMap map;
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start("test1");
//        Thread.sleep(5000);
//        stopWatch.stop();
//
//        stopWatch.start("test2");
//        Thread.sleep(3000);
//        stopWatch.stop();
//        System.out.println(stopWatch.prettyPrint());

//        ConcurrentHashMap map;
        int arg1=2;//核心线程
        int arg2=40;//最大线程数量
        int arg3=100;//空余保留时间
        executor = new ThreadPoolExecutor(arg1, arg2, arg3,TimeUnit.MILLISECONDS, // 时间单位
                new LinkedBlockingQueue<Runnable>());//默认构造的队列大小为Integer.Max, 可指定大小new LinkedBlockingQueue<Runnable>(3)，队列容量为3
        for (int i=0; i<10;i++) {
            executor.execute(() -> {
                try {
                    System.out.println("thread=" + Thread.currentThread().getName() + ";执行开始！");
                    Thread.sleep(5000);
                    System.out.println("thread=" + Thread.currentThread().getName() + ";执行完毕！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("i=" + i + "execute执行完毕！！");
        }
        Thread.sleep(1000000);
    }
}
