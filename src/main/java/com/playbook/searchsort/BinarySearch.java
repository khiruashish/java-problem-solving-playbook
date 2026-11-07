package com.playbook.searchsort;

/** Find the index of target in a SORTED array, or -1. */
public final class BinarySearch {
    private BinarySearch() {}

    /** Linear search: works on unsorted data too. O(n). */
    public static int linear(int[] a, int target) {
        for (int i = 0; i < a.length; i++) if (a[i] == target) return i;
        return -1;
    }

    /**
     * Iterative binary search on the closed range [lo, hi]. Each step discards half → O(log n), O(1).
     * Invariant: if target exists, it is inside [lo, hi].
     */
    public static int iterative(int[] a, int target) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {                                   // <= because [lo, hi] is closed: one element left still counts
            int mid = lo + (hi - lo) / 2;                    // never (lo + hi) / 2 — it can overflow
            if (a[mid] == target) return mid;
            if (a[mid] < target) lo = mid + 1;               // target is strictly right of mid
            else hi = mid - 1;
        }
        return -1;
    }

    /** Recursive form of the same idea. O(log n) time, O(log n) stack. */
    public static int recursive(int[] a, int target) {
        return recursive(a, target, 0, a.length - 1);
    }

    private static int recursive(int[] a, int target, int lo, int hi) {
        if (lo > hi) return -1;                              // base case: empty range
        int mid = (lo + hi) >>> 1;                           // unsigned shift: also overflow-safe
        if (a[mid] == target) return mid;
        return a[mid] < target ? recursive(a, target, mid + 1, hi) : recursive(a, target, lo, mid - 1);
    }

    /** Integer square root by binary search on the answer: largest r with r*r <= x. */
    public static int sqrt(int x) {
        if (x < 0) throw new IllegalArgumentException("negative");
        long lo = 0, hi = x;
        while (lo < hi) {
            long mid = (lo + hi + 1) / 2;                    // upper mid, because lo = mid below
            if (mid * mid <= x) lo = mid;
            else hi = mid - 1;
        }
        return (int) lo;
    }
}
