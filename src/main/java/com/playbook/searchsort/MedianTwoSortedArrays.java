package com.playbook.searchsort;

/** Median of two sorted arrays (not both empty). [1,3] + [2] → 2.0; [1,2] + [3,4] → 2.5. Target O(log(min(m,n))). */
public final class MedianTwoSortedArrays {
    private MedianTwoSortedArrays() {}

    /** Merge until the middle (like merge sort's merge). O(m + n) time, O(1) space. */
    public static double merge(int[] a, int[] b) {
        int total = a.length + b.length, i = 0, j = 0, prev = 0, curr = 0;
        for (int step = 0; step <= total / 2; step++) {
            prev = curr;
            curr = (j >= b.length || (i < a.length && a[i] <= b[j])) ? a[i++] : b[j++];
        }
        return total % 2 == 1 ? curr : (prev + (double) curr) / 2;
    }

    /**
     * Partition binary search on the SHORTER array. Cut a at i and b at j = half - i so the left side holds
     * half the elements. The cut is correct when aLeft <= bRight and bLeft <= aRight; then the median comes from
     * the max of the left side and the min of the right side. O(log(min(m, n))).
     */
    public static double partition(int[] a, int[] b) {
        if (a.length > b.length) return partition(b, a);     // binary search the smaller array
        int m = a.length, n = b.length, half = (m + n + 1) / 2;
        int lo = 0, hi = m;
        while (lo <= hi) {
            int i = (lo + hi) >>> 1, j = half - i;
            int aLeft = i == 0 ? Integer.MIN_VALUE : a[i - 1];
            int aRight = i == m ? Integer.MAX_VALUE : a[i];
            int bLeft = j == 0 ? Integer.MIN_VALUE : b[j - 1];
            int bRight = j == n ? Integer.MAX_VALUE : b[j];
            if (aLeft <= bRight && bLeft <= aRight) {        // valid cut
                int leftMax = Math.max(aLeft, bLeft);
                if ((m + n) % 2 == 1) return leftMax;
                return (leftMax + (double) Math.min(aRight, bRight)) / 2;
            }
            if (aLeft > bRight) hi = i - 1;                  // took too many from a
            else lo = i + 1;                                 // took too few from a
        }
        throw new IllegalArgumentException("arrays must be sorted");
    }
}
