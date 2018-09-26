package com.xs.twoSum;

/**
 * Created by xs on 2018/5/8
 */
public class Solution {

    public int[] twoSum(int[] nums, int target) {
        int left;
        int[] result = new int[2];
        for (int i=0; i < nums.length-1; i++) {
            result[0] = i;
            left = target - nums[result[0]];
            for (int j=i+1; j < nums.length; j++) {
                if (nums[j] == left) {
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }
}
