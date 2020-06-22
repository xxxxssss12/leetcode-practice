package com.xs.other.timerwheel;

/**
 * @author xs
 * create time:2020-06-22 18:37:04
 */
public interface TimerTask {
    void execute();

    String getTaskId();

    String getCron();

    TimerTaskStatusEnum currentStatus();
}
