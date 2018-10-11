package com.xs.spring.cycledepends;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by xs on 2018/9/10
 */
public class GetStart {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanA.class, BeanB.class);
        BeanA beanA = (BeanA) applicationContext.getBean("beanA");
        beanA.test();
        BeanB beanB = (BeanB) applicationContext.getBean("beanB");
        beanB.test();

    }
}
