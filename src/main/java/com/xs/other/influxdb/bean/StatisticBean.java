package com.xs.other.influxdb.bean;

import java.math.BigDecimal;

/**
 * @author xs
 * create time:2020-05-27 22:11:56
 */
public class StatisticBean {

    private SingleTestBean lastInsert;
    private DirSizeBean dirSize;
    private int currentCount = 0;

    public StatisticBean(SingleTestBean lastInsert, DirSizeBean dirSize, int currentCount) {
        this.lastInsert = lastInsert;
        this.dirSize = dirSize;
        this.currentCount = currentCount;
    }

    public SingleTestBean getLastInsert() {
        return lastInsert;
    }

    public void setLastInsert(SingleTestBean lastInsert) {
        this.lastInsert = lastInsert;
    }

    public DirSizeBean getDirSize() {
        return dirSize;
    }

    public void setDirSize(DirSizeBean dirSize) {
        this.dirSize = dirSize;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }
}
