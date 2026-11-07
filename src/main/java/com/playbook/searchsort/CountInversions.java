package com.playbook.searchsort;

/** Number of pairs i < j with a[i] > a[j] — "how far from sorted" an array is. [8,4,2,1] → 6. */
public final class CountInversions {
    private CountInversions() {}

    /** Brute force: check every pair. O(n^2). */
    public static long bruteForce(int[] a) {
        long count = 0;
        for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) if (a[i] > a[j]) count++;
        return count;
    }

    /**
     * Merge sort, counting while merging: when a right-half element is taken before left[i..mid],
     * it is smaller than ALL remaining left elements → it forms (mid - i + 1) inversions at once. O(n log n).
     * Works on a copy so the input is untouched. Result is long: up to n(n-1)/2.
     */
    public static long mergeSort(int[] a) {
        int[] c = a.clone();
        return sortCount(c, new int[c.length], 0, c.length - 1);
    }

    private static long sortCount(int[] a, int[] tmp, int lo, int hi) {
        if (lo >= hi) return 0;
        int mid = (lo + hi) >>> 1;
        long count = sortCount(a, tmp, lo, mid) + sortCount(a, tmp, mid + 1, hi);
        System.arraycopy(a, lo, tmp, lo, hi - lo + 1);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = tmp[j++];
            else if (j > hi) a[k] = tmp[i++];
            else if (tmp[j] < tmp[i]) {
                count += mid - i + 1;                        // tmp[j] beats every remaining left element
                a[k] = tmp[j++];
            } else a[k] = tmp[i++];
        }
        return count;
    }
}
