package com.xs.other.timerwheel;

/**
 * 任务执行器
 * @author xs
 * create time:2020-06-22 18:59:05
 */
public class TaskRunner {

    private int startTimerWheelLattice;    // 任务开始时在第几格
    private long startTimerWheelRound;      // 任务开始时时间轮在第几圈
    private TimerTask timerTask;            // 需要执行的任务
    private int endTimerWheelLattice;      // 任务结束时在第几格
    private long endTimerWheelRound;
    private TimerContext timerContext;

    public TaskRunner(TimerContext context) {
        this.timerContext = context;
    }

    public void execute() {
        // 计时
        this.startTimerWheelLattice = this.timerContext.nowLattice.intValue();
        this.startTimerWheelRound = this.timerContext.round.intValue();
        timerTask.execute();
        this.endTimerWheelLattice = this.timerContext.nowLattice.intValue();
        this.endTimerWheelRound = this.timerContext.round.intValue();
    }
    public long getStartTimerWheelLattice() {
        return startTimerWheelLattice;
    }

    public long getStartTimerWheelRound() {
        return startTimerWheelRound;
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public long getEndTimerWheelLattice() {
        return endTimerWheelLattice;
    }

    public long getEndTimerWheelRound() {
        return endTimerWheelRound;
    }
}
