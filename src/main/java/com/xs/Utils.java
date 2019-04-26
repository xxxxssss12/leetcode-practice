package com.xs;

import com.alibaba.fastjson.JSON;

import java.util.Random;

/**
 * Created by xs on 2019/4/12
 */
public class Utils {
    public static int[] createNums(int n, int bound) {
        Random ran = new Random();
        int[] arr = new int[n];
        for (int i=0; i<n; i++) {
            arr[i] = ran.nextInt(bound) - (bound/2);
        }
        System.out.println(JSON.toJSONString(arr));
        return arr;
    }
}
