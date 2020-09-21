package com.xs.other.png;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import net.coobird.thumbnailator.Thumbnails;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xs
 * create time:2020-09-21 22:46
 **/
public class JpgTest {

    private static String originfile = "D:\\Pictures\\test\\10074x3658-99.jpg";
    private static String targetPreffix = "D:\\Pictures\\test\\10074x3658-99";
    public static void main(String[] args) throws Exception {
        int originSize = (int) new File(originfile).length();
        for (int i=99; i>0; i--) {
            try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
                copressThum(originfile, b, i);
                System.out.println(i + ";" + originSize + "," + b.toByteArray().length);
            }
        }
    }
    public static void copressThum(String originFile, OutputStream out, int quanlity) throws Exception {
        double q = BigDecimal.valueOf(quanlity).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).doubleValue();
        Thumbnails.of(new File(originFile)).scale(1).outputFormat("jpg").outputQuality(q).toOutputStream(out);
    }
}
