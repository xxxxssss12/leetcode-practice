package com.xs.other.lru;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @author xs
 * create time:2020-04-19 16:42
 **/
public class LruCache<KEY, VALUE> {

    private LinkedHashMap<KEY, VALUE> cacheData = new LinkedHashMap<>();
    private final int capacity;

    public int getCapacity() {
        return capacity;
    }

    public Collection<VALUE> values() {
        return cacheData.values();
    }
    public LruCache(int capacity) {
        if (capacity < 1) {
            throw new RuntimeException("LruCache capacity cannot less than 1");
        }
        this.capacity = capacity;
    }
    public VALUE get(KEY key) {
        VALUE value = cacheData.get(key);
        if (value == null) {
            return null;
        }
        cacheData.remove(key);
        cacheData.put(key, value);
        return value;
    }

    public void put(KEY key, VALUE value) {
        if (value == null) {
            return;
        }
        if (this.get(key) != null) {
            cacheData.remove(key);
            cacheData.put(key, value);
            return;
        }
        if (cacheData.size() >= capacity) {
            this.removeEarliestKey();
        }
        cacheData.put(key, value);
    }

    private void removeEarliestKey() {
        while (cacheData.size() >= capacity) {
            cacheData.remove(cacheData.keySet().iterator().next());
        }
    }

    public void remove(KEY key) {
        VALUE value = cacheData.remove(key);
    }

    public int size() {
        return cacheData.size();
    }
}
