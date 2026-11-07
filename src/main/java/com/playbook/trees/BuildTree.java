package com.playbook.trees;

import java.util.HashMap;
import java.util.Map;

/** Rebuild a binary tree (distinct values) from its pre-order and in-order traversals. Also from post + in. */
public final class BuildTree {
    private BuildTree() {}

    /**
     * Pre-order's first value is the root. Its position in in-order splits left subtree | right subtree, and the
     * left size tells where the right subtree starts in pre-order. Index map → O(n) total instead of O(n^2).
     */
    public static TreeNode fromPreIn(int[] preorder, int[] inorder) {
        Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) pos.put(inorder[i], i);
        return preIn(preorder, 0, preorder.length - 1, 0, pos);
    }

    private static TreeNode preIn(int[] pre, int preLo, int preHi, int inLo, Map<Integer, Integer> pos) {
        if (preLo > preHi) return null;
        TreeNode root = new TreeNode(pre[preLo]);
        int inRoot = pos.get(root.val), leftSize = inRoot - inLo;
        root.left = preIn(pre, preLo + 1, preLo + leftSize, inLo, pos);
        root.right = preIn(pre, preLo + leftSize + 1, preHi, inRoot + 1, pos);
        return root;
    }

    /** Post-order's LAST value is the root; otherwise identical. */
    public static TreeNode fromPostIn(int[] postorder, int[] inorder) {
        Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) pos.put(inorder[i], i);
        return postIn(postorder, 0, postorder.length - 1, 0, pos);
    }

    private static TreeNode postIn(int[] post, int lo, int hi, int inLo, Map<Integer, Integer> pos) {
        if (lo > hi) return null;
        TreeNode root = new TreeNode(post[hi]);
        int inRoot = pos.get(root.val), leftSize = inRoot - inLo;
        root.left = postIn(post, lo, lo + leftSize - 1, inLo, pos);
        root.right = postIn(post, lo + leftSize, hi - 1, inRoot + 1, pos);
        return root;
    }
}
