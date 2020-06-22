package com.xs.other.timerwheel;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author xs
 * create time:2020-06-22 18:49:15
 */
public class TimerContext {

    public LongAdder round = new LongAdder(); // 全局-跑了几圈

    public AtomicInteger nowLattice = new AtomicInteger(0); // 全局-现在走到第几格了

}
