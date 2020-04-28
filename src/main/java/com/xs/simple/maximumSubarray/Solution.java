package com.xs.simple.maximumSubarray;

import com.alibaba.fastjson.JSON;

import java.util.Random;

/**
 * Created by xs on 2019/4/1
 */
public class Solution {
//    private static int[] nums = null;


    private int max(int a1, int a2) {
        return Math.max(a1, a2);
    }

    public static void main(String[] args) {
        int[] nums = createNums(10);
        System.out.println(new Solution().maxSubArray(nums));
    }

    private int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int[] dp = new int[nums.length];
        for (int i=0; i<nums.length; i++) {
            if (i==0) {
                dp[i] = nums[i];
                max = dp[i];
                continue;
            }
            dp[i] = max(dp[i-1] + nums[i], nums[i]);
            if (max < dp[i]) {
                max = dp[i];
            }
        }
        return max;
    }

    private static int[] createNums(int n) {
        Random ran = new Random();
        int[] arr = new int[n];
        for (int i=0; i<n; i++) {
            arr[i] = ran.nextInt(20) - 10;
        }
        System.out.println(JSON.toJSONString(arr));
        return arr;
    }
}
