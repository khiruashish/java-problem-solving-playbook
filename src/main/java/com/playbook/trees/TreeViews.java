package com.playbook.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** What you see from the left, right, top and bottom of a binary tree. */
public final class TreeViews {
    private TreeViews() {}

    /** Left view: first node of every level (BFS). */
    public static List<Integer> left(TreeNode root) {
        return levelEdge(root, true);
    }

    /** Right view: last node of every level (BFS). */
    public static List<Integer> right(TreeNode root) {
        return levelEdge(root, false);
    }

    private static List<Integer> levelEdge(TreeNode root, boolean first) {
        List<Integer> out = new ArrayList<>();
        if (root == null) return out;
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode n = q.poll();
                if (first ? i == 0 : i == size - 1) out.add(n.val);
                if (n.left != null) q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
        }
        return out;
    }

    /** Right view, recursive: visit right child first; the first node seen at each depth is visible. */
    public static List<Integer> rightRecursive(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        rightDfs(root, 0, out);
        return out;
    }

    private static void rightDfs(TreeNode n, int depth, List<Integer> out) {
        if (n == null) return;
        if (depth == out.size()) out.add(n.val);
        rightDfs(n.right, depth + 1, out);
        rightDfs(n.left, depth + 1, out);
    }

    /**
     * Top view: horizontal distance hd (root 0, left -1, right +1). BFS guarantees the FIRST node met at each hd is
     * the topmost (ties at the same depth go to the left one). TreeMap keeps hd sorted.
     */
    public static List<Integer> top(TreeNode root) {
        return byColumn(root, true);
    }

    /** Bottom view: the LAST node met at each hd in BFS (later = deeper; same depth → the right one, as GfG specifies). */
    public static List<Integer> bottom(TreeNode root) {
        return byColumn(root, false);
    }

    private static List<Integer> byColumn(TreeNode root, boolean keepFirst) {
        Map<Integer, Integer> column = new TreeMap<>();
        if (root == null) return new ArrayList<>();
        Deque<Object[]> q = new ArrayDeque<>();
        q.offer(new Object[]{root, 0});
        while (!q.isEmpty()) {
            Object[] e = q.poll();
            TreeNode n = (TreeNode) e[0];
            int hd = (int) e[1];
            if (keepFirst) column.putIfAbsent(hd, n.val);
            else column.put(hd, n.val);
            if (n.left != null) q.offer(new Object[]{n.left, hd - 1});
            if (n.right != null) q.offer(new Object[]{n.right, hd + 1});
        }
        return new ArrayList<>(column.values());
    }
}
