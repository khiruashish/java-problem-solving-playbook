package com.playbook.trees;

import java.util.ArrayDeque;
import java.util.Deque;

/** Maximum depth (number of nodes on the longest root-to-leaf path) and minimum depth. Empty tree → 0. */
public final class TreeHeight {
    private TreeHeight() {}

    /** Recursive (post-order): 1 + max(left, right). O(n) time, O(h) stack. */
    public static int maxDepthRecursive(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepthRecursive(root.left), maxDepthRecursive(root.right));
    }

    /** Iterative BFS: count levels. O(n) time, O(width) space. */
    public static int maxDepthBfs(TreeNode root) {
        if (root == null) return 0;
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        int depth = 0;
        while (!q.isEmpty()) {
            depth++;
            for (int size = q.size(); size > 0; size--) {    // process exactly one level
                TreeNode n = q.poll();
                if (n.left != null) q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
        }
        return depth;
    }

    /** Minimum depth to a LEAF. Pitfall: a node with one child is not a leaf, so don't take min with the null side. */
    public static int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null) return 1 + minDepth(root.right);
        if (root.right == null) return 1 + minDepth(root.left);
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }

    /** Balanced: every node's subtree heights differ by ≤ 1. One pass returning -1 for "unbalanced". O(n). */
    public static boolean isBalanced(TreeNode root) {
        return checkedHeight(root) != -1;
    }

    private static int checkedHeight(TreeNode n) {
        if (n == null) return 0;
        int l = checkedHeight(n.left);
        if (l == -1) return -1;
        int r = checkedHeight(n.right);
        if (r == -1 || Math.abs(l - r) > 1) return -1;
        return 1 + Math.max(l, r);
    }
}
