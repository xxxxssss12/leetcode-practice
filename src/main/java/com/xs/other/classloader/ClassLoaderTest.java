package com.xs.other.classloader;

import java.lang.reflect.InvocationTargetException;

/**
 * @author xs
 * create time:2020-04-29 14:49:55
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> aClass = ClassLoaderTest.class.getClassLoader().loadClass("com.xs.other.classloader.BeanAAA");
//        BeanAAA beanAAA = new BeanAAA();
        System.out.println(aClass.getName());
        aClass.getConstructor().newInstance();
        System.out.println("-----");
        Class<?> bClass = Class.forName("com.xs.other.classloader.BeanAAA");
    }
}
