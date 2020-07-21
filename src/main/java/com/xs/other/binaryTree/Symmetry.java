package com.xs.other.binaryTree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 *
 * 判断二叉树是否对称
 * @author xs
 * create time:2020-07-18 11:54
 **/
public class Symmetry {

    StringBuilder sb = null;
    public boolean isSymmetry(Node<Integer> root) {
        sb = new StringBuilder();
        dfsLeft(root, sb);
        String s1 = sb.toString();
        sb = new StringBuilder();
        dfsRight(root, sb);
        String s2 = sb.toString();
        System.out.println(s1);
        System.out.println(s2);
        return s1.equals(s2);
    }

    private static Node<Integer> generateTree() {
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);
        Node<Integer> node5 = new Node<>(5);
        Node<Integer> node6 = new Node<>(6);
        Node<Integer> node7 = new Node<>(7);
        Node<Integer> node8 = new Node<>(2);
        Node<Integer> node9 = new Node<>(3);
        Node<Integer> node10 = new Node<>(4);
        Node<Integer> node11 = new Node<>(5);
        Node<Integer> node12 = new Node<>(6);
        Node<Integer> node13 = new Node<>(7);
        node1.setLeftChild(node2);
        node1.setRightChild(node8);
        node2.setRightChild(node3);
        node8.setLeftChild(node9);
        return node1;
    }
    private void dfsRight(Node<Integer> node, StringBuilder sb) {
        if (node == null) {
            sb.append("!");
            return;
        }
        dfsLeft(node.getRightChild(), sb);
        dfsLeft(node.getLeftChild(), sb);
        sb.append(node.getData());
    }

    private void dfsLeft(Node<Integer> node, StringBuilder sb) {
        if (node == null) {
            sb.append("!");
            return;
        }
        dfsLeft(node.getLeftChild(), sb);
        dfsLeft(node.getRightChild(), sb);
        sb.append(node.getData());
    }

    public static void main(String[] args) {
        Symmetry width = new Symmetry();
        Node<Integer> root = generateTree();
        System.out.println(JSON.toJSONString(root, SerializerFeature.PrettyFormat));
        System.out.println(width.isSymmetry(root));
    }
}
