package com.playbook.trees;

/** Diameter = number of EDGES on the longest path between any two nodes (may not pass through the root). */
public final class Diameter {
    private Diameter() {}

    /** Brute force: at every node, leftHeight + rightHeight, recomputing heights each time. O(n^2) on a skewed tree. */
    public static int bruteForce(TreeNode root) {
        if (root == null) return 0;
        int through = TreeHeight.maxDepthRecursive(root.left) + TreeHeight.maxDepthRecursive(root.right);
        return Math.max(through, Math.max(bruteForce(root.left), bruteForce(root.right)));
    }

    /** Optimal: one post-order pass returns height AND updates the best "through this node" path. O(n). */
    public static int optimal(TreeNode root) {
        int[] best = {0};
        height(root, best);
        return best[0];
    }

    private static int height(TreeNode n, int[] best) {
        if (n == null) return 0;
        int l = height(n.left, best), r = height(n.right, best);
        best[0] = Math.max(best[0], l + r);                  // longest path that bends at n
        return 1 + Math.max(l, r);                           // what the parent can extend
    }
}
