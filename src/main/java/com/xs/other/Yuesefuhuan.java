package com.xs.other;

import com.alibaba.fastjson.JSON;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * 约瑟夫环
 *
 * @author xs
 * create time:2020-07-25 21:43
 **/
public class Yuesefuhuan {

    public void solution(int m, int n) {
        Point head = generateLinkedList(m);
        List<Integer> outOrderList = new ArrayList<>();
        Point cursor = head;
        for (;;) {
            for (int i=0;i<n-1;i++) {
                cursor = cursor.next;
            }
            outOrderList.add(cursor.data);
            cursor.prev.next = cursor.next;
            cursor.next.prev = cursor.prev;
            cursor = cursor.next;
            if (cursor.next == cursor) {
                System.out.println(cursor.data);
                System.out.println(JSON.toJSONString(outOrderList));
                return;
            }
        }
    }

    public static void main(String[] args) {
        Yuesefuhuan y = new Yuesefuhuan();
        y.solution(10, 3);
    }
    private Point generateLinkedList(int m) {
        Point head = new Point();
        head.data = 1;
        Point cursor = head;
        for (int i=1; i<m; i++) {
            Point next = new Point();
            next.data = i+1;
            next.prev = cursor;
            cursor.next = next;
            cursor = cursor.next;
        }
        cursor.next = head;
        head.prev = cursor;
        return head;
    }

    static class Point {
        int data;
        Point prev;
        Point next;
    }
}
