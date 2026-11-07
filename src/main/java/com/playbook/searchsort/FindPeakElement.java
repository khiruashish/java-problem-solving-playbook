package com.playbook.searchsort;

/**
 * A peak is strictly greater than its neighbours (a[-1] = a[n] = -∞). Neighbours are never equal.
 * Return the index of ANY peak. [1,2,1,3,5,6,4] → 1 or 5.
 */
public final class FindPeakElement {
    private FindPeakElement() {}

    /** Linear: the first i with a[i] > a[i+1] is a peak (everything before it was rising). O(n). */
    public static int linear(int[] a) {
        for (int i = 0; i < a.length - 1; i++) if (a[i] > a[i + 1]) return i;
        return a.length - 1;
    }

    /**
     * Binary search on the slope: if a[mid] < a[mid+1] we are on an upward slope and a peak MUST exist to the right
     * (the array eventually drops to -∞). Otherwise a peak exists at mid or to its left. O(log n).
     */
    public static int binary(int[] a) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] < a[mid + 1]) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    /** Recursive version of the same idea. */
    public static int recursive(int[] a) {
        return recursive(a, 0, a.length - 1);
    }

    private static int recursive(int[] a, int lo, int hi) {
        if (lo == hi) return lo;
        int mid = (lo + hi) >>> 1;
        return a[mid] < a[mid + 1] ? recursive(a, mid + 1, hi) : recursive(a, lo, mid);
    }

    public static boolean isPeak(int[] a, int i) {
        boolean left = i == 0 || a[i] > a[i - 1], right = i == a.length - 1 || a[i] > a[i + 1];
        return left && right;
    }
}
