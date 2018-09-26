package com.xs.spring.cycledepends;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xs on 2018/9/10
 */
public class GetStart {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanA.class, BeanB.class);
        BeanA beanA = applicationContext.getBean(BeanA.class);
        beanA.test();
        BeanB beanB = applicationContext.getBean(BeanB.class);
        beanB.test();

    }
}
