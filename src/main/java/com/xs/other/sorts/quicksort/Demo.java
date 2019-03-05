package com.xs.other.sorts.quicksort;

import com.alibaba.fastjson.JSON;

import java.util.Random;

/**
 * Created by xs on 2018/10/11
 */
public class Demo {

    public static void sort(int[] arr,int low,int high) {
        int l=low;
        int h=high;
        int povit=arr[low];
        while(l < h) {
            while(l < h && arr[h] >= povit)
                h--;
            if(l < h){
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                l++;
            }
            while(l < h && arr[l] <= povit)
                l++;
            if(l < h){
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                h--;
            }

        }
        System.out.println(JSON.toJSONString(arr));
        System.out.println("l=" + (l+1) + " \th=" + (h+1) + " \tpovit=" + povit);
        if(l > low)sort(arr,low,l-1);
        if(h < high)sort(arr,l+1,high);
    }

    public static void main(String[] args) {
        int n=10;
        int[] arr = randomArr(n);
        sort(arr, 0, n-1);
        System.out.println(JSON.toJSONString(arr));
    }

    private static int[] randomArr(int n) {
        Random random = new Random();
        int[] arr = new int[10];
        for (int i=0; i<n; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }
}
