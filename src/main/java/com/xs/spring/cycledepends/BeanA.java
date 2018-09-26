package com.xs.spring.cycledepends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

/**
 * Created by xs on 2018/9/10
 */
@Repository
public class BeanA {
    @Autowired
    private BeanB beanB;

    public void test() {
        System.out.println("beanA out");
    }
}
