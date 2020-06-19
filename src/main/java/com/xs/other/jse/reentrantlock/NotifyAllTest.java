package com.xs.other.jse.reentrantlock;

import com.xs.MyThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

public class NotifyAllTest {
    private static final ExecutorService pool = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new MyThreadFactory("test"), new ThreadPoolExecutor.AbortPolicy());
    private static class Sync extends AbstractQueuedSynchronizer {
        Condition condition = new ConditionObject();
        public void await() {
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void notify_all() {
            condition.signalAll();
            System.out.println("signalOver");
        }
        protected boolean isHeldExclusively() {
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Sync sync = new Sync();

        for (int i=0; i<10; i++) {
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "...start");
                sync.await();
                System.out.println(Thread.currentThread().getName() + "...over");
            });
        }
        Thread.sleep(10000);
        sync.notify_all();
    }
}
