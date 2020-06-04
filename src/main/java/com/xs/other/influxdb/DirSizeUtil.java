package com.xs.other.influxdb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xs.other.influxdb.bean.DirSizeBean;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xs
 * create time:2020-05-27 22:45:31
 */
public class DirSizeUtil {
    private static String basePath = "/Users/xiongshun/.influxdb/";
    private static String dataDirName = "data";
    private static String walDirName = "wal";
    private static String metaDirName = "meta";

    public static DirSizeBean calculateSize() {
        DirSizeBean bean = new DirSizeBean();
        long total = FileUtils.sizeOfDirectory(new File(basePath));
        long data = FileUtils.sizeOfDirectory(new File(basePath + dataDirName));
        long wal = FileUtils.sizeOfDirectory(new File(basePath + walDirName));
        long meta = FileUtils.sizeOfDirectory(new File(basePath + metaDirName));
        bean.setDataSizeKb(trans(data));
        bean.setMetaSizeKb(trans(meta));
        bean.setWalSizeKb(trans(wal));
        bean.setTotalSizeKb(trans(total));
        System.out.println(JSON.toJSONString(bean));
        return bean;
    }

    private static BigDecimal trans(long bytes) {
        return BigDecimal.valueOf(bytes)
                .divide(BigDecimal.valueOf(1000),3, RoundingMode.HALF_EVEN)
                .setScale(3, RoundingMode.HALF_EVEN);
    }
}
