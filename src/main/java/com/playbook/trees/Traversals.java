package com.playbook.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/** Pre-, in- and post-order, each recursively and iteratively (explicit stack), plus Morris in-order in O(1) space. */
public final class Traversals {
    private Traversals() {}

    // ---------- recursive: the order of the three lines IS the traversal ----------
    public static List<Integer> preorderRecursive(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        pre(root, out);
        return out;
    }

    private static void pre(TreeNode n, List<Integer> out) {
        if (n == null) return;
        out.add(n.val);                                      // root
        pre(n.left, out);                                    // left
        pre(n.right, out);                                   // right
    }

    public static List<Integer> inorderRecursive(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        in(root, out);
        return out;
    }

    private static void in(TreeNode n, List<Integer> out) {
        if (n == null) return;
        in(n.left, out);
        out.add(n.val);
        in(n.right, out);
    }

    public static List<Integer> postorderRecursive(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        post(root, out);
        return out;
    }

    private static void post(TreeNode n, List<Integer> out) {
        if (n == null) return;
        post(n.left, out);
        post(n.right, out);
        out.add(n.val);
    }

    // ---------- iterative ----------
    /** Pre-order: pop, visit, push RIGHT then LEFT (so left is processed first). */
    public static List<Integer> preorderIterative(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode n = stack.pop();
            out.add(n.val);
            if (n.right != null) stack.push(n.right);
            if (n.left != null) stack.push(n.left);
        }
        return out;
    }

    /** In-order: go left as far as possible pushing nodes; pop, visit, then turn right. */
    public static List<Integer> inorderIterative(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            out.add(curr.val);
            curr = curr.right;
        }
        return out;
    }

    /** Post-order trick: root-RIGHT-left pre-order, reversed, is left-right-root. */
    public static List<Integer> postorderIterative(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode n = stack.pop();
            out.add(n.val);
            if (n.left != null) stack.push(n.left);
            if (n.right != null) stack.push(n.right);
        }
        Collections.reverse(out);
        return out;
    }

    /**
     * Morris in-order: O(1) extra space. Temporarily thread each node's in-order predecessor's right pointer back to it,
     * so we can return without a stack; the thread is removed on the second visit, restoring the tree.
     */
    public static List<Integer> inorderMorris(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left == null) {
                out.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode pred = curr.left;
                while (pred.right != null && pred.right != curr) pred = pred.right;
                if (pred.right == null) {                    // first visit: create the thread, go left
                    pred.right = curr;
                    curr = curr.left;
                } else {                                     // second visit: remove the thread, visit, go right
                    pred.right = null;
                    out.add(curr.val);
                    curr = curr.right;
                }
            }
        }
        return out;
    }
}
