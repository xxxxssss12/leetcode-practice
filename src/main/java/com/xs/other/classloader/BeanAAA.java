package com.xs.other.classloader;

/**
 * @author xs
 * create time:2020-04-29 14:50:28
 */
public class BeanAAA {
    static {
        System.out.println("静态代码块");
    }
    private static final String str = "静态str";
    private static String str1;

    static {
        str1 = "hehe";
        System.out.println(str1);
    }
}
