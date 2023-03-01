package me.java.leetcode.ch02_recurrsion2.ch02_00_divide_and_conquer;

import me.java.leetcode.TreeNode;

public class P01_ValidBinarySearchTree {
    public boolean isValidBST_wrong(TreeNode root) {
        if (root == null)
            return true;
        if (root.right != null && root.val >= root.right.val)
            return false;
        if (root.left != null && root.val <= root.left.val)
            return false;
        boolean left = isValidBST_wrong(root.left);
        boolean right = isValidBST_wrong(root.right);
        return left && right;
    }

    public boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }

    private boolean validate(TreeNode node, Integer high, Integer low) {
        if (node == null)
            return true;
        if ((high != null && node.val >= high) || (low != null && node.val <= low))
            return false;
        return validate(node.right, high, node.val) && validate(node.left, node.val, low);
    }
}
