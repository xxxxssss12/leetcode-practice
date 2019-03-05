package com.xs.other.sorts.mergesort;

/**
 * Created by xs on 2019/2/28
 */
public class MergeSort {
    
    public int[] mergeSort(int[] arr, int start, int end) {
        if (arr == null || arr.length <= 0) {
            return null;
        }
        if (start < end) {
            int middle = start + (end - start) / 2;
            mergeSort(arr, start, middle);
            mergeSort(arr, middle+1, end);
            merge(arr, start, middle, end);
        }
        return arr;
    }

    private void merge(int[] arr, int start, int middle, int end) {
        int[] leftArr = new int[middle - start + 1];
        int[] rightArr = new int[end - middle];
        int left = 0;
        int right = 0;

        for (int i = start; i <= middle; i++) {
            leftArr[left] = arr[i];
            left ++;
        }
        for (int i = middle+1; i <= end; i++) {
            rightArr[right] = arr[i];
            right ++;
        }
        int i=0;
        int j=0;
        int flag = start;
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i] < rightArr[j]) {
                arr[flag] = leftArr[i];
                i ++;
            } else {
                arr[flag] = rightArr[j];
                j ++;
            }
            flag ++;
        }
        while (i < leftArr.length) {
            arr[flag] = leftArr[i];
            i ++;
            flag ++;
        }
        while (j < rightArr.length) {
            arr[flag] = rightArr[j];
            j ++;
            flag ++;
        }
    }
}
