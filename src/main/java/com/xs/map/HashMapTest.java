package com.xs.map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HashMapTest implements Runnable {
    private static Map<String, String> map = new HashMap<>();
    private Integer index;
    private CyclicBarrier barrier;
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
            exec.execute(new HashMapTest(i, cyclicBarrier));
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
        map.put(index + "", Thread.currentThread().getName());
        System.out.println("put:i=" + index + ";value=" + Thread.currentThread().getName());
    }
}
