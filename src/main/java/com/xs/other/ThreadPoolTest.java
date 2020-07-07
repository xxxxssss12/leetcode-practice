package com.xs.other;

import com.xs.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    private static ExecutorService executor = Utils.generatorExecutor(1, "test");

    public static void main(String[] args) {
        executor.execute(() -> {
            throw new RuntimeException("fuck");
        });
    }
}
