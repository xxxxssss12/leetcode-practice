package com.xs.other.influxdb;

import com.xs.other.influxdb.bean.DirSizeBean;
import com.xs.other.influxdb.bean.SingleTestBean;
import com.xs.other.influxdb.bean.StatisticBean;
import com.xs.other.influxdb.mysql.XsSingleCapacityDao;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xs
 * create time:2020-05-26 22:38:44
 */
public class InfluxDBTest {
    private static InfluxDBConnection influxDBConnection = new InfluxDBConnection("admin", "admin", "http://localhost:8086", "capacity_single_test", "def_new");
    private static long firstTimestampMicro = 1589904000000000L;
    private static Random random = new Random();

    public static void main(String[] args) throws Exception {
        singleInsert();
        QueryResult results = influxDBConnection
                .query("SELECT * FROM test_table where host = 'localhost'  order by time desc limit 1");
        //results.getResults()是同时查询多条SQL语句的返回值，此处我们只有一条SQL，所以只取第一个结果集即可。
        QueryResult.Result oneResult = results.getResults().get(0);
        if (oneResult.getSeries() != null) {
            for (QueryResult.Series series : oneResult.getSeries()) {
                List<String> columns = series.getColumns();
                List<List<Object>> values = series.getValues();
                for (List<Object> row : values) {
                    System.out.print("{");
                    for (int i = 0; i < columns.size(); i++) {
                        System.out.print("\"" + columns.get(i) + "\":\"" + values.get(0).get(i) + "\"");
                        if (i < columns.size() - 1) {
                            System.out.print(",");
                        }
                    }
                    System.out.println("}");
                }
            }
        }
        startInsert(firstTimestampMicro);
    }

    private static void startInsert(long firstTimestampMicro) throws Exception {
        XsSingleCapacityDao xsSingleCapacityDao = new XsSingleCapacityDao();

        SingleTestBean testBean = new SingleTestBean();
        List<SingleTestBean> list = new ArrayList<>();
        for (int j=0; j<86400; j++) {
            for (int i = 0; i < 10000; i++) {
                testBean = new SingleTestBean();
                testBean.setTimestamp(firstTimestampMicro + (i+j*10000) * 100);
                testBean.setElasped(BigDecimal.valueOf(random.nextInt(1000)).floatValue());
                addToList(list, testBean);
            }
            DirSizeBean dir = DirSizeUtil.calculateSize();
            StatisticBean bean = new StatisticBean(testBean, dir, j*10000 + 10000);
            xsSingleCapacityDao.insert(bean);
        }
    }

    private static void addToList(List<SingleTestBean> list, SingleTestBean testBean) {
        list.add(testBean);
        if (list.size() >= 1000) {
            List<Point> points = new ArrayList<>();
            for (SingleTestBean testBean1 : list) {
                Point.Builder builder = Point.measurement(testBean1.getTableName());
                Map<String, String> tags = new HashMap<>();
                tags.put("host", testBean1.getHost());
                Map<String, Object> fields = new HashMap<>();
                fields.put("elasped", testBean1.getElasped());
                builder.tag(tags);
                builder.fields(fields);
                builder.time(testBean1.getTimestamp(), testBean1.getTimeUnit());
                points.add(builder.build());
            }
            BatchPoints batchPoints = BatchPoints
                    .database("capacity_single_test")
                    .retentionPolicy("def_new")
                    .tag("host", list.get(0).getHost())
                    .precision(list.get(0).getTimeUnit())
                    .consistency(InfluxDB.ConsistencyLevel.ALL)
                    .points(points)
                    .build();

            influxDBConnection.batchInsert(batchPoints);
            list.clear();
        }
    }

    private static void singleInsert() {
        Map<String, String> tags = new HashMap<>();
        tags.put("host", "localhost");
        Map<String, Object> val = new HashMap<>();
        val.put("elasped", 57f);
        influxDBConnection.insert("test_table", tags, val, 1274885168, TimeUnit.SECONDS);
    }
}
