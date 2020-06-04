package com.xs.other.linked;

import com.xs.Utils;

/**
 * @author xs
 * create time:2020-04-29 09:27:14
 */
public class LinkListTest {

    public static void main(String[] args) {
        int[] nums = Utils.createNums(10, 100, true);
        LinkList<Integer> list = initLinkList(nums);
        list.print();
        list.reverseHead();
        list.print();
        list.reverse3Point();
        list.print();
        list.reverseDigui(list.getRoot());
        list.print();
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
