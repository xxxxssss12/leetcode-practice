package com.xs;


import org.springframework.util.StopWatch;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xs on 2018/5/9
 */
public class FixThreadPoolTest {
    static Executor executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("test1");
        Thread.sleep(5000);
        stopWatch.stop();

        stopWatch.start("test2");
        Thread.sleep(3000);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

//        ConcurrentHashMap map;
//        for (int i=0; i<10;i++) {
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        System.out.println("thread=" + Thread.currentThread().getName() + ";执行开始！");
//                        Thread.sleep(5000);
//                        System.out.println("thread=" + Thread.currentThread().getName() + ";执行完毕！");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            System.out.println("i=" + i + "execute执行完毕！！");
//        }
//        Thread.sleep(1000000);
    }
}
