package com.xs.other.heap;

import com.xs.Utils;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 简单优先队列
 * 1. 定长
 * 2. 线程安全
 * 3. 性能差
 * 4. 里面只有数字
 *
 * create time:2020-07-11 10:16
 **/
public class MyPriorityQueue {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = Utils.generatorExecutor(20, "fake");
        int[] nums = Utils.createNums(10, 100, true);
        MyPriorityQueue queue = new MyPriorityQueue(10);
        for (int i=0; i<nums.length; i++ ) {
            executor.execute(() -> {
                try {
                    System.out.println(queue.poll(-1L));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(1000);
        for (int num : nums) {
            executor.execute(() -> {
                queue.add(num);
            });
        }
    }
    private int capacity;
    private int currentSize;
    private Integer[] dataArr;

    public MyPriorityQueue(int capacity) {
        if (capacity <= 0) {
            throw new RuntimeException("capacity less than 0");
        }
        this.capacity = capacity;
        this.currentSize = 0;
        this.dataArr = new Integer[capacity];
    }
    public synchronized int size() {
        return this.currentSize;
    }
    public synchronized boolean add(int num) {
        if (this.capacity == this.currentSize) {
            throw new RuntimeException("queue is full, can't add");
        }
        this.dataArr[this.currentSize] = num;
        this.currentSize ++;
        doFloat();
        this.notify();
        return true;
    }

    /**
     * 上浮
     */
    private void doFloat() {
        int fatherIndex = this.currentSize / 2 - 1;
        while (fatherIndex >= 0) {
            int leftIndex = fatherIndex * 2 + 1;
            int rightIndex = fatherIndex * 2 + 2;
            int maxNodeType = compare(fatherIndex, leftIndex, rightIndex);
            if (maxNodeType > 0) {
                if (maxNodeType == 1) {
                    swap(leftIndex, fatherIndex);
                } else if (maxNodeType == 2) {
                    swap(rightIndex, fatherIndex);
                }
                fatherIndex = (fatherIndex+1) / 2 - 1;
            } else {
                break;
            }
        }
    }

    private void swap(int aIndex, int bIndex) {
        int tmp = this.dataArr[aIndex];
        this.dataArr[aIndex] = this.dataArr[bIndex];
        this.dataArr[bIndex] = tmp;
    }

    /**
     *
     * @param fatherIndex
     * @param leftIndex
     * @param rightIndex
     * @return 0爸爸最大，1左节点最大，2右节点最大
     */
    private int compare(int fatherIndex, int leftIndex, int rightIndex) {
        int maxNodeType = 0;
        int maxValue = this.dataArr[fatherIndex];
        if (leftIndex < this.currentSize && maxValue < this.dataArr[leftIndex] ) {
            maxNodeType = 1;
            maxValue = this.dataArr[leftIndex];
        }
        if (rightIndex < this.currentSize && maxValue < this.dataArr[rightIndex] ) {
            maxNodeType = 2;
        }
        return maxNodeType;
    }

    public synchronized int poll(long timeoutMs) throws InterruptedException {
        while (this.currentSize <= 0) {
            if (timeoutMs <= 0) {
                this.wait();
            } else {
                this.wait(timeoutMs);
                if (this.currentSize <= 0) {
                    throw new InterruptedException("poll timeout");
                }
            }
        }
        int result = this.dataArr[0];
        this.dataArr[0] = this.dataArr[currentSize - 1];
        this.dataArr[currentSize - 1] = null;
        this.currentSize --;
        doSink();
        return result;
    }

    /**
     * 下沉
     */
    private void doSink() {
        if (this.currentSize <= 1) {
            return;
        }
        int cursor = 0;
        do {
            int leftIndex = cursor * 2 + 1;
            int rightIndex = cursor * 2 + 2;
            int maxNodeType = compare(cursor, leftIndex, rightIndex);
            if (maxNodeType > 0) {
                if (maxNodeType == 1) {
                    swap(leftIndex, cursor);
                    cursor = leftIndex;
                } else if (maxNodeType == 2) {
                    swap(rightIndex, cursor);
                    cursor = rightIndex;
                }
            } else {
                break;
            }
        } while (cursor < this.currentSize);
    }
}
