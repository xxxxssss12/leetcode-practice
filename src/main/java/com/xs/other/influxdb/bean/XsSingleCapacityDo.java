package com.xs.other.influxdb.bean;

import java.math.BigDecimal;

/**
 * @author xs
 * create time:2020-05-27 22:13:45
 */
public class XsSingleCapacityDo {
    private Long id;
    private String tableName;
    private Long currentCount;
    private Long currentTimeMicro;
    private BigDecimal dataSizeKb;
    private BigDecimal walSizeKb;
    private BigDecimal metaSizeKb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Long currentCount) {
        this.currentCount = currentCount;
    }

    public Long getCurrentTimeMicro() {
        return currentTimeMicro;
    }

    public void setCurrentTimeMicro(Long currentTimeMicro) {
        this.currentTimeMicro = currentTimeMicro;
    }

    public BigDecimal getDataSizeKb() {
        return dataSizeKb;
    }

    public void setDataSizeKb(BigDecimal dataSizeKb) {
        this.dataSizeKb = dataSizeKb;
    }

    public BigDecimal getWalSizeKb() {
        return walSizeKb;
    }

    public void setWalSizeKb(BigDecimal walSizeKb) {
        this.walSizeKb = walSizeKb;
    }

    public BigDecimal getMetaSizeKb() {
        return metaSizeKb;
    }

    public void setMetaSizeKb(BigDecimal metaSizeKb) {
        this.metaSizeKb = metaSizeKb;
    }
}
