package com.playbook.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Minimum number of intervals to REMOVE so the rest don't overlap. Touching ends ([1,2] and [2,3]) do NOT overlap.
 * Equivalent: n − (maximum number of non-overlapping intervals) — the activity-selection problem.
 */
public final class NonOverlappingIntervals {
    private NonOverlappingIntervals() {}

    /** Brute force: try every subset, keep the largest non-overlapping one. O(2ⁿ · n log n). n ≤ 15. */
    public static int bruteForce(int[][] intervals) {
        int n = intervals.length, best = 0;
        for (int mask = 0; mask < (1 << n); mask++) {
            int[][] chosen = new int[Integer.bitCount(mask)][];
            for (int i = 0, k = 0; i < n; i++) if ((mask & (1 << i)) != 0) chosen[k++] = intervals[i];
            Arrays.sort(chosen, Comparator.comparingInt(a -> a[0]));
            boolean ok = true;
            for (int i = 1; i < chosen.length && ok; i++) ok = chosen[i][0] >= chosen[i - 1][1];
            if (ok) best = Math.max(best, chosen.length);
        }
        return n - best;
    }

    /**
     * Greedy by END time: always keep the interval that finishes first — it leaves the most room for the rest
     * (exchange argument). Count conflicts as removals. O(n log n).
     */
    public static int greedyByEnd(int[][] intervals) {
        if (intervals.length == 0) return 0;
        int[][] a = intervals.clone();
        Arrays.sort(a, Comparator.comparingInt(x -> x[1]));
        int removed = 0, lastEnd = a[0][1];
        for (int i = 1; i < a.length; i++) {
            if (a[i][0] >= lastEnd) lastEnd = a[i][1];     // compatible: keep it
            else removed++;                                 // overlaps the kept one: remove it
        }
        return removed;
    }

    /**
     * Greedy by START time: on an overlap, keep whichever of the two ends EARLIER (drop the longer-reaching one).
     * Same answer, sometimes more natural when the input is already sorted by start. O(n log n).
     */
    public static int greedyByStart(int[][] intervals) {
        if (intervals.length == 0) return 0;
        int[][] a = intervals.clone();
        Arrays.sort(a, Comparator.comparingInt(x -> x[0]));
        int removed = 0, prevEnd = a[0][1];
        for (int i = 1; i < a.length; i++) {
            if (a[i][0] >= prevEnd) prevEnd = a[i][1];
            else {
                removed++;
                prevEnd = Math.min(prevEnd, a[i][1]);       // keep the one that frees up sooner
            }
        }
        return removed;
    }

    /** DP (like LIS) for comparison: sort by start, dp[i] = max kept ending with i. O(n²). */
    public static int dp(int[][] intervals) {
        int n = intervals.length;
        if (n == 0) return 0;
        int[][] a = intervals.clone();
        Arrays.sort(a, Comparator.comparingInt(x -> x[0]));
        int[] keep = new int[n];
        int best = 0;
        for (int i = 0; i < n; i++) {
            keep[i] = 1;
            for (int j = 0; j < i; j++) if (a[j][1] <= a[i][0]) keep[i] = Math.max(keep[i], keep[j] + 1);
            best = Math.max(best, keep[i]);
        }
        return n - best;
    }
}
