package com.xs.medium.tiaoshiban;

import com.alibaba.fastjson.JSON;

/**
 * @author xs
 * create time:2020-05-18 09:01:57
 */
public class Solution {

    public int getMinCountDp(int currentPosition, int distination) {
        int[] dp = new int[distination+1];
        System.out.println(JSON.toJSONString(dp));
        for (int i=currentPosition; i<distination; i++) {
            if (i != currentPosition && dp[i] == 0) {
                // 不可达
                continue;
            }
            for(int j=2;(j*j)<=i;j++){
                if(i%j == 0){
                    if(i+j <= distination){
                        if (dp[i+j] == 0) {
                            dp[i+j] = dp[i]+1;
                        } else {
                            dp[i + j] = Math.min(dp[i] + 1, dp[i + j]);
                        }
                    }
                    if(i+(i/j) <= distination){
                        if (dp[i + (i / j)] == 0) {
                            dp[i + (i / j)] = dp[i]+1;
                        } else {
                            dp[i + (i / j)] = Math.min(dp[i] + 1, dp[i + (i / j)]);
                        }
                    }
                }
            }
        }
        System.out.println(JSON.toJSONString(dp));
        if (dp[distination] == 0) {
            return -1;
        }
        return dp[distination];
    }

    public static void main(String[] args) {
        System.out.println(new Solution().getMinCountDp(6,44));
    }
}
