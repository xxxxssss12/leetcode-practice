package com.xs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class SimpleTest {
    public static void main(String[] args) {
        try {
            // 输入
            BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
            String read = strin.readLine();
            Integer length = Integer.valueOf(read);
            PeopleCoor[] peopleCoors = new PeopleCoor[length];
            for (int i=0; i<length; i++) {
                read = strin.readLine();
                PeopleCoor coor = new PeopleCoor();
                String[] arr = read.split(" ");
                coor.x = Integer.valueOf(arr[0]);
                coor.y = Integer.valueOf(arr[1]);
                peopleCoors[i] = coor;
            }
            // 计算
            System.out.println(mianjiCal(peopleCoors));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BigDecimal mianjiCal(PeopleCoor[] people) {
        if (people == null || people.length < 2) return BigDecimal.ZERO;
        Extreme ext = getExteme(people);
        Long bianchang = Math.max(ext.maxX - ext.minX , ext.maxY - ext.minY);
        return BigDecimal.valueOf(bianchang).multiply(BigDecimal.valueOf(bianchang));
    }

    private static Extreme getExteme(PeopleCoor[] peopleList) {
        // 取坐标的最大最小值
        Extreme extreme = new Extreme();
        for (PeopleCoor peopleCoor : peopleList) {
            // min
            if (peopleCoor.x < extreme.minX) {
                extreme.minX = peopleCoor.x;
            }
            if (peopleCoor.y < extreme.minY) {
                extreme.minY = peopleCoor.y;
            }
            // max
            if (peopleCoor.x > extreme.maxX) {
                extreme.maxX = peopleCoor.x;
            }
            if (peopleCoor.y > extreme.maxY) {
                extreme.maxY = peopleCoor.y;
            }
        }
        return extreme;
    }
}

class PeopleCoor {
    public long x;
    public long y;
}

class Extreme {
    public long minX = Integer.MAX_VALUE;
    public long minY = Long.MAX_VALUE;
    public long maxX = Long.MIN_VALUE;
    public long maxY = Long.MIN_VALUE;
}