package com.xs.jse.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by xs on 2019/1/28
 */
public class TestFuture implements Callable<BeanA> {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<BeanA> task = new FutureTask<>(new TestFuture());
        new Thread(task).start();
        System.out.println("线程启动");
        BeanA a = task.get();
        System.out.println("线程结束");
        System.out.println(a);
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