package com.xs.other.heap;

import com.alibaba.fastjson.JSON;
import com.xs.Utils;

/**
 * topK，小顶堆实现
 *
 * @author xs
 * create time:2020-03-29 18:56
 **/
public class TopK {
    public static void main(String[] args) {
        Heap heap = new Heap(7);
        int[] arr = Utils.createNums(100, 101, true);
//        int[] arr = new int[]{1,5,9,4,6};
        for (int i=0; i<arr.length; i++) {
            heap.add(arr[i]);
            heap.printResult();
        }
        heap.printResult();
    }
}

class Heap {
    private int capacity;
    private Integer[] arr;
    private int currentCount; // 当前堆中有多少数据

    public Heap(int capacity) {
        this.capacity = capacity;
        this.arr = new Integer[capacity];
        this.currentCount = 0;
    }

    public void add(Integer data) {
        // index=0是最小值
        if (data == null) {
            return;
        }
        if (currentCount == 0) {
            arr[0] = data;
            currentCount ++;
        } else if (capacity > currentCount) {
            // 容量不足，直接放到堆尾部，然后上浮
            arr[currentCount] = data;
            // 上浮data，保证最小堆
            currentCount ++;
            up(currentCount - 1);
        } else if (arr[0] < data) {
            // 最小堆中最小值小于data，替换arr[0]，然后下沉
            arr[0] = data;
            sink(0);
        }
    }

    private void up(int index) {
        // 上浮-最小值上浮
        int cursor = index;
        for (;;) {
            int fatherIndex = getFatherIndex(cursor);
            if (fatherIndex == -1) {
                return;
            }
            Integer fatherData = arr[fatherIndex];
            int rightChildIndex = -1;
            int leftChildIndex = -1;
            if (cursor % 2 == 0) {
                rightChildIndex = cursor;
                leftChildIndex = cursor - 1;
            } else {
                leftChildIndex = cursor;
                rightChildIndex = cursor + 1;
            }
            int compareResult = compare(fatherData, arr[leftChildIndex], rightChildIndex >= currentCount ? null : arr[rightChildIndex]);
            if (compareResult == 0) {
                return;
            } else if (compareResult == 1){
                swap(fatherIndex, leftChildIndex);
//                sink(leftChildIndex);
                cursor = fatherIndex;
            } else if (compareResult == 2) {
                swap(fatherIndex, rightChildIndex);
//                sink(rightChildIndex);
                cursor = fatherIndex;
            }

        }
    }

    private int getFatherIndex(int index) {
        if (index == 0) {
            return -1;
        }
        return (index + 1) / 2 - 1;
    }

    private void sink(int index) {
        int cursor = index;
        // 下沉-大值下沉
        for (;;) {
            int leftChildIndex = getLeftChildIndex(cursor);
            Integer leftData = leftChildIndex < capacity ? arr[leftChildIndex] : null;
            Integer rightData = leftChildIndex + 1 < capacity ? arr[leftChildIndex + 1] : null;
            int compareResult = compare(arr[cursor], leftData, rightData);
            if (compareResult == 0) {
                return;
            } else if (compareResult == 1) {
                swap(cursor, leftChildIndex);
                cursor = leftChildIndex;
            } else {
                swap(cursor, leftChildIndex + 1);
                cursor = leftChildIndex + 1;
            }
        }
    }

    private void swap(int cursor, int leftChildIndex) {
        int tmp = arr[cursor];
        arr[cursor] = arr[leftChildIndex];
        arr[leftChildIndex] = tmp;
    }

    private int compare(Integer currentData, Integer leftData, Integer rightData) {
        int result = 0;// 0:当前节点最小；1:左子树最小;2:右子树最小
        if (leftData == null) {
            return 0;
        }
        if (rightData == null) {
            if (currentData <= leftData) {
                return 0;
            } else {
                return 1;
            }
        }
        // 都不为空
        if (currentData <= leftData && currentData <= rightData) {
            return 0;
        } else if (currentData > leftData && currentData < rightData) {
            return 1;
        } else if (currentData > rightData && currentData < leftData) {
            return 2;
        } else if (leftData <= rightData) {
            // currentData比左右儿子都大。把小的上位
            return 1;
        } else {
            return 2;
        }
    }

    private int getLeftChildIndex(int cursor) {
        return (cursor + 1) * 2 - 1;
    }

    private int getIndex(int currentIndex, Integer data) {
        if (arr[currentIndex] > data) {
            if ((currentIndex+1) * 2 - 1 < capacity) {
                return getIndex((currentIndex + 1) * 2 - 1, data);
            }
            if ((currentIndex + 1) * 2 < capacity) {
                return getIndex((currentIndex + 1) * 2, data);
            }
            return -1;
        } else {
            return currentIndex;
        }
    }

    public void printResult() {
        System.out.println(JSON.toJSONString(this.arr));
    }
}