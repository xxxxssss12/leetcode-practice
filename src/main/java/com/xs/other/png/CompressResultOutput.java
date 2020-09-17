package com.xs.other.png;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiongshun
 * create-time: 2020-09-08 15:16
 */
public class CompressResultOutput {
    Map<String, String> map = new HashMap<>();
    {
        map.put("500k.png", "511");
        map.put("20m.png", "22727");
        map.put("6m.png", "6345");
        map.put("5m.png", "5345");
        map.put("4m.png", "4212");
        map.put("3m.png", "2962");
        map.put("2m.png", "2207");
        map.put("1m.png", "1095");
    }
    public static void main(String[] args) throws Exception {
        String jsonFile = "D:\\Workspaces\\IdeaProject\\jni-test\\src\\main\\resources\\result.json";
        String outputFileName = "D:\\Pictures\\result.xlsx";
        String json = FileUtils.readFileToString(new File(jsonFile), Charset.defaultCharset());
        JSONArray originObj = JSON.parseArray(json);
        Workbook workbook = new SXSSFWorkbook();

        for (int i=0; i<originObj.size(); i++) {

        }
    }
}
