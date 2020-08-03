package com.xs.other.sorts.quicksort;

import com.alibaba.fastjson.JSON;
import com.xs.Utils;

public class Practice {
    public static void main(String[] args) {
        int[] nums = Utils.createNums(10, 100, true);
        System.out.println(JSON.toJSONString(nums));
        quickSort(nums, 0, nums.length - 1);
        System.out.println(JSON.toJSONString(nums));
    }

    private static void quickSort(int[] nums, int start, int end) {
        int low = start;
        int high = end;
        int base = nums[low];
        boolean exchange = false;
        while (high > low) {
            exchange = false;
            while (high > low) {
                if (nums[high] < base) {
                    exchange = true;
                    break;
                }
                high--;
            }
            if (exchange) {
                int tmp = nums[high];
                nums[high] = nums[low];
                nums[low] = tmp;
            }
            exchange = false;
            while (low < high) {
                if (nums[low] > base) {
                    exchange = true;
                    break;
                }
                low++;
            }
            if (exchange) {
                int tmp = nums[low];
                nums[low] = nums[high];
                nums[high] = tmp;
            }
            System.out.println("base=" + base + ";" + JSON.toJSONString(nums));
        }
        if (start < low - 1) quickSort(nums, start, low - 1);
        if (end > low + 1) quickSort(nums, low + 1, end);
    }
}
