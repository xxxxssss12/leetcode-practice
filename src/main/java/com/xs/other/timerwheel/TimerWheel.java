package com.xs.other.timerwheel;

/**
 * @author xs
 * create time:2020-06-22 18:36:15
 */
public interface TimerWheel {
    boolean add(TimerTask task);

    boolean remove(TimerTask task);
}
