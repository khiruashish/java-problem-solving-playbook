package com.playbook.searchsort;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

/** k-th smallest element (1-based) of an unsorted array. [7,10,4,3,20,15], k=3 → 7. */
public final class KthSmallest {
    private KthSmallest() {}

    /** Sort a copy. O(n log n). */
    public static int sorting(int[] a, int k) {
        int[] c = a.clone();
        Arrays.sort(c);
        return c[k - 1];
    }

    /** Max-heap of size k: its root is the k-th smallest seen so far. O(n log k), O(k). Works on streams. */
    public static int maxHeap(int[] a, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int x : a) {
            pq.offer(x);
            if (pq.size() > k) pq.poll();                    // drop the largest: it can't be among the k smallest
        }
        return pq.peek();
    }

    /**
     * Quickselect (Hoare's selection): partition like quicksort, then recurse into ONE side only.
     * n + n/2 + n/4 + … = O(n) expected with a random pivot; O(n^2) worst case. Works on a copy.
     */
    public static int quickselect(int[] a, int k) {
        int[] c = a.clone();
        int lo = 0, hi = c.length - 1, target = k - 1;
        while (true) {
            int p = partition(c, lo, hi);
            if (p == target) return c[p];
            if (p < target) lo = p + 1;
            else hi = p - 1;
        }
    }

    private static int partition(int[] a, int lo, int hi) {
        MergeQuickSort.swap(a, lo + ThreadLocalRandom.current().nextInt(hi - lo + 1), hi);
        int pivot = a[hi], i = lo;
        for (int j = lo; j < hi; j++) if (a[j] < pivot) MergeQuickSort.swap(a, i++, j);
        MergeQuickSort.swap(a, i, hi);
        return i;
    }

    /** Kth LARGEST is the (n - k + 1)-th smallest. */
    public static int kthLargest(int[] a, int k) {
        return quickselect(a, a.length - k + 1);
    }
}
