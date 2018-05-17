package com.xs.binaryTree;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Practice {

    private static int nodeLimit = 32;
    private static  Random random = new Random();
    private static List<Node> nodeList = new ArrayList<>();
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

    public static void main(String[] args) {
        nodeLimit=12;
        nodeList = new ArrayList<>();
        createRandomTree(0, null);
        System.out.println(JSON.toJSONString(nodeList.get(0)));
    }
}
