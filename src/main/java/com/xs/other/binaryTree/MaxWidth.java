package com.xs.other.binaryTree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.*;

/**
 * 求二叉树的最大宽度
 *
 * @author xs
 * create time:2020-07-18 10:17
 **/
public class MaxWidth {

    Map<Integer, Integer> levelLeftPos = new HashMap<>();
    public int maxWidth(Node<Integer> root) {
        return dfsGetMax(root, 0, 0);
    }

    public static void main(String[] args) {
        MaxWidth width = new MaxWidth();
        Node<Integer> root = generateTree();
        System.out.println(JSON.toJSONString(root, SerializerFeature.PrettyFormat));
        System.out.println(width.maxWidth(root));
    }
    private int dfsGetMax(Node<Integer> node, int level, int currentMax) {
        if (node == null) {
            return currentMax;
        }
        if (levelLeftPos.get(level) == null) {
            levelLeftPos.put(level, node.getPos());
        } else if (node.getPos() - levelLeftPos.get(level) > currentMax) {
            currentMax = node.getPos() - levelLeftPos.get(level);
        }
        int max1 = dfsGetMax(node.getLeftChild(), level + 1, currentMax);
        int max2 = dfsGetMax(node.getRightChild(), level + 1, currentMax);
        return max(currentMax, max1, max2);
    }

    private int max(int currentMax, int max1, int max2) {
        return Math.max(currentMax, Math.max(max1, max2));
    }


    private static Node<Integer> generateTree() {
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);
        Node<Integer> node5 = new Node<>(5);
        Node<Integer> node6 = new Node<>(6);
        Node<Integer> node7 = new Node<>(7);
        Node<Integer> node8 = new Node<>(8);
        Node<Integer> node9 = new Node<>(9);
        Node<Integer> node10 = new Node<>(10);
        Node<Integer> node11 = new Node<>(11);
        Node<Integer> node12 = new Node<>(12);
        Node<Integer> node13 = new Node<>(13);
        node1.setLeftChild(node2);
        node1.setRightChild(node3);
        node2.setLeftChild(node4);
        node4.setRightChild(node5);
        node5.setLeftChild(node6);
        node5.setRightChild(node7);
        node7.setLeftChild(node8);
        node7.setRightChild(node9);
        node3.setRightChild(node10);
        node10.setRightChild(node11);
        node11.setRightChild(node12);
        node12.setLeftChild(node13);
        return node1;
    }
}

