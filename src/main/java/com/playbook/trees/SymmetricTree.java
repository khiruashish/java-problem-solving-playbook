package com.playbook.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** Is the tree a mirror image of itself around its centre? */
public final class SymmetricTree {
    private SymmetricTree() {}

    /** Recursive: two subtrees mirror each other iff roots match, a.left mirrors b.right and a.right mirrors b.left. */
    public static boolean recursive(TreeNode root) {
        return root == null || mirror(root.left, root.right);
    }

    private static boolean mirror(TreeNode a, TreeNode b) {
        if (a == null || b == null) return a == b;                 // both null → true; exactly one → false
        return a.val == b.val && mirror(a.left, b.right) && mirror(a.right, b.left);
    }

    /** Iterative: a queue of PAIRS that must mirror each other; enqueue children in crossed order. */
    public static boolean iterative(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> q = new LinkedList<>();                    // LinkedList allows null elements
        q.add(root.left);
        q.add(root.right);
        while (!q.isEmpty()) {
            TreeNode a = q.poll(), b = q.poll();
            if (a == null && b == null) continue;
            if (a == null || b == null || a.val != b.val) return false;
            q.add(a.left);  q.add(b.right);                        // outer pair
            q.add(a.right); q.add(b.left);                         // inner pair
        }
        return true;
    }

    /** Level by level: every level (with nulls as gaps) must read the same forwards and backwards. O(n) space. */
    public static boolean levelPalindromes(TreeNode root) {
        if (root == null) return true;
        List<TreeNode> level = new ArrayList<>();
        level.add(root);
        while (!level.isEmpty()) {
            for (int i = 0, j = level.size() - 1; i < j; i++, j--) {
                TreeNode a = level.get(i), b = level.get(j);
                if ((a == null) != (b == null) || (a != null && a.val != b.val)) return false;
            }
            List<TreeNode> next = new ArrayList<>();
            for (TreeNode n : level) if (n != null) { next.add(n.left); next.add(n.right); }
            if (next.stream().allMatch(x -> x == null)) break;
            level = next;
        }
        return true;
    }

    /** Brute force for tests: build the mirror copy and compare structure + values. */
    public static boolean viaMirrorCopy(TreeNode root) {
        return same(root, mirrorCopy(root));
    }

    static TreeNode mirrorCopy(TreeNode n) {
        return n == null ? null : new TreeNode(n.val, mirrorCopy(n.right), mirrorCopy(n.left));
    }

    private static boolean same(TreeNode a, TreeNode b) {
        Deque<TreeNode[]> st = new ArrayDeque<>();
        st.push(new TreeNode[]{a, b});
        while (!st.isEmpty()) {
            TreeNode[] p = st.pop();
            if (p[0] == null && p[1] == null) continue;
            if (p[0] == null || p[1] == null || p[0].val != p[1].val) return false;
            st.push(new TreeNode[]{p[0].left, p[1].left});
            st.push(new TreeNode[]{p[0].right, p[1].right});
        }
        return true;
    }
}
