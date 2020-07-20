package com.xs.other.binaryTree;

import com.alibaba.fastjson.annotation.JSONField;

public class Node<T> {
    @JSONField(serialize=false,deserialize=false)
    private Node<T> father;
    private T data;
    private int pos = 0;    // node的下标
    private Node<T> leftChild;
    private Node<T> rightChild;

    public Node() {
    }
    public Node(T data) {
        this.data = data;
    }
    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Node<T> getFather() {
        return father;
    }

    public void setFather(Node<T> father) {
        this.father = father;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        if (leftChild != null) {
            leftChild.pos = this.pos * 2 + 1;
        }
        this.leftChild = leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        if (rightChild != null) {
            rightChild.pos = this.pos * 2 + 2;
        }
        this.rightChild = rightChild;
    }
}
