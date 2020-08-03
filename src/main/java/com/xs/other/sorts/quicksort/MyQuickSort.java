package com.xs.other.sorts.quicksort;

import com.alibaba.fastjson.JSON;
import com.xs.Utils;

/**
 * @author xs
 * create time:2020-08-01 11:12
 **/
public class MyQuickSort {

    public static void doSort(int[] nums, int start, int end) {
        if (start >= nums.length || end < 0 || start >= end) {
            return;
        }
        int index = chooseIndex(nums, start, end);
        doSort(nums, start, index-1);
        doSort(nums, index+1, end);
    }


    public static void main(String[] args) {
        int[] nums = Utils.createNums(10, 1000, true);
        System.out.println(JSON.toJSONString(nums));
        doSort(nums, 0, nums.length - 1);
        System.out.println(JSON.toJSONString(nums));
    }
    public static int chooseIndex(int[] nums, int start, int end) {
        if (start >= nums.length || end < 0 || start >= end) {
            return start;
        }
        int result = start;
        int baseData = nums[start];
        int low = start;
        int high = end;
        while (low < high) {
            while (nums[high] >= baseData && low < high) {
                high--;
            }
            if (low < high) {
                nums[low] = nums[high];
                nums[high] = baseData;
                result = high;
            }
            while (nums[low] <= baseData && low < high) {
                low ++;
            }
            if (low < high) {
                nums[high] = nums[low];
                nums[low] = baseData;
                result = low;
            }
        }
        return result;
    }
}