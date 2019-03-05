package com.xs.other.binaryTree;

import com.alibaba.fastjson.annotation.JSONField;

public class Node<T> {
    @JSONField(serialize=false,deserialize=false)
    private Node<T> father;
    private T data;
    private Node<T> leftChild;
    private Node<T> rightChild;

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
        this.leftChild = leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }
}
