package com.playbook.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

/** Iterate a BST in ascending order with next() / hasNext(). */
public final class BstIterator {
    private BstIterator() {}

    public interface IntIterator {
        boolean hasNext();
        int next();
    }

    /** Flatten first: in-order traversal into a list. next/hasNext O(1), but O(n) memory and O(n) up-front work. */
    public static final class Flattened implements IntIterator {
        private final List<Integer> values = new ArrayList<>();
        private int i;

        public Flattened(TreeNode root) {
            inorder(root);
        }

        private void inorder(TreeNode n) {
            if (n == null) return;
            inorder(n.left);
            values.add(n.val);
            inorder(n.right);
        }

        @Override public boolean hasNext() { return i < values.size(); }

        @Override public int next() {
            if (!hasNext()) throw new NoSuchElementException();
            return values.get(i++);
        }
    }

    /**
     * Controlled recursion: a stack holding the "left spine" still to visit. next() pops the smallest node, then pushes
     * the left spine of its right subtree. Every node is pushed and popped once → amortised O(1) per next(),
     * and the stack never exceeds the tree height: O(h) memory.
     */
    public static final class Stack implements IntIterator {
        private final Deque<TreeNode> stack = new ArrayDeque<>();

        public Stack(TreeNode root) {
            pushLeft(root);
        }

        private void pushLeft(TreeNode n) {
            for (; n != null; n = n.left) stack.push(n);
        }

        @Override public boolean hasNext() { return !stack.isEmpty(); }

        @Override public int next() {
            if (stack.isEmpty()) throw new NoSuchElementException();
            TreeNode n = stack.pop();                    // smallest unvisited value
            pushLeft(n.right);                           // its successor is the leftmost node of the right subtree
            return n.val;
        }
    }

    /**
     * Morris-style iterator: O(1) extra memory by temporarily threading each in-order predecessor's right pointer
     * back to its successor. The tree is restored by the time iteration finishes (it is modified meanwhile).
     */
    public static final class Morris implements IntIterator {
        private TreeNode cur;

        public Morris(TreeNode root) {
            cur = root;
        }

        @Override public boolean hasNext() { return cur != null; }

        @Override public int next() {
            while (cur != null) {
                if (cur.left == null) {
                    int v = cur.val;
                    cur = cur.right;                      // may follow a thread back up
                    return v;
                }
                TreeNode pred = cur.left;
                while (pred.right != null && pred.right != cur) pred = pred.right;
                if (pred.right == null) {                 // first visit: thread and go left
                    pred.right = cur;
                    cur = cur.left;
                } else {                                  // second visit: unthread, emit, go right
                    pred.right = null;
                    int v = cur.val;
                    cur = cur.right;
                    return v;
                }
            }
            throw new NoSuchElementException();
        }
    }

    /** Helper: drain any iterator into a list. */
    public static List<Integer> drain(IntIterator it) {
        List<Integer> out = new ArrayList<>();
        while (it.hasNext()) out.add(it.next());
        return out;
    }
}
