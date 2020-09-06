package com.xs.other.dubbo.demo.service;

/**
 * @author xs
 * create time:2020-08-02 16:00
 **/
public class TestServiceImpl implements TestService {
    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public String print(String str) {
        System.out.println(str);
        return "hello !!!!" + str;
    }
}
