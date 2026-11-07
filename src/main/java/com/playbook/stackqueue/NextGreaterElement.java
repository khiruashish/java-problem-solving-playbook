package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/** For each element, the first element to its RIGHT that is strictly greater; -1 if none. */
public final class NextGreaterElement {
    private NextGreaterElement() {}

    /** Brute force: scan right for every index. O(n^2). */
    public static int[] bruteForce(int[] a) {
        int[] out = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = -1;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] > a[i]) {
                    out[i] = a[j];
                    break;
                }
            }
        }
        return out;
    }

    /**
     * Monotonic stack, right to left: keep a stack of candidates in decreasing order. Pop everything
     * ≤ a[i] (it can never be the answer for anything further left, because a[i] is closer and bigger).
     * Each index is pushed and popped once → O(n).
     */
    public static int[] monotonicStack(int[] a) {
        int[] out = new int[a.length];
        Deque<Integer> stack = new ArrayDeque<>();           // values, decreasing from bottom to top
        for (int i = a.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= a[i]) stack.pop();
            out[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(a[i]);
        }
        return out;
    }

    /** Left-to-right form: stack holds indexes still WAITING for their answer; a bigger value resolves them. */
    public static int[] leftToRight(int[] a) {
        int[] out = new int[a.length];
        Arrays.fill(out, -1);
        Deque<Integer> waiting = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            while (!waiting.isEmpty() && a[waiting.peek()] < a[i]) out[waiting.pop()] = a[i];
            waiting.push(i);
        }
        return out;
    }

    /** Circular array (LeetCode 503): walk the array twice, only push during the first lap. */
    public static int[] circular(int[] a) {
        int n = a.length;
        int[] out = new int[n];
        Arrays.fill(out, -1);
        Deque<Integer> waiting = new ArrayDeque<>();
        for (int i = 0; i < 2 * n; i++) {
            int v = a[i % n];
            while (!waiting.isEmpty() && a[waiting.peek()] < v) out[waiting.pop()] = v;
            if (i < n) waiting.push(i);
        }
        return out;
    }

    /** Mirror problem: previous smaller element (nearest strictly smaller to the LEFT). */
    public static int[] previousSmaller(int[] a) {
        int[] out = new int[a.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            while (!stack.isEmpty() && stack.peek() >= a[i]) stack.pop();
            out[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(a[i]);
        }
        return out;
    }
}
