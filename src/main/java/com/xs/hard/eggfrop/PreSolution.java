package com.xs.hard.eggfrop;

/**
 * M层楼，2个鸡蛋扔，计算最少可扔次数
 *
 * @author xiongshun
 * create time: 2020/6/2 10:55
 */
public class PreSolution {

    public int superEggDrop(int m) {
        int[] result = new int[m+1];
        result[0] = 1;
        result[1] = 1;
        for (int i=2; i<=m; i++) {
            result[i] = getMin(result, i);
        }
        return result[m];
    }

    private int getMin(int[] result, int i) {
        int min = Integer.MAX_VALUE;
        for (int x=1; x <= i; x++) {
            int count1 = -1;
            if (i-x != 0) {
                count1 = result[i-x] + 1;
            }
            int singleResult = Math.max(x, count1);
            if (min > singleResult) {
                min = singleResult;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        PreSolution preSolution = new PreSolution();
        System.out.println(preSolution.superEggDrop(100));
    }
}
