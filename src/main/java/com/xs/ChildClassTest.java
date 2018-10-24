package com.xs;

/**
 * Created by xs on 2018/10/24
 */
public class ChildClassTest {

    public static void main(String[] args) {
        Child child = new Child();
        child.funcA();
    }
}

class Father {
    protected void funcA() {
        System.out.println("father.funcA");
        funcB();
    }

    protected void funcB() {
        System.out.println("father.funcB");
    }
}

class Child extends Father {
    @Override
    protected void funcB() {
        System.out.println("child.funcB");
    }
}
