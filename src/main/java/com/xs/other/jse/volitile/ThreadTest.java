package com.xs.other.jse.volitile;

import com.alibaba.fastjson.JSON;

public class ThreadTest {
    private BeanA beanA = new BeanA("a", false);
    public static void main(String[] args) throws InterruptedException {
        ThreadTest test = new ThreadTest();
        System.out.println(JSON.toJSONString(test.beanA));
        System.out.println(test.beanA.getStrA() + ";" + test.beanA.isBooA());
        Thread t1 = new Thread(() -> {
            test.beanA.setBooA(true);
            test.beanA.setStrA("hehe");
        });
        t1.start();
        Thread.sleep(1000);
        System.out.println(test.beanA.getStrA() + ";" + test.beanA.isBooA());
        t1.join();
        System.out.println(test.beanA.getStrA() + ";" + test.beanA.isBooA());
    }
}
class TThread {

}
class BeanA {
    private String strA;
    private boolean booA;

    public BeanA(String strA, boolean booA) {
        this.strA = strA;
        this.booA = booA;
    }

    public String getStrA() {
        return strA;
    }

    public void setStrA(String strA) {
        this.strA = strA;
    }

    public boolean isBooA() {
        return booA;
    }

    public void setBooA(boolean booA) {
        this.booA = booA;
    }
}