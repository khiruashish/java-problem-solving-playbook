package com.playbook.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Kth largest element in a STREAM (LeetCode 703): constructor gets k and initial numbers, add(x) returns the current
 * kth largest. Plus the one-shot array version.
 */
public final class KthLargest {
    private final int k;
    private final PriorityQueue<Integer> minHeap = new PriorityQueue<>();   // holds the k largest seen so far

    public KthLargest(int k, int[] nums) {
        if (k <= 0) throw new IllegalArgumentException("k must be > 0");
        this.k = k;
        for (int x : nums) {                                 // don't call add(): it throws while size < k
            minHeap.offer(x);
            if (minHeap.size() > k) minHeap.poll();
        }
    }

    /** O(log k): push, and if we exceed k evict the smallest — the root is then exactly the kth largest. */
    public int add(int x) {
        minHeap.offer(x);
        if (minHeap.size() > k) minHeap.poll();
        if (minHeap.size() < k) throw new IllegalStateException("fewer than k elements so far");
        return minHeap.peek();
    }

    /** Brute force for one array: sort descending, pick index k-1. O(n log n). */
    public static int sorting(int[] a, int k) {
        int[] c = a.clone();
        Arrays.sort(c);
        return c[c.length - k];
    }

    /** Min-heap of size k. O(n log k) time, O(k) space. */
    public static int minHeap(int[] a, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int x : a) {
            pq.offer(x);
            if (pq.size() > k) pq.poll();
        }
        return pq.peek();
    }
}
