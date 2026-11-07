package com.playbook.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/** Level-order traversal grouped by level: [[3],[9,20],[15,7]]. Plus zig-zag order. */
public final class LevelOrder {
    private LevelOrder() {}

    /** BFS with a queue; snapshot the queue size to know where a level ends. O(n). */
    public static List<List<Integer>> bfs(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        if (root == null) return out;
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (int size = q.size(); size > 0; size--) {
                TreeNode n = q.poll();
                level.add(n.val);
                if (n.left != null) q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
            out.add(level);
        }
        return out;
    }

    /** DFS with depth: pre-order puts each value into the list for its depth. Same result, O(h) stack. */
    public static List<List<Integer>> dfs(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        dfs(root, 0, out);
        return out;
    }

    private static void dfs(TreeNode n, int depth, List<List<Integer>> out) {
        if (n == null) return;
        if (depth == out.size()) out.add(new ArrayList<>());   // first time we reach this depth
        out.get(depth).add(n.val);
        dfs(n.left, depth + 1, out);
        dfs(n.right, depth + 1, out);
    }

    /** Zig-zag: alternate direction per level; add to the front of the level list on odd levels. O(n). */
    public static List<List<Integer>> zigzag(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        if (root == null) return out;
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        boolean leftToRight = true;
        while (!q.isEmpty()) {
            LinkedList<Integer> level = new LinkedList<>();
            for (int size = q.size(); size > 0; size--) {
                TreeNode n = q.poll();
                if (leftToRight) level.addLast(n.val);
                else level.addFirst(n.val);                  // reverse without reversing the traversal
                if (n.left != null) q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
            out.add(level);
            leftToRight = !leftToRight;
        }
        return out;
    }

    /** Zig-zag with two stacks (the classic GfG approach): children pushed in alternating order. */
    public static List<List<Integer>> zigzagTwoStacks(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        if (root == null) return out;
        Deque<TreeNode> current = new ArrayDeque<>(), next = new ArrayDeque<>();
        current.push(root);
        boolean leftToRight = true;
        List<Integer> level = new ArrayList<>();
        while (!current.isEmpty()) {
            TreeNode n = current.pop();
            level.add(n.val);
            TreeNode first = leftToRight ? n.left : n.right, second = leftToRight ? n.right : n.left;
            if (first != null) next.push(first);
            if (second != null) next.push(second);
            if (current.isEmpty()) {
                out.add(level);
                level = new ArrayList<>();
                Deque<TreeNode> t = current;
                current = next;
                next = t;
                leftToRight = !leftToRight;
            }
        }
        return out;
    }
}
