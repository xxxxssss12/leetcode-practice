package com.xs.other.transmit.threadlocal;

/**
 * @author xs
 * create time:2020-06-23 17:09
 **/
public class InheritThreadLocalTest {
    public static InheritableThreadLocal<String> tl = new InheritableThreadLocal<>();
    public static void main(String[] args) throws InterruptedException {
        tl.set("fuck");
        new Thread(() -> {
            System.out.println(tl.get());
        }).start();
        Thread.sleep(1000);
    }
}
