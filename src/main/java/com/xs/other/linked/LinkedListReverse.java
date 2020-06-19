package com.xs.other.linked;

import com.xs.Utils;

/**
 * 链表翻转走一个
 *
 * @author xs
 * create time:2020-06-13 19:51
 **/
public class LinkedListReverse {
    public static void reverseLinkedList(PointNode<Integer> root) {
        if (root == null) {
            return;
        }
        System.out.println(root);
        PointNode<Integer> first=root,second=root.getNext(),current = second;
        while (current != null) {
            if (current.getNext() == null) {
                current.setNext(first);
                break;
            } else {
                current = second.getNext();
                second.setNext(first);
                if (first == root) {
                    first.setNext(null);
                }
                first = second;
                second = current;
            }
        }
        root = current;
        System.out.println(root);
    }

    public static void main(String[] args) {
        int[] nums = Utils.createNums(10, 100, true);
        LinkList<Integer> integerLinkList = initLinkList(nums);
        reverseLinkedList(integerLinkList.getRoot());
    }

    private static LinkList<Integer> initLinkList(int[] nums) {
        LinkList<Integer> list = new LinkList<>();
        PointNode<Integer> root = new PointNode<>();
        list.setRoot(root);
        PointNode<Integer> a = root;
        for (int i=0;i<nums.length;i++) {
            int num = nums[i];
            a.setData(num);
            if (i<nums.length - 1) {
                PointNode<Integer> next = new PointNode<>();
                a.setNext(next);
                a = a.getNext();
            }
        }
        return list;
    }
}
