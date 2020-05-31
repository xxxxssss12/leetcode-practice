package com.xs.other.influxdb.bean;

import java.util.concurrent.TimeUnit;

/**
 * @author xs
 * create time:2020-05-27 21:50:05
 */
public class SingleTestBean {
    private String host = "localhost";
    private Float elasped;
    private String tableName = "test_table";
    private Long timestamp;
    private TimeUnit timeUnit = TimeUnit.MICROSECONDS;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Float getElasped() {
        return elasped;
    }

    public void setElasped(Float elasped) {
        this.elasped = elasped;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
