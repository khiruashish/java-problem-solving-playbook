package com.playbook.trees;

import java.util.ArrayList;
import java.util.List;

/** Lowest common ancestor of two values that exist in the tree (a node may be its own ancestor). */
public final class LowestCommonAncestor {
    private LowestCommonAncestor() {}

    /** Path comparison: record root→p and root→q paths, the last common node is the LCA. O(n), O(h). */
    public static TreeNode viaPaths(TreeNode root, int p, int q) {
        List<TreeNode> a = new ArrayList<>(), b = new ArrayList<>();
        if (!path(root, p, a) || !path(root, q, b)) return null;
        TreeNode lca = null;
        for (int i = 0; i < Math.min(a.size(), b.size()) && a.get(i) == b.get(i); i++) lca = a.get(i);
        return lca;
    }

    private static boolean path(TreeNode n, int target, List<TreeNode> out) {
        if (n == null) return false;
        out.add(n);
        if (n.val == target || path(n.left, target, out) || path(n.right, target, out)) return true;
        out.remove(out.size() - 1);                          // backtrack: target not under n
        return false;
    }

    /**
     * Optimal single pass: return the node if it is p or q; otherwise ask both subtrees.
     * Both non-null → this node is where the paths split → LCA. One non-null → pass it up. O(n), O(h).
     */
    public static TreeNode recursive(TreeNode root, int p, int q) {
        if (root == null || root.val == p || root.val == q) return root;
        TreeNode left = recursive(root.left, p, q), right = recursive(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    /** BST: walk down; both smaller → go left, both bigger → go right, otherwise we're at the split. O(h), O(1). */
    public static TreeNode bst(TreeNode root, int p, int q) {
        TreeNode n = root;
        while (n != null) {
            if (p < n.val && q < n.val) n = n.left;
            else if (p > n.val && q > n.val) n = n.right;
            else return n;
        }
        return null;
    }
}
