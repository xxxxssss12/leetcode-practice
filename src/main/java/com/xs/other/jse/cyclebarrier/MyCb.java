package com.xs.other.jse.cyclebarrier;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xs
 * create time:2020-06-20 17:38
 **/
public class MyCb {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private AtomicInteger a = null;
    private boolean needManualWakeup;

    public MyCb(int num, boolean needManualWakeup) {
        a = new AtomicInteger(num);
        this.needManualWakeup = needManualWakeup;
    }
    public void await() {
        try {
            lock.lock();
            int i = a.decrementAndGet();
            if (i <= 0 && !needManualWakeup) {
                System.out.println(condition.getClass().getName());
                condition.signalAll();
            } else {
                condition.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signAll() {
        lock.lock();
        condition.signalAll();
        lock.unlock();
    }
}
