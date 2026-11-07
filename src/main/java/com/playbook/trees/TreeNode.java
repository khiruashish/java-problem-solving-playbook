package com.playbook.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/** Binary tree node + helpers to build/print trees in LeetCode level-order form, e.g. [3,9,20,null,null,15,7]. */
public final class TreeNode {
    public int val;
    public TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /** Build from level order; null marks a missing child. Empty / leading null → null tree. */
    public static TreeNode of(Integer... levelOrder) {
        if (levelOrder.length == 0 || levelOrder[0] == null) return null;
        TreeNode root = new TreeNode(levelOrder[0]);
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int i = 1;
        while (!queue.isEmpty() && i < levelOrder.length) {
            TreeNode node = queue.poll();
            if (i < levelOrder.length && levelOrder[i] != null) queue.offer(node.left = new TreeNode(levelOrder[i]));
            i++;
            if (i < levelOrder.length && levelOrder[i] != null) queue.offer(node.right = new TreeNode(levelOrder[i]));
            i++;
        }
        return root;
    }

    /** Level order with nulls, trailing nulls trimmed — the inverse of {@link #of}. */
    public static List<Integer> toLevelOrder(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        for (int i = 0; i < queue.size(); i++) {
            TreeNode n = queue.get(i);
            out.add(n == null ? null : n.val);
            if (n != null) {
                queue.add(n.left);
                queue.add(n.right);
            }
        }
        while (!out.isEmpty() && out.get(out.size() - 1) == null) out.remove(out.size() - 1);
        return out;
    }

    @Override
    public String toString() {
        return toLevelOrder(this).toString();
    }
}
