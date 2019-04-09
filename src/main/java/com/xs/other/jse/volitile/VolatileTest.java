package com.xs.other.jse.volitile;

/**
 * Created by xs on 2019/3/8
 */
public class VolatileTest {
    private boolean flag = true;
    public Thread test() {
        Thread t1 = new Thread(() -> {
            int counter = 0;
            while (flag) {
                counter++;
                try {
                    Thread.sleep(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread 1 finished. Counted up to " + counter);
        });
        t1.start();
        new Thread(() -> {
            // Sleep for a bit so that thread 1 has a chance to start
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
                // catch block
            }
            flag = false;
            System.out.println("Thread 2 finishing:flag=" + flag);
        }).start();
        return t1;
    }

    public static void main(String[] args) throws InterruptedException {
        new VolatileTest().test().join();
        System.out.println("主线程结束");
    }
}