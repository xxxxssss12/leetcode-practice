package com.xs.binaryTree;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Practice {

    private static int nodeLimit = 32;
    private static  Random random = new Random();
    private static List<Node<Integer>> nodeList = new ArrayList<>();
    /**
     * 生成一棵随机的树
     * @param nodeNumber
     * @return
     */
    public static Node<Integer> createRandomTree(int nodeNumber, Node<Integer> father) {
        if (nodeList.size() > nodeLimit) return null;
        Node<Integer> node = new Node<>();
        if (father != null) {
            node.setFather(father);
        }
        node.setData(nodeList.size()*1000 + nodeNumber);
        nodeList.add(node);
        int leftChildType = random.nextInt(2);
        int rightChildType = random.nextInt(2);
        if (leftChildType == 1) {
            node.setLeftChild(createRandomTree(nodeNumber+1, node));
        }
        if (rightChildType == 1) {
            node.setRightChild(createRandomTree(nodeNumber+1, node));
        }
        return node;
    }

//    public
    public static void main(String[] args) {
        nodeLimit=32;
        nodeList = new ArrayList<>();
        createRandomTree(0, null);
        System.out.println(JSON.toJSONString(nodeList.get(0)));
        printList(nodeList);
        qianXuBianLi(nodeList.get(0));
        zhongXuBianLi(nodeList.get(0));
        houXuBianLi(nodeList.get(0));
    }

    private static void printList(List<Node<Integer>> nodeList) {
        List<Integer> list = new ArrayList<>();
        nodeList.forEach(node -> list.add((Integer) node.getData()));
        System.out.println("list order:-->" + JSON.toJSONString(list));
    }

    private static void qianXuBianLi(Node<Integer> root) {
        List<Integer> numList = new ArrayList<>();
        qbianli(root, numList);
        System.out.println("qianXuBianLi order:-->" + JSON.toJSONString(numList));
    }


    private static void zhongXuBianLi(Node<Integer> root) {
        List<Integer> numList = new ArrayList<>();
        zbianli(root, numList);
        System.out.println("zhongXuBianLi order:-->" + JSON.toJSONString(numList));
    }

    private static void houXuBianLi(Node<Integer> root) {
        List<Integer> numList = new ArrayList<>();
        hbianli(root, numList);
        System.out.println("houXuBianLi order:-->" + JSON.toJSONString(numList));
    }

    private static void qbianli(Node<Integer> node, List<Integer> list) {
        if (node == null) return;
        list.add(node.getData());
        qbianli(node.getLeftChild(), list);
        qbianli(node.getRightChild(), list);
    }

    private static void zbianli(Node<Integer> node, List<Integer> list) {
        if (node == null) return;
        qbianli(node.getLeftChild(), list);
        list.add(node.getData());
        qbianli(node.getRightChild(), list);
    }

    private static void hbianli(Node<Integer> node, List<Integer> list) {
        if (node == null) return;
        qbianli(node.getLeftChild(), list);
        qbianli(node.getRightChild(), list);
        list.add(node.getData());
    }
}
