package com.playbook.trees;

/** Maximum sum of any path (any node to any node, at least one node, no node repeated). Values may be negative. */
public final class MaxPathSum {
    private MaxPathSum() {}

    /** Brute force: treat every node as the "bend" and compute the best downward chains from scratch. O(n^2). */
    public static int bruteForce(TreeNode root) {
        if (root == null) throw new IllegalArgumentException("empty tree");
        int here = root.val + Math.max(0, bestDown(root.left)) + Math.max(0, bestDown(root.right));
        int best = here;
        if (root.left != null) best = Math.max(best, bruteForce(root.left));
        if (root.right != null) best = Math.max(best, bruteForce(root.right));
        return best;
    }

    private static int bestDown(TreeNode n) {
        if (n == null) return 0;
        return n.val + Math.max(0, Math.max(bestDown(n.left), bestDown(n.right)));
    }

    /**
     * Optimal post-order: gain(n) = best downward chain starting at n = n.val + max(0, gain(left), gain(right)).
     * At each node also try the path that bends here: n.val + max(0, gainL) + max(0, gainR). O(n).
     * Negative gains are dropped (max with 0): a chain only helps if it adds something.
     */
    public static int optimal(TreeNode root) {
        if (root == null) throw new IllegalArgumentException("empty tree");
        int[] best = {Integer.MIN_VALUE};                    // MIN, not 0: an all-negative tree must return its max node
        gain(root, best);
        return best[0];
    }

    private static int gain(TreeNode n, int[] best) {
        if (n == null) return 0;
        int l = Math.max(0, gain(n.left, best)), r = Math.max(0, gain(n.right, best));
        best[0] = Math.max(best[0], n.val + l + r);          // path bending at n (can't be extended upward)
        return n.val + Math.max(l, r);                       // parent may extend only ONE side
    }
}
