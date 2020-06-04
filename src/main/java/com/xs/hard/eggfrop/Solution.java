package com.xs.hard.eggfrop;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xs
 * create time:2020-05-24 18:57:15
 */
public class Solution {

    int[] result;
    int[] lessOneEggResult;
    public int superEggDrop(int K, int N){
        if (N == 1) {
            return 1;
        }
        int[][] f = new int[N + 1][K + 1];
        for (int i = 1; i <= K; ++i) {
            f[1][i] = 1;
        }
        int ans = -1;
        for (int i = 2; i <= N; ++i) {
            for (int j = 1; j <= K; ++j) {
                f[i][j] = 1 + f[i - 1][j - 1] + f[i - 1][j];
            }
            if (f[i][K] >= N) {
                ans = i;
                break;
            }
        }
        return ans;
    }

    /**
     *
     * @param K 鸡蛋数量
     * @param N 楼层数量
     * @return
     */
    public int superEggDrop2(int K, int N) {
        init(N);
        for (int i=2; i<=K; i++) {
            output(i-1);
            lessOneEggResult = result;
            result = new int[N+1];
            result[1] = 1;
            for (int j=2; j<=N; j++) {
                result[j] = getMin(j);
            }
        }
        output(K);
        return result[N];
    }

    private void output(int k) {
        System.out.print("有" + (k) + "个鸡蛋: ");
        List<Integer> alist = new ArrayList<>();
        for (int i=1;i<result.length;i++) {
            alist.add(result[i]);
        }
        System.out.println(JSON.toJSONString(alist));
    }

    public int superEggDrop3(int K, int N) {
        return -1;
    }
    /**
     * 只有一个鸡蛋的数据处理（初始化）
     * @param n 楼层数量
     */
    private void init(int n) {
        result = new int[n + 1];
        result[0] = 1;
        for (int i=1; i<=n; i++) {
            result[i] = i;
        }
    }

    /**
     * @param j 第j层楼
     */
    private int getMin(int j) {
        int min = Integer.MAX_VALUE; // min = MIN(max(f(j-x, n) + 1, f(j-1, n-1) + 1)), 1<=x<=m, x为整数
        for (int x=1; x <= j; x++) {
            int countBroke = -1; // 碎了：countBroke = f(x-1, n-1) + 1
            int countNoBroke = -1;  // 没碎：countNoBroke = f(j-x, n) + 1
            if (j-x > 0) {
                countNoBroke = result[j-x] + 1;
            }
            if (x-1 > 0) {
                countBroke = lessOneEggResult[x-1] + 1;
            }
            int singleResult = Math.max(countBroke, countNoBroke);
            if (min > singleResult) {
                min = singleResult;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.superEggDrop2(4, 50));
    }
}
