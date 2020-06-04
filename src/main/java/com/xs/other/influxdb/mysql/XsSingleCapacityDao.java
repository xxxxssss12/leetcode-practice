package com.xs.other.influxdb.mysql;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xs.other.influxdb.bean.DirSizeBean;
import com.xs.other.influxdb.bean.StatisticBean;
import com.xs.other.influxdb.bean.XsSingleCapacityDo;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author xs
 * create time:2020-05-27 22:15:38
 */
public class XsSingleCapacityDao extends BaseDao {


    public void insert(StatisticBean bean) throws Exception {
//        Connection conn=BaseDao.getConnection();
        String sql="insert into xs_single_capacity VALUES (null, ?, ?, ?, ?, ?, ?, ?);";
//        PreparedStatement stmt= conn.prepareStatement(sql);
        int rs = insert(sql,
                bean.getLastInsert().getTableName(),
                bean.getCurrentCount(),
                bean.getLastInsert().getTimestamp(),
                bean.getDirSize().getDataSizeKb(),
                bean.getDirSize().getWalSizeKb(),
                bean.getDirSize().getMetaSizeKb(),
                bean.getDirSize().getTotalSizeKb()
                );
    }
}
