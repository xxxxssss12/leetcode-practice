package com.xs.other.binaryTree;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 求二叉树上节点最长路径
 * @author xs
 * create time:2020-07-07 11:07:43
 */
public class BinaryMaxWay {

    private Map<Node<?>, WayHolder> map = new HashMap<>();
    private Integer maxWay = -1;
    public int getMaxWay(Node<?> root) {
        Integer rootMaxWay = dfsGet(root);
        printMap(map);
        return maxWay;
    }

    private void printMap(Map<Node<?>, WayHolder> map) {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\n");

        for (Node<?> key : map.keySet()) {
            sb.append(key.getData().toString()).append(":").append(JSON.toJSONString(map.get(key))).append(",");
            sb.append("\n");
        }
        sb.replace(sb.length()-2, sb.length()-1, "").append("}");
        System.out.println(sb.toString());
    }

    /**
     * 深度优先获取当前子树的最大深度
     * @param node
     * @return
     */
    private Integer dfsGet(Node<?> node) {
        WayHolder holder = new WayHolder();
        holder.maxWayLeft = 0;
        holder.maxWayRight = 0;
        if (node.getLeftChild() != null) {
            holder.maxWayLeft = dfsGet(node.getLeftChild()) + 1;
        }
        if (node.getRightChild() != null) {
            holder.maxWayRight = dfsGet(node.getRightChild()) + 1;
        }
        if (maxWay < holder.maxWayRight + holder.maxWayLeft) {
            maxWay = holder.maxWayRight + holder.maxWayLeft;
        }
        if (map.get(node) != null) {
            System.out.println("重复了：node.value=" + node.getData());
        }
        map.put(node, holder);
        return holder.max();
    }

    public static void main(String[] args) {
        BinaryMaxWay maxWay = new BinaryMaxWay();
        Node<Integer> tree = generateTree();
        System.out.println(maxWay.getMaxWay(tree));
    }

    private static Node<Integer> generateTree() {
        Node<Integer> root = new Node<>();
        root.setData(0);
        Node<Integer> left = new Node<>();
        left.setData(1);
        left.setFather(root);
        root.setLeftChild(left);
        Node<Integer> currentNode = root.getLeftChild();
        left = new Node<>();
        left.setData(2);
        left.setFather(currentNode);
        currentNode.setLeftChild(left);
        Node<Integer> left1 = new Node<>();
        left1.setData(8);
        left1.setFather(left);
        left.setLeftChild(left1);
        Node<Integer> right = new Node<>();
        right.setData(3);
        right.setFather(currentNode);
        currentNode.setRightChild(right);
        currentNode = currentNode.getRightChild();
        right = new Node<>();
        right.setData(4);
        right.setFather(currentNode);
        currentNode.setRightChild(right);
        currentNode = currentNode.getRightChild();
        right = new Node<>();
        right.setData(5);
        right.setFather(currentNode);
        currentNode.setRightChild(right);
        currentNode = currentNode.getRightChild();
        right = new Node<>();
        right.setData(6);
        right.setFather(currentNode);
        currentNode.setRightChild(right);
        currentNode = currentNode.getRightChild();
        right = new Node<>();
        right.setData(7);
        right.setFather(currentNode);
        currentNode.setRightChild(right);
        currentNode = currentNode.getRightChild();
        return root;
    }
}

class WayHolder {
    int maxWayLeft;
    int maxWayRight;

    public int getMaxWayLeft() {
        return maxWayLeft;
    }

    public void setMaxWayLeft(int maxWayLeft) {
        this.maxWayLeft = maxWayLeft;
    }

    public int getMaxWayRight() {
        return maxWayRight;
    }

    public void setMaxWayRight(int maxWayRight) {
        this.maxWayRight = maxWayRight;
    }

    public int max() {
        return Math.max(maxWayLeft, maxWayRight);
    }
}