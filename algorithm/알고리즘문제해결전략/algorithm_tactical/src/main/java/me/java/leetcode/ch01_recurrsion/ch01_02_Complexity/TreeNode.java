package me.java.leetcode.ch01_recurrsion.ch01_02_Complexity;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    TreeNode() {}
    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
