package com.xs.jse.future;

import java.util.concurrent.Callable;

/**
 * Created by xs on 2019/1/28
 */
public class TestFuture implements Callable<BeanA> {
    public static void main(String[] args) {

    }
    @Override
    public BeanA call() throws Exception {
        Thread.sleep(5000);

        return new BeanA(1,true, "hahaha");
    }
}

class BeanA {
    private Integer id;
    private Boolean flag;
    private String msg;

    public BeanA(Integer id, Boolean flag, String msg) {
        this.id = id;
        this.flag = flag;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BeanA{" +
                "id=" + id +
                ", flag=" + flag +
                ", msg='" + msg + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}