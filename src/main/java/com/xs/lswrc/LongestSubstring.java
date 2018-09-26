package com.xs.lswrc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xs on 2018/7/20
 */
public class LongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        int headIndex = 0;
        int endIndex = 0;
        Map<Character, Integer> charIndexMap = new HashMap<>();
        List<String> noRepeatSubStringList = new ArrayList<>();
        int maxLength = 0;
        int maxLengthIndex = 0;
        do {
            Integer lastRepeatIndex = charIndexMap.get( s.charAt( endIndex));
            if (lastRepeatIndex != null && lastRepeatIndex >= headIndex) {
                noRepeatSubStringList.add(s.substring(headIndex, endIndex));
                int temphead = charIndexMap.get(s.charAt(endIndex));
                if (temphead >= headIndex) headIndex = temphead;
                headIndex += 1;
                if (noRepeatSubStringList.get(noRepeatSubStringList.size() - 1).length() > maxLength) {
                    maxLength = noRepeatSubStringList.get(noRepeatSubStringList.size()-1).length();
                    maxLengthIndex = noRepeatSubStringList.size() - 1;
                }
            }
            charIndexMap.put(s.charAt(endIndex), endIndex);
            endIndex++;
        } while (endIndex < s.length() && headIndex < s.length());
        noRepeatSubStringList.add(s.substring(headIndex, endIndex));
        if (noRepeatSubStringList.get(noRepeatSubStringList.size() - 1).length() > maxLength) {
            maxLength = noRepeatSubStringList.get(noRepeatSubStringList.size()-1).length();
            maxLengthIndex = noRepeatSubStringList.size() - 1;
        }
//        System.out.println(noRepeatSubStringList.get(maxLengthIndex));
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(new LongestSubstring().lengthOfLongestSubstring("abbat"));
    }
}
