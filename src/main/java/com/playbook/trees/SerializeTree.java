package com.playbook.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/** Encode a tree to a string and decode it back to an identical tree. Works for duplicates and negatives. */
public final class SerializeTree {
    private SerializeTree() {}

    private static final String NULL = "#", SEP = ",";

    /** Pre-order with null markers: "1,2,#,#,3,4,#,#,5,#,#". Markers make the shape unambiguous. O(n). */
    public static String serializePreorder(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        pre(root, sb);
        return sb.substring(0, sb.length() - 1);            // drop trailing separator
    }

    private static void pre(TreeNode n, StringBuilder sb) {
        if (n == null) {
            sb.append(NULL).append(SEP);
            return;
        }
        sb.append(n.val).append(SEP);
        pre(n.left, sb);
        pre(n.right, sb);
    }

    /** Recursive decode: read a token; "#" → null, else build node and recursively read left then right. */
    public static TreeNode deserializePreorder(String data) {
        return build(Arrays.asList(data.split(SEP)).iterator());
    }

    private static TreeNode build(Iterator<String> tokens) {
        String t = tokens.next();
        if (t.equals(NULL)) return null;
        TreeNode n = new TreeNode(Integer.parseInt(t));
        n.left = build(tokens);
        n.right = build(tokens);
        return n;
    }

    /**
     * BFS level order with markers (LeetCode's format), trailing markers trimmed. ArrayDeque rejects null, so the
     * queue is an ArrayList walked by index — it holds null children naturally. O(n).
     */
    public static String serializeLevelOrder(TreeNode root) {
        if (root == null) return "";
        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < queue.size(); i++) {
            TreeNode n = queue.get(i);
            if (n == null) {
                tokens.add(NULL);
                continue;
            }
            tokens.add(String.valueOf(n.val));
            queue.add(n.left);
            queue.add(n.right);
        }
        while (tokens.get(tokens.size() - 1).equals(NULL)) tokens.remove(tokens.size() - 1);
        return String.join(SEP, tokens);
    }

    public static TreeNode deserializeLevelOrder(String data) {
        if (data.isEmpty()) return null;
        String[] t = data.split(SEP);
        TreeNode root = new TreeNode(Integer.parseInt(t[0]));
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        int i = 1;
        while (!q.isEmpty() && i < t.length) {
            TreeNode n = q.poll();
            if (!t[i].equals(NULL)) q.offer(n.left = new TreeNode(Integer.parseInt(t[i])));
            i++;
            if (i < t.length && !t[i].equals(NULL)) q.offer(n.right = new TreeNode(Integer.parseInt(t[i])));
            i++;
        }
        return root;
    }

    /** Structural equality (used by tests). */
    public static boolean sameTree(TreeNode a, TreeNode b) {
        if (a == null || b == null) return a == b;
        return a.val == b.val && sameTree(a.left, b.left) && sameTree(a.right, b.right);
    }
}
