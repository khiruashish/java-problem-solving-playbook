package com.playbook.searchsort;

/**
 * Split non-negative array into k contiguous non-empty parts minimising the largest part sum.
 * Same as GfG "Allocate minimum pages" / "Painter's partition". [7,2,5,10,8], k=2 → 18 ([7,2,5] | [10,8]).
 */
public final class SplitArrayLargestSum {
    private SplitArrayLargestSum() {}

    /** Brute force recursion: try every position for the first cut. Exponential. */
    public static long bruteForce(int[] a, int k) {
        return best(a, 0, k);
    }

    private static long best(int[] a, int start, int k) {
        if (k == 1) {
            long s = 0;
            for (int i = start; i < a.length; i++) s += a[i];
            return s;
        }
        long result = Long.MAX_VALUE, first = 0;
        for (int end = start; end <= a.length - k; end++) {  // leave at least one element per remaining part
            first += a[end];
            result = Math.min(result, Math.max(first, best(a, end + 1, k - 1)));
        }
        return result;
    }

    /**
     * Binary search on the ANSWER: for a candidate limit L, greedily count how many parts are needed
     * (start a new part when adding would exceed L). parts(L) is monotone in L, so binary search
     * the smallest L with parts(L) <= k. Range: [max element, total sum]. O(n log(sum)).
     */
    public static long binarySearchOnAnswer(int[] a, int k) {
        long lo = 0, hi = 0;
        for (int x : a) {
            lo = Math.max(lo, x);                            // a part must hold the biggest element
            hi += x;                                         // one part holding everything
        }
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (partsNeeded(a, mid) <= k) hi = mid;          // feasible: try smaller
            else lo = mid + 1;
        }
        return lo;
    }

    static int partsNeeded(int[] a, long limit) {
        int parts = 1;
        long sum = 0;
        for (int x : a) {
            if (sum + x > limit) {
                parts++;
                sum = 0;
            }
            sum += x;
        }
        return parts;
    }
}
