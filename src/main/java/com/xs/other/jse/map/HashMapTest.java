package com.xs.other.jse.map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xs.MyThreadFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class HashMapTest implements Runnable {
//    private static HashMap<String, String> map = new HashMap<>();
    private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    private Integer index;
    private CyclicBarrier barrier;
    private static final ExecutorService pool = new ThreadPoolExecutor(128, 128, 0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new MyThreadFactory("test"), new ThreadPoolExecutor.AbortPolicy());
    public HashMapTest(Integer index, CyclicBarrier barrier) {
        this.index = index;
        this.barrier = barrier;
    }
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(128, new Runnable() {
            @Override
            public void run() {
                System.out.println("All are ready");
            }
        });
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 128; i++) {
            pool.submit(new HashMapTest(i, cyclicBarrier));
        }
        Thread.sleep(10000);
        System.out.println("map长度为：" + map.size());
        System.out.println(JSON.toJSONString(map, SerializerFeature.PrettyFormat));
        exec.shutdown();
    }

    @Override
    public void run() {
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        long curTime = System.nanoTime();
        map.put(index + "", Thread.currentThread().getName());
        System.out.println("put:i=" + index + ";value=" + Thread.currentThread().getName() + ";time=" + (System.nanoTime() - curTime));
    }
}
