package com.xs.other;

/**
 * @author xiongshun
 * create time: 2020/8/26 9:56
 */
public class FinalTest {

    int i=1;
    public void test() {
        if (i==1) {
            throw new RuntimeException();
        }
        try {
            i=0;
        } finally {
            i=2;
        }
    }
    public static void main(String[] args) {
        FinalTest test = new FinalTest();
        try {
            test.test();
        } catch (Exception ignored) {
        }
        System.out.println(test.i);
    }
}
