package com.xs.other.spring.cycledepends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xs on 2018/9/10
 */
@Component
public class BeanB {
    @Autowired
    private BeanA beanA;

    public void test() {
        System.out.println("beanB out");
    }
}
