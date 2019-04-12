package com.xs.simple.maximumSubarray;

import com.alibaba.fastjson.JSON;

import java.util.Random;

/**
 * Created by xs on 2019/4/1
 */
public class Solution {
//    private static int[] nums = null;
    public int maxSubArray(int[] nums) {
//        if (Solution.nums == null) {
//            Solution.nums = nums;
//        }
        int max = getMax(nums, nums.length);
        return max;
    }

    private int getMax(int[] nums, int n) {
        if (n == 1) {
            return nums[0];
        }
        int max = Integer.MIN_VALUE;
        int[] solution = new int[n];
        for (int i=0; i<nums.length; i++) {
            if (i == 0) {
                solution[i] = nums[i];
            } else {
                solution[i] = Math.max(solution[i-1] + nums[i], nums[i]);
            }
            max = Math.max(max, solution[i]);
        }
        return solution[n - 1];
//        int a1 = getMax(childLength-1, index);
//        int a2 = getMax(childLength-1, index+1);
//        int a3 = a1 + nums[childLength-1];
//        int a4 = a2 + nums[index];
//        System.out.println("now=(" + childLength + "," + index + ");" + "\t" +
//                "("+(childLength-1)+","+index+")=" + a1 + ";" + "\t" +
//                "("+(childLength-1)+","+(index+1)+")=" + a2 + ";" + "\t" +
//                "("+(childLength-1)+","+index+")+a("+(childLength-1)+")=" + a3 + ";" + "\t" +
//                "("+(childLength-1)+","+(index+1)+")+a("+(index)+")=" + a4);
//        return max(a1, a2, a3, a4);
    }

    private int max(int a1, int a2, int a3, int a4) {
        int max = a1;
        max = Math.max(max, a2);
        max = Math.max(max, a3);
        max = Math.max(max, a4);
        return max;
    }

    public static void main(String[] args) {
        int[] nums = createNums();
        System.out.println(new Solution().maxSubArray(nums));
    }

    private static int[] createNums() {
        Random ran = new Random();
        int n = 6;
        int[] arr = new int[n];
        for (int i=0; i<n; i++) {
            arr[i] = ran.nextInt(20) - 10;
        }
        System.out.println(JSON.toJSONString(arr));
        return arr;
    }
}
