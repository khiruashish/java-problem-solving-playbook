package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;

/** Largest rectangle in a histogram of bar heights (width 1 each). [2,1,5,6,2,3] → 10 (bars 5,6). */
public final class LargestRectangleHistogram {
    private LargestRectangleHistogram() {}

    /** Brute force: for every bar as the shortest, expand left and right. O(n^2). */
    public static int bruteForce(int[] h) {
        int best = 0;
        for (int i = 0; i < h.length; i++) {
            int l = i, r = i;
            while (l > 0 && h[l - 1] >= h[i]) l--;
            while (r < h.length - 1 && h[r + 1] >= h[i]) r++;
            best = Math.max(best, h[i] * (r - l + 1));
        }
        return best;
    }

    /** Two passes of "previous/next smaller index" (monotonic stacks), then one pass for areas. O(n). */
    public static int nearestSmaller(int[] h) {
        int n = h.length;
        int[] left = new int[n], right = new int[n];         // first smaller index on each side (exclusive)
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && h[st.peek()] >= h[i]) st.pop();
            left[i] = st.isEmpty() ? -1 : st.peek();
            st.push(i);
        }
        st.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && h[st.peek()] >= h[i]) st.pop();
            right[i] = st.isEmpty() ? n : st.peek();
            st.push(i);
        }
        int best = 0;
        for (int i = 0; i < n; i++) best = Math.max(best, h[i] * (right[i] - left[i] - 1));
        return best;
    }

    /**
     * Optimal one pass: stack of indexes with increasing heights. When a lower bar arrives, every taller bar
     * on the stack has found its right boundary (i) and its left boundary is the element below it.
     * A sentinel height 0 at i == n flushes the stack. O(n).
     */
    public static int onePass(int[] h) {
        Deque<Integer> st = new ArrayDeque<>();
        int best = 0;
        for (int i = 0; i <= h.length; i++) {
            int cur = i == h.length ? 0 : h[i];
            while (!st.isEmpty() && h[st.peek()] > cur) {
                int height = h[st.pop()];
                int leftBoundary = st.isEmpty() ? -1 : st.peek();
                best = Math.max(best, height * (i - leftBoundary - 1));
            }
            st.push(i);
        }
        return best;
    }

    /** Follow-up: maximal rectangle of 1s in a binary matrix — each row is a histogram of column heights. */
    public static int maximalRectangle(char[][] grid) {
        if (grid.length == 0) return 0;
        int[] heights = new int[grid[0].length];
        int best = 0;
        for (char[] row : grid) {
            for (int c = 0; c < row.length; c++) heights[c] = row[c] == '1' ? heights[c] + 1 : 0;
            best = Math.max(best, onePass(heights));
        }
        return best;
    }
}
