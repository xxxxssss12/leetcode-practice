package com.xs.other.heap;

import com.xs.Utils;

import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xiongshun
 * create time: 2020/7/28 10:12
 */
public class MyPriorityQueue2 {
    private Integer[] dataArray = null;
    private int capacity;
    private int size = 0;
    public MyPriorityQueue2(int capacity) {
        this.capacity = capacity;
        this.dataArray = new Integer[capacity];
    }

    public boolean add(int value) {
        if (size < capacity) {
            dataArray[size] = value;
            size++;
            upflow();
            return true;
        } else {
            return false;
        }
    }

    public Integer poll() {
        if (size <= 0) {
            return null;
        } else {
            Integer value = dataArray[0];
            dataArray[0] = dataArray[size - 1];
            dataArray[size - 1] = null;
            size --;
            sink();
            return value;
        }
    }

    private void upflow() {
        if (size <= 1) {
            return;
        }
        int currentIndex = size - 1;
        for (;;) {
            int fatherIndex = getFatherIndex(currentIndex);
            int leftIndex = isLeftIndex(currentIndex) ? currentIndex : getLeftIndex(currentIndex);
            int rightIndex = !isLeftIndex(currentIndex) ? currentIndex : getRightIndex(leftIndex);
            int maxType = getMaxIndex(fatherIndex, leftIndex, rightIndex);
            if (maxType == 0 || maxType == -1) {
                return;
            } else if (maxType == 1) {
                swap(leftIndex, fatherIndex);
                currentIndex = fatherIndex;
                continue;
            } else {
                swap(rightIndex, fatherIndex);
                currentIndex = fatherIndex;
                continue;
            }
        }
    }

    private void sink() {
        if (size <= 1) {
            return;
        }
        int currentIndex = 0;
        for (;;) {
            int fatherIndex = currentIndex;
            int leftIndex = currentIndex * 2 + 1;
            int rightIndex = currentIndex * 2 + 2;
            int maxType = getMaxIndex(fatherIndex, leftIndex, rightIndex);
            if (maxType == 0 || maxType == -1) {
                return;
            } else if (maxType == 1) {
                swap(leftIndex, fatherIndex);
                currentIndex = leftIndex;
                continue;
            } else {
                swap(rightIndex, fatherIndex);
                currentIndex = rightIndex;
                continue;
            }
        }
    }
    private void swap(int indexA, int indexB) {
        int tmp = dataArray[indexA];
        dataArray[indexA] = dataArray[indexB];
        dataArray[indexB] = tmp;
    }
    private int getMaxIndex(int fatherIndex, int leftIndex, int rightIndex) {
        if (fatherIndex < 0) {
            return 0;
        }
        int maxIndex = 0;
        int max = dataArray[fatherIndex];
        if (leftIndex > 0 && leftIndex < size && dataArray[leftIndex] > max) {
            maxIndex = 1;
            max = dataArray[leftIndex];
        }
        if (rightIndex > 0 && rightIndex < size && dataArray[rightIndex] > max) {
            maxIndex = 2;
        }
        return maxIndex;
    }
    private int getFatherIndex(int currentIndex) {
        return (currentIndex+1) / 2 - 1;
    }

    private boolean isLeftIndex(int currentIndex) {
        if (currentIndex == 0) {
            return false;
        }
        return currentIndex % 2 == 1;
    }

    private int getLeftIndex(int rightIndex) {
        if (rightIndex == 0) {
            return -1;
        }
        return rightIndex - 1;
    }

    private int getRightIndex(int leftIndex) {
        if (leftIndex == 0) {
            return -1;
        }
        if (leftIndex + 1 < size) {
            return leftIndex + 1;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        MyPriorityQueue2 queue = new MyPriorityQueue2(10);
        Random ran = new Random();
        queue.add(ran.nextInt(1000));
        queue.add(ran.nextInt(1000));
        queue.add(ran.nextInt(1000));
        queue.add(ran.nextInt(1000));
        queue.add(ran.nextInt(1000));
        queue.add(ran.nextInt(1000));
        queue.add(ran.nextInt(1000));
        queue.add(ran.nextInt(1000));
        queue.add(ran.nextInt(1000));
        queue.add(ran.nextInt(1000));
        Integer value = null;
        while((value = queue.poll()) != null) {
            System.out.println(value);
        }

    }
}
