package com.xs.medium.addTwoNumbers;

/**
 * Created by xs on 2018/5/8
 */
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int jinwei=0, a , b;
        ListNode result = null;
        ListNode res = null;
        for (;;) {
            a = l1==null ? 0 : l1.val;
            b = l2==null ? 0 : l2.val;
            int tmp = a + b + jinwei;
            jinwei = tmp / 10;
            ListNode newNode = new ListNode(tmp % 10);
            if (res == null) {
                res = newNode;
                result = newNode;
            } else {
                res.next = newNode;
                res = res.next;
            }
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
            if (l1 == null && l2==null) {
                if (jinwei != 0) {
                    newNode = new ListNode(jinwei);
                    res.next = newNode;
                }
                break;
            }
        }
        return result;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}