package com.xs;

import java.util.HashMap;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleTest {
    public static void main(String[] args) throws ClassNotFoundException {
        String a = "a";
        HashMap<String, Object> map = new HashMap<>();
//        map.put(a, "hehe");
        System.out.println(a.hashCode());
        a = "Aa";
        String b = "BB";
        System.out.println(a.hashCode() + ";" + b.hashCode());
        System.out.println(a.hashCode() == b.hashCode());
        System.out.println(a == b);
        Class.forName("java.lang.Integer");
    }
}
