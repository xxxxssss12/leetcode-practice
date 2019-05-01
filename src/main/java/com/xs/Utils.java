package com.xs;

import com.alibaba.fastjson.JSON;

import java.util.Random;

/**
 * Created by xs on 2019/4/12
 */
public class Utils {
    public static int[] createNums(int n, int bound) {
        return createNums(n, bound, false);
    }

    public static int[] createNums(int n, int bound, boolean isAbs) {
        Random ran = new Random();
        int[] arr = new int[n];
        for (int i=0; i<n; i++) {
            if (!isAbs) {
                arr[i] = ran.nextInt(bound) - (bound / 2);
            } else {
                arr[i] = ran.nextInt(bound);
            }
        }
        System.out.println(JSON.toJSONString(arr));
        return arr;
    }


    public static void println(int[] nums) {
        if (nums != null) {
            for (int i=0; i<nums.length; i++) {
                System.out.print(nums[i] + "\t\t");
            }
            System.out.println();
        }
    }
}
