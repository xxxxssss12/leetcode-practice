package com.xs.hard.mofTwoSorted;

/**
 * Created by xs on 2018/7/20
 */
public class Solution {

    /**
     * 为了解决这个问题，我们需要理解“中位数的作用是什么”。在统计中，中位数被用来：
     * 将一个集合划分为两个长度相等的子集，其中一个子集中的元素总是大于另一个子集中的元素。
     *
     * @param nums1
     * @param nums2
     * @return
     */

    int index1 = -1;
    int index2 = -1;

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // TODO
        int[] allArr = new int[(nums1.length + nums2.length) / 2 + 1];
        for (int i=0; i<allArr.length; i++) {
            addYoubiao(nums1, nums2, allArr, i);
        }
        int max = allArr.length - 1;
        if ((nums1.length + nums2.length) % 2 == 0) {
            return (Double.valueOf(allArr[max]) + Double.valueOf(allArr[max - 1])) / 2;
        } else {
            return Double.valueOf(allArr[max]);
        }
    }

    private void addYoubiao(int[] nums1, int[] nums2, int[] allArr, int allIndex) {
        if (nums1.length <= 0) {
            index2++;
            allArr[allIndex] = nums2[index2];
            return;
        }
        if (nums2.length <= 0) {
            index1++;
            allArr[allIndex] = nums1[index1];
            return;
        }
        if (nums1.length - 1 <= index1) {
            index2++;
            allArr[allIndex] = nums2[index2];
            return;
        }
        if (nums2.length - 1 <= index2) {
            index1++;
            allArr[allIndex] = nums1[index1];
            return;
        }
        if (nums1[index1 + 1] < nums2[index2 + 1]) {
            index1++;
            allArr[allIndex] = nums1[index1];
            return;
        } else {
            index2++;
            allArr[allIndex] = nums2[index2];
            return;
        }
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{1,3};
        int[] nums2 = new int[]{2};
        System.out.println( new Solution().findMedianSortedArrays(nums1, nums2));
    }
}