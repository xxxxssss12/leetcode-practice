package com.xs.medium.robIII;

import java.util.HashMap;
import java.util.Map;

/**
 * 337. 打家劫舍 III
 * @author xiongshun
 * create time: 2020/8/5 15:28
 */
public class Solution {

    Map<TreeNode, Integer> resultHolder = new HashMap<>();
    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return dfs(root);
    }

    public int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (resultHolder.containsKey(node)) {
            return resultHolder.get(node);
        }
        // 加当前节点
        int resultAdd = 0;
        if (node.left != null) {
            resultAdd += dfs(node.left.left) + dfs(node.left.right);
        }
        if (node.right != null) {
            resultAdd += dfs(node.right.left) + dfs(node.right.right);
        }
        resultAdd += node.val;
        // 不加当前节点
        int resultNoAdd = dfs(node.left) + dfs(node.right);
        int result = Math.max(resultAdd, resultNoAdd);
        resultHolder.put(node, result);
        return result;
    }

    public static void main(String[] args) {
        TreeNode root = generateTree();
        Solution s = new Solution();
        System.out.println(s.rob(root));
    }

    private static TreeNode generateTree() {
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(3);
        TreeNode node5 = new TreeNode(1);
        node1.left = (node2);
        node1.right = (node3);
        node2.right = (node4);
        node3.right = (node5);
        return node1;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

}
