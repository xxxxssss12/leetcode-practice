package com.xs.callable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class CallableTest {
    private static ExecutorService executor = Executors.newFixedThreadPool(20);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        List<Future<Integer>> fList = new ArrayList<>();
        for (Integer i=0; i<100; i++) {
            Future<Integer> future = executor.submit(new CallThread(i));
            fList.add(future);
        }
        LinkedList<String> linkedList = new LinkedList<>();
        ArrayList<String> arrayList = new ArrayList<>();

        for (Future<Integer> future : fList) {
            System.out.println("future 出现了！index=" + future.get() + ";hast=" + (System.currentTimeMillis() - startTime));
        }
        System.out.println("耗时" + (System.currentTimeMillis() - startTime));
    }
}
class CallThread implements Callable<Integer> {

    private int index;
    CallThread(Integer index) {
        this.index = index;
    }
    @Override
    public Integer call() throws Exception {
        Thread.sleep(10000);
        return index;
    }
}
