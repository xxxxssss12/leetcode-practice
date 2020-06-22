package com.xs.other.jse.cyclebarrier;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xs
 * create time:2020-06-22 22:09:59
 */
public class MyCb2 {
    private AtomicInteger a = null;
    private boolean needManualWakeup;

    public MyCb2(int num, boolean needManualWakeup) {
        a = new AtomicInteger(num);
        this.needManualWakeup = needManualWakeup;
    }

    public synchronized void await() {
        try {
            int i = a.decrementAndGet();
            if (i <= 0 && needManualWakeup) {
                this.notifyAll();
            } else {
                this.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void signAll() {
        this.notifyAll();
    }
}
