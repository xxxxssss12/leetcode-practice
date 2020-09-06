package com.xs.medium.searchMatrix;

import com.alibaba.fastjson.JSON;
import com.xs.Utils;

import java.util.Arrays;
import java.util.Random;

/**
 * 240. 搜索二维矩阵 II
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
 *
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-a-2d-matrix-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author xs
 * create time:2020-08-09 12:44
 **/
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length <= 0 || matrix[0].length <= 0) {
            return false;
        }
        if (matrix[0][0] > target) {
            return false;
        }
        int xMax = matrix.length - 1;
        int yMax =  matrix[0].length - 1;
        int xMinIndex = 0;
        int xMaxIndex = matrix.length - 1;
        int yMinIndex = 0;
        int yMaxIndex = matrix[0].length - 1;
        while (matrix[xMinIndex][yMax] < target && xMinIndex < xMax) {
            xMinIndex++;
        }
        while (matrix[xMaxIndex][0] > target && xMaxIndex > xMinIndex) {
            xMaxIndex --;
        }
        while (matrix[xMaxIndex][yMinIndex] < target && yMinIndex < yMax) {
            yMinIndex ++;
        }
        while (matrix[xMinIndex][yMaxIndex] > target && yMaxIndex > yMinIndex) {
            yMaxIndex --;
        }
        for (int i=xMinIndex; i<=xMaxIndex; i++) {
            for (int j=yMinIndex; j<= yMaxIndex; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[][] generateArr(int n, int m) {
        Random r = new Random();
        int[][] arr = new int[n][m];
        int index = 0;
        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr[0].length; j++) {
                arr[i][j] = index;
                index+= r.nextInt(10);
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] arr = s.generateArr(4,5);
        arr = new int[][] {
                new int[]{3,3,8,13,13,18},
                new int[]{4,5,11,13,18,20},
                new int[]{9,9,14,15,23,23},
                new int[]{13,18,22,22,25,27},
                new int[]{18,22,23,28,30,33},
                new int[]{21,25,28,30,35,35},
                new int[]{24,25,33,36,37,40}
        };
        for (int i=0; i<arr.length; i++) {
            System.out.println(JSON.toJSONString(arr[i]));
        }
        System.out.println(s.searchMatrix(arr, 21));
    }

    /*
    [[3,3,8,13,13,18],[4,5,11,13,18,20],[9,9,14,15,23,23],[13,18,22,22,25,27],[18,22,23,28,30,33],[21,25,28,30,35,35],[24,25,33,36,37,40]]
21
     */
}
