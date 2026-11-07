package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

/** Maximum of every window of size k. [1,3,-1,-3,5,3,6,7], k=3 → [3,3,5,5,6,7]. */
public final class SlidingWindowMaximum {
    private SlidingWindowMaximum() {}

    /** Brute force: max of each window. O(n·k). */
    public static int[] bruteForce(int[] a, int k) {
        if (a.length == 0 || k <= 0) return new int[0];
        int[] out = new int[a.length - k + 1];
        for (int i = 0; i + k <= a.length; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++) max = Math.max(max, a[j]);
            out[i] = max;
        }
        return out;
    }

    /** Max-heap of (value, index); lazily discard tops that slid out. O(n log n). */
    public static int[] heap(int[] a, int k) {
        if (a.length == 0 || k <= 0) return new int[0];
        PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> Integer.compare(y[0], x[0]));
        int[] out = new int[a.length - k + 1];
        for (int i = 0; i < a.length; i++) {
            pq.offer(new int[]{a[i], i});
            if (i >= k - 1) {
                while (pq.peek()[1] <= i - k) pq.poll();     // stale: index left the window
                out[i - k + 1] = pq.peek()[0];
            }
        }
        return out;
    }

    /**
     * Optimal: monotonic deque of indexes with DECREASING values. Front = current max.
     * Pop back while smaller than the new element (they can never be a max again), pop front if it slid out.
     * Each index enters and leaves once → O(n), O(k) space.
     */
    public static int[] monotonicDeque(int[] a, int k) {
        if (a.length == 0 || k <= 0) return new int[0];
        int[] out = new int[a.length - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            if (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();   // front fell out of the window
            while (!dq.isEmpty() && a[dq.peekLast()] <= a[i]) dq.pollLast(); // dominated by a[i]
            dq.offerLast(i);
            if (i >= k - 1) out[i - k + 1] = a[dq.peekFirst()];
        }
        return out;
    }
}
