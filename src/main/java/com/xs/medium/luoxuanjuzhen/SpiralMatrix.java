package com.xs.medium.luoxuanjuzhen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 54.螺旋矩阵
 * @author xs
 * create time:2020-08-09 11:38
 **/
public class SpiralMatrix {

    private static final int[] yDir = new int[]{1,0,-1,0};
    private static final int[] xDir = new int[]{0,1,0,-1};
    int printCount = 0;
    public void print(int[][] arr) {
        int n = arr.length;
        if (n == 0) {
            return;
        }
        if (n == 1) {
            System.out.println(arr[0][0]);
            return;
        }
        int singleTimes = n - 1;
        int currentLevel = 0;
        int currentDir = 0;
        int currentX = 0;
        int currentY = 0;
        EndPoint end = new EndPoint(n);
        for (int i=0; i<n/2; i++) {
            for (currentDir = 0; currentDir < 4; currentDir++) {
                for (int j=0; j<(currentDir == 3 ? singleTimes-1 :singleTimes); j++) {
                    System.out.print(arr[currentX][currentY] + " ");
                    printCount ++;
                    currentX += xDir[currentDir];
                    currentY += yDir[currentDir];
                    if (currentX == end.x && currentY == end.y) {
                        System.out.print(arr[currentX][currentY] + " ");
                        printCount++;
                        return;
                    }
                }
            }
            System.out.print(arr[currentX][currentY] + " ");
            printCount++;
            currentX += xDir[0];
            currentY += yDir[0];
            if (currentX == end.x && currentY == end.y) {
                printCount++;
                System.out.print(arr[currentX][currentY] + " ");
                return;
            }
            singleTimes -= 2;
        }
    }
    static class EndPoint {
        int x;
        int y;
        public EndPoint(int n) {
            if (n % 2 == 1) {
                this.x = n / 2;
                this.y = n / 2;
            } else {
                this.x = n / 2 - 1;
                this.y = n / 2;
            }
        }
    }

    public static void main(String[] args) {
        SpiralMatrix sm = new SpiralMatrix();
        int[][] arr = sm.generateArr(4);
        System.out.println(JSON.toJSONString(arr, SerializerFeature.PrettyFormat));
        sm.print(arr);
        System.out.println();
        System.out.println(sm.printCount);
    }

    private int[][] generateArr(int n) {
        int[][] arr = new int[n][n];
        int index = 0;
        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr.length; j++) {
                arr[i][j] = index;
                index++;
            }
        }
        return arr;
    }
}
