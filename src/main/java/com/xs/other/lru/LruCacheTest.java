package com.xs.other.lru;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author xs
 * create time:2020-04-19 17:06
 **/
public class LruCacheTest {
    public static void main(String[] args) {
        LruCache<String, Object> cache = new LruCache<>(10);
        for (int i=0; i<20; i++) {
            cache.put(i + "", i);
            if (cache.size() >= cache.getCapacity()) {
                System.out.println(JSON.toJSONString(cache.values()));
            }
        }
        cache.put("15", 15);
        cache.put("17", 17);
        cache.get("11");
        System.out.println(JSON.toJSONString(cache.values()));
    }
}
