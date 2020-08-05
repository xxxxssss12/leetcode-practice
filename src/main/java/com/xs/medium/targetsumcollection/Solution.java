package com.xs.medium.targetsumcollection;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    List<List<Integer>> result = new ArrayList<>();
    SolutionHolder holder = new SolutionHolder();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates == null || candidates.length < 1) {
            return result;
        }
        Arrays.sort(candidates);
        for (int i=0; i<candidates.length; i++) {
            if (i > 0 && candidates[i] == candidates[i-1]) {
                continue;
            }
            if (candidates[i] > target) {
                break;
            }
            searchDfs(candidates, target, i, 0);
        }
        return result;
    }

    public void searchDfs(int[] candidates, int target, int currentIndex, int currentSum) {
        holder.currentSum += candidates[currentIndex];
        holder.result.add(candidates[currentIndex]);
        if (holder.currentSum == target) {
            result.add(new ArrayList<>(holder.result));
        } else if (holder.currentSum < target) {
            for (int i = currentIndex + 1; i<candidates.length; i++) {
                if (holder.currentSum + candidates[i] > target) {
                    break;
                }
                if (candidates[i] == candidates[i-1] && i != currentIndex + 1) {
                    continue;
                }
                searchDfs(candidates, target, i, holder.currentSum);
            }
        }
        holder.currentSum -= candidates[currentIndex];
        holder.result.remove(holder.result.size() - 1);
    }

    class SolutionHolder {
        int currentSum = 0;
        List<Integer> result = new ArrayList<>();
    }

}



