package com.xs.other.jse.reentrantlock;

import com.xs.MyThreadFactory;
import io.netty.channel.rxtx.RxtxChannel;

import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

public class NotifyAllTest {
    private static final ExecutorService pool = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new MyThreadFactory("test"), new ThreadPoolExecutor.AbortPolicy());
    private static class Sync extends AbstractQueuedSynchronizer {
        Condition condition = new ConditionObject();

        @Override
        protected boolean tryAcquire(int arg) {
            if (arg == 1) {
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (arg == 1) {
                return true;
            }
            return false;
        }
        public void await() {
            try {
                condition.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void notify_all() {
            condition.signalAll();
            System.   out.println("signalOver");
        }
        protected boolean isHeldExclusively() {
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Sync sync = new Sync();

        for (int i=0; i<10; i++) {
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "...start");
                sync.await();
                System.out.println(Thread.currentThread().getName() + "...over");
            });
        }
        Thread.sleep(5000);
        sync.notify_all();
        System.out.println("ok");
    }
}
