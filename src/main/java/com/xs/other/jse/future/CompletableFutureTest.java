package com.xs.other.jse.future;

import com.xs.Utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author xs
 * create time:2020-07-15 22:17
 **/
public class CompletableFutureTest {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        CompletableFutureTest test = new CompletableFutureTest();
        // CompletableFutureTest局限是无法设置每个任务的超时时间，所以不合适在生产环境使用
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                return test.getString(10000 + Utils.random(999, true));
            } catch (Exception e) {
                return null;
            }
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                return test.getString(20000 + Utils.random(999, true));
            } catch (Exception e) {
                return null;
            }
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                return test.getString(30000 + Utils.random(999, true));
            } catch (Exception e) {
                return null;
            }
        });
        CompletableFuture.allOf(future1, future2, future3).thenAccept((Void) -> {
            try {
                System.out.println("到这里耗时" + (System.currentTimeMillis() - start));
                System.out.println(future1.get(1000, TimeUnit.MILLISECONDS));
                System.out.println(future2.get(1000, TimeUnit.MILLISECONDS));
                System.out.println(future3.get(1000, TimeUnit.MILLISECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
        Thread.sleep(10000L);
    }

    private String getString(int i) throws InterruptedException {
        if (i > 30000) {
            throw new RuntimeException("fuck1");
        }
        Thread.sleep(1000 + Utils.random(1000, false));
        return String.valueOf(i);
    }
}
