package me.java.leetcode.ch01_recurrsion.ch01_02_Complexity;

import me.java.leetcode.TreeNode;

public class P05_MaximumDepthOfBinary {
    public int maxDepth(TreeNode root) {
        return getMax(root);
    }

    private int getMax(TreeNode node) {
        if (node == null)
            return 0;
        if (node.left == null && node.right == null)
            return 1;
        int left = 0;
        int right = 0;
        if (node.left != null)
            left =  getMax(node.left) + 1;
        if (node.right != null)
            right = getMax(node.right) + 1;
        return left >= right ? left : right;
    }
}
