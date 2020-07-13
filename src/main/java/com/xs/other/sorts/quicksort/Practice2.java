package com.xs.other.sorts.quicksort;

import com.xs.Utils;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Arrays;

public class Practice2 {
    public static void quickSort(int[] nums, int start, int end) {
        int middleValue = nums[start];
        int middleValueIndex = start;
        int endPoint = end;
        int startPoint = start;
        int tmp;
        while(endPoint > startPoint) {
            while (nums[endPoint] >= middleValue && endPoint > startPoint) {
                endPoint --;
            }
            if (endPoint > middleValueIndex) {
                tmp = nums[middleValueIndex];
                nums[middleValueIndex] = nums[endPoint];
                nums[endPoint] = tmp;
                middleValueIndex = endPoint;
            }
            while (nums[startPoint] <= middleValue && endPoint > startPoint) {
                startPoint ++;
            }
            if (middleValueIndex > startPoint) {
                tmp = nums[middleValueIndex];
                nums[middleValueIndex] = nums[startPoint];
                nums[startPoint] = tmp;
                middleValueIndex = startPoint;
            }
        }
        if (middleValueIndex > start+1) {
            quickSort(nums, start, middleValueIndex-1);
        }
        if (middleValueIndex < end-1) {
            quickSort(nums, middleValueIndex+1, end);
        }
    }

    public static void main(String[] args) throws Exception {

        for (int i=0; i<10; i++) {
            int[] nums = Utils.createNums(30, 999, true);
            nums = new int[]{3,1,4,2,6,9,8,0};
            System.out.print("ori :");
            Utils.println(nums);
            quickSort(nums, 0, nums.length - 1);
            System.out.print("over:");
            Utils.println(nums);
        }
    }
}
