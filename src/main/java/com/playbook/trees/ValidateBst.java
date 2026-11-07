package com.playbook.trees;

import java.util.ArrayDeque;
import java.util.Deque;

/** Is the tree a valid BST (left subtree < node < right subtree, strictly, at EVERY level)? */
public final class ValidateBst {
    private ValidateBst() {}

    /** Wrong-but-tempting: only compares with direct children. Kept to show the bug in the docs and tests. */
    public static boolean naiveChildrenOnly(TreeNode n) {
        if (n == null) return true;
        if (n.left != null && n.left.val >= n.val) return false;
        if (n.right != null && n.right.val <= n.val) return false;
        return naiveChildrenOnly(n.left) && naiveChildrenOnly(n.right);
    }

    /** Correct: pass the allowed (low, high) range down. long bounds so Integer.MIN/MAX values work. O(n). */
    public static boolean range(TreeNode root) {
        return range(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean range(TreeNode n, long low, long high) {
        if (n == null) return true;
        if (n.val <= low || n.val >= high) return false;
        return range(n.left, low, n.val) && range(n.right, n.val, high);
    }

    /** In-order of a BST is strictly increasing: iterate and compare with the previous value. O(n). */
    public static boolean inorder(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        Long prev = null;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (prev != null && curr.val <= prev) return false;
            prev = (long) curr.val;
            curr = curr.right;
        }
        return true;
    }

    /** BST insert (used by tests to build valid trees). */
    public static TreeNode insert(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insert(root.left, val);
        else if (val > root.val) root.right = insert(root.right, val);
        return root;
    }
}
