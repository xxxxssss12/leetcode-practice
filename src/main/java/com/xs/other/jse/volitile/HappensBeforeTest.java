package com.xs.other.jse.volitile;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class HappensBeforeTest {
     public static AtomicInteger beforeCount = new AtomicInteger(0);
     int a = 0;
     volatile boolean flag = false;
     public void writer() {
         a=1;
         flag = true;
     }

     public int reader() {
         int i = -1;
         if (flag) {
             i=a;
         }
         return i;
     }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0; i< 10000; i++) {
            CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
            HappensBeforeTest happensBeforeTest = new HappensBeforeTest();
            Thread t1 = new Thread(new TestWriteThread(cyclicBarrier, happensBeforeTest));
            Thread t2 = new Thread(new TestReaderThread(cyclicBarrier, happensBeforeTest));
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        }
        System.out.println(beforeCount.get());
    }
}

class TestWriteThread implements Runnable {
    private CyclicBarrier c;
    private HappensBeforeTest hp;
    public TestWriteThread(CyclicBarrier c, HappensBeforeTest hp) {
        this.c = c;
        this.hp = hp;
    }


    @Override
    public void run() {
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        hp.writer();
    }
}

class TestReaderThread implements Runnable {
    private CyclicBarrier c;
    private HappensBeforeTest hp;
    public TestReaderThread(CyclicBarrier c, HappensBeforeTest hp) {
        this.c = c;
        this.hp = hp;
    }


    @Override
    public void run() {
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        int i = hp.reader();
        if (i == 1) {
            HappensBeforeTest.beforeCount.incrementAndGet();
        }
    }
}