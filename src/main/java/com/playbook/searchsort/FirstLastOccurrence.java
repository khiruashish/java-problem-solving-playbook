package com.playbook.searchsort;

/** First and last index of target in a sorted array with duplicates; {-1, -1} if absent. [5,7,7,8,8,10], 8 → {3, 4}. */
public final class FirstLastOccurrence {
    private FirstLastOccurrence() {}

    /** Linear scan. O(n). */
    public static int[] linear(int[] a, int target) {
        int first = -1, last = -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == target) {
                if (first == -1) first = i;
                last = i;
            }
        }
        return new int[]{first, last};
    }

    /** Two binary searches: lower bound and upper bound. O(log n). */
    public static int[] binary(int[] a, int target) {
        int first = lowerBound(a, target);
        if (first == a.length || a[first] != target) return new int[]{-1, -1};
        return new int[]{first, upperBound(a, target) - 1};
    }

    /** First index with a[i] >= target (a.length if none). Half-open [lo, hi) template. */
    public static int lowerBound(int[] a, int target) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] < target) lo = mid + 1;               // mid is too small: answer is to the right
            else hi = mid;                                   // mid might be the answer: keep it
        }
        return lo;
    }

    /** First index with a[i] > target (a.length if none). */
    public static int upperBound(int[] a, int target) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] <= target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    /** Count of target = upperBound - lowerBound. O(log n). */
    public static int count(int[] a, int target) {
        return upperBound(a, target) - lowerBound(a, target);
    }
}
