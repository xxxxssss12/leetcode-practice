package com.xs.other.jse;

import com.xs.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;

/**
 * @author xiongshun
 * create time: 2020/6/29 9:25
 */
public class LamdaTest {

    public static void main(String[] args) {
        Arrays.stream(Utils.createNums(10, 100, false)).filter(new IntPredicate() {
            @Override
            public boolean test(int value) {
                if (value > 0) {
                    return true;
                }
                return false;
            }
        }).forEach(System.out::println);
        List<Integer> aList = new ArrayList<>();
        aList.stream().forEach(System.out::println);
    }
}
