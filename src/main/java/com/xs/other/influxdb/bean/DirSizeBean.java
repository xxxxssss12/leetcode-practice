package com.xs.other.influxdb.bean;

import java.math.BigDecimal;

/**
 * @author xs
 * create time:2020-05-27 22:21:53
 */
public class DirSizeBean {
    private BigDecimal metaSizeKb;
    private BigDecimal dataSizeKb;
    private BigDecimal walSizeKb;
    private BigDecimal totalSizeKb;


    public BigDecimal getMetaSizeKb() {
        return metaSizeKb;
    }

    public void setMetaSizeKb(BigDecimal metaSizeKb) {
        this.metaSizeKb = metaSizeKb;
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

    public BigDecimal getTotalSizeKb() {
        return totalSizeKb;
    }

    public void setTotalSizeKb(BigDecimal totalSizeKb) {
        this.totalSizeKb = totalSizeKb;
    }
}
