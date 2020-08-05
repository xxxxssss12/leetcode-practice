package com.xs.other.lru;

import java.util.*;
/**
 手写lru
 */
class LruCache1<K,V> {
    private final int capacity;
    private final KeyHolder<K> keyHolder; // keyHolder可以直接使用linkedHashMap。
    private final HashMap<K, V> data;

    public LruCache1(int capacity) {
        if (capacity < 1) {
            throw new RuntimeException("capacity can not less than 1");
        }
        this.capacity = capacity;
        this.keyHolder = new KeyHolder<>();
        this.data = new HashMap<>(capacity);
    }

    public boolean add(K key, V value) {
        if (data.size() < capacity || data.containsKey(key)) {
            this.keyHolder.remove(key);
            this.keyHolder.add(key);
            this.data.put(key, value);
            return true;
        } else {
            K key1 = this.keyHolder.poll();
            if (key1 != null) {
                System.out.println("缓存满，删除：" + key1);
                this.data.remove(key1);
            }
            this.keyHolder.add(key);
            this.data.put(key, value);
            return true;
        }
    }

    public V get(K key) {
        V value = this.data.get(key);
        if (value != null) {
            this.keyHolder.remove(key);
            this.keyHolder.add(key);
        }
        return value;
    }

    public Collection<V> getAll() {
        return this.data.values();
    }
    public void remove(K key) {
        this.data.remove(key);
        this.keyHolder.remove(key);
    }

    public static void main(String[] args) {
        LruCache1<String, Integer> cache = new LruCache1<>(5);
        cache.add("1", 1);
        cache.add("2", 2);
        cache.add("3", 3);
        cache.add("4", 4);
        cache.add("5", 5);
        cache.add("2", 2);
        cache.add("3", 3);
        cache.add("1", 1);
        cache.add("6", 6);
        cache.add("7", 7);
        cache.add("8", 8);
        cache.add("9", 9);
        cache.add("6", 6);
        cache.add("7", 7);
        cache.add("10", 10);
        cache.add("11", 11);
        cache.add("12", 12);
        cache.add("13", 13);
        cache.add("14", 14);
        cache.add("1", 1);
        System.out.println(cache.get("4"));
        Collection<Integer> values = cache.getAll();
        for (Integer value : values) {
            System.out.println(value);
        }
    }
    static class Point<K> {
        Point<K> prev;
        Point<K> next;
        K data;
    }

    static class KeyHolder<K> {
        private Point<K> head;
        private Point<K> tail;
        private final HashMap<K, Point<K>> keyHash = new HashMap<>();

        /**
         * 添加一个key
         * @param key
         */
        void add(K key) {
            if (keyHash.containsKey(key)) {
                // key重复，删除后添加
                this.remove(key);
                this.add(key);
                return;
            }
            Point<K> point = new Point<K>();
            point.data = key;
            if (this.tail == null) {
                // 队列中无数据，初始化
                this.head = point;
                this.tail = point;
            } else {
                // 将key添加到尾部
                this.tail.next = point;
                point.prev = this.tail;
                this.tail = point;
            }
            keyHash.put(key, point);
        }

        /**
         * 删除一个key
         * @param key
         */
        void remove(K key) {
            Point<K> keyPoint = this.keyHash.get(key);
            if (keyPoint == null) {
                // 无key，不删
                return;
            }
            this.keyHash.remove(key);
            if (keyPoint.prev == null && keyPoint.next == null) {
                // 只有这一个节点，将队列设置为空
                this.head = null;
                this.tail = null;
            } else if (keyPoint.prev == null) {
                // 这个key是头结点
                this.head = keyPoint.next;
                this.head.prev = null;
            } else if (keyPoint.next == null) {
                // 这个key是尾结点
                this.tail = keyPoint.prev;
                this.tail.next = null;
            } else {
                keyPoint.prev.next = keyPoint.next;
                keyPoint.next.prev = keyPoint.prev;
            }
        }

        /**
         * 从头出一个节点
         * @return head
         */
        K poll() {
            Point<K> keyPoint = this.head;
            this.keyHash.remove(keyPoint.data);
            this.head = keyPoint.next;
            this.head.prev = null;
            return keyPoint.data;
        }
    }
}