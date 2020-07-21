package com.xs.other.binaryTree;

import java.util.Stack;

/**
 * 用栈遍历二叉树
 *
 * @author xs
 * create time:2020-07-19 10:59
 **/
public class StackErgodic {

    public static String ergodic(Node<Integer> root) {
        Stack<Node<Integer>> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        Node<Integer> currentNode = root;
        do {
            while (currentNode != null) {
                stack.push(currentNode);
                sb.append(currentNode.getData()).append(" ");
                currentNode = currentNode.getLeftChild();
            }
            currentNode = stack.pop().getRightChild();
        } while(!stack.isEmpty() || currentNode != null);
        return sb.toString();
    }

    public static void main(String[] args) {
        Node<Integer> root = StackErgodic.generateTree();
        System.out.println(ergodic(root));
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
