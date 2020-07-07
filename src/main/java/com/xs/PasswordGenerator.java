package com.xs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author xs
 * create time:2020-06-28 09:48
 **/
public class PasswordGenerator {

    private enum PasswordDict {
        NUM(new char[] {'0','1','2','3','4','5','6','7','8','9'}),
        LETTER_LOWCASE(new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}),
        LETTER_UPCASE(new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}),
        SPECIAL(new char[] {'!','@','#','$','%','^','&','*','(',')'})
        ;
        private char[] dict;
        private PasswordDict(char[] dict) {
            this.dict = dict;
        }

        public char[] getDict() {
            return dict;
        }
    }
    public static String create(int size, int type) {
        List<Character> dicts = composePasswordDict(type);
        if (dicts == null || dicts.isEmpty()) {
            return null;
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<size; i++) {
            sb.append(dicts.get(random.nextInt(dicts.size())));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(create(16, 991));
    }
    private static List<Character> composePasswordDict(int type) {
        List<Character> finalDict = new ArrayList<>();
        List<Integer> latterEnumIndex = new ArrayList<>();
        PasswordDict[] baseDict = PasswordDict.values();
        int index = 0;
        while (type > 0 && index < baseDict.length) {
            int isHave = type & 1;
            if (isHave > 0) {
                latterEnumIndex.add(index);
            }
            index++;
            type = type >> 1;
        }
        for (Integer i : latterEnumIndex) {
            if (i >= baseDict.length) {
                break;
            }
            for (char c : baseDict[i].dict) {
                finalDict.add(c);
            }
        }
        return finalDict;
    }
}
