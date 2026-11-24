package com.playbook.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Vertical order traversal. Root at column 0; left child col−1, right child col+1.
 * BFS variant (LeetCode 314 / GfG): columns left→right; inside a column top→bottom, and left→right within a row.
 * Sorted variant (LeetCode 987): inside a column by row, then by VALUE when two nodes share the same (row, col).
 */
public final class VerticalOrder {
    private VerticalOrder() {}

    private record Cell(TreeNode node, int col) {}

    /** BFS: the queue already yields top→bottom, left→right; a TreeMap keeps columns sorted. O(n log n). */
    public static List<List<Integer>> bfs(TreeNode root) {
        if (root == null) return new ArrayList<>();
        Map<Integer, List<Integer>> cols = new TreeMap<>();
        Queue<Cell> q = new ArrayDeque<>();
        q.add(new Cell(root, 0));
        while (!q.isEmpty()) {
            Cell e = q.poll();
            cols.computeIfAbsent(e.col(), k -> new ArrayList<>()).add(e.node().val);
            if (e.node().left != null) q.add(new Cell(e.node().left, e.col() - 1));
            if (e.node().right != null) q.add(new Cell(e.node().right, e.col() + 1));
        }
        return new ArrayList<>(cols.values());
    }

    /**
     * DFS for the BFS variant. Plain DFS is WRONG on its own (it reaches a deep left node before a shallow right one),
     * but recording each node's row and sorting by (row, pre-order sequence) fixes it: pre-order visits nodes of the
     * same row from left to right.
     */
    public static List<List<Integer>> dfsWithRowSort(TreeNode root) {
        return collect(root, Comparator.<int[]>comparingInt(x -> x[0]).thenComparingInt(x -> x[1]));
    }

    /** Sorted variant (LeetCode 987): ties on (row, col) are broken by value. */
    public static List<List<Integer>> sortedTies(TreeNode root) {
        return collect(root, Comparator.<int[]>comparingInt(x -> x[0]).thenComparingInt(x -> x[2]));
    }

    /** DFS collecting [row, preorderSeq, val] per column, then sorting each column with the given order. */
    private static List<List<Integer>> collect(TreeNode root, Comparator<int[]> order) {
        Map<Integer, List<int[]>> cols = new TreeMap<>();
        dfs(root, 0, 0, cols, new int[]{0});
        List<List<Integer>> out = new ArrayList<>();
        for (List<int[]> col : cols.values()) {
            col.sort(order);
            List<Integer> vals = new ArrayList<>();
            for (int[] x : col) vals.add(x[2]);
            out.add(vals);
        }
        return out;
    }

    private static void dfs(TreeNode n, int row, int col, Map<Integer, List<int[]>> cols, int[] seq) {
        if (n == null) return;
        cols.computeIfAbsent(col, k -> new ArrayList<>()).add(new int[]{row, seq[0]++, n.val});
        dfs(n.left, row + 1, col - 1, cols, seq);
        dfs(n.right, row + 1, col + 1, cols, seq);
    }
}
