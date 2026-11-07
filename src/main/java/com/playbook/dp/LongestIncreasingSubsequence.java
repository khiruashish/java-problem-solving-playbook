package com.playbook.dp;

import java.util.Arrays;

/** Length of the longest STRICTLY increasing subsequence. [10,9,2,5,3,7,101,18] → 4 (2,3,7,18). */
public final class LongestIncreasingSubsequence {
    private LongestIncreasingSubsequence() {}

    /** Recursion: take or skip each element, remembering the last taken value. O(2^n). */
    public static int recursive(int[] a) {
        return rec(a, 0, -1);
    }

    private static int rec(int[] a, int i, int prevIdx) {
        if (i == a.length) return 0;
        int skip = rec(a, i + 1, prevIdx);
        int take = prevIdx == -1 || a[i] > a[prevIdx] ? 1 + rec(a, i + 1, i) : 0;
        return Math.max(skip, take);
    }

    /** DP O(n^2): dp[i] = LIS ending exactly at i = 1 + max(dp[j]) for j < i with a[j] < a[i]. */
    public static int quadratic(int[] a) {
        int[] dp = new int[a.length];
        int best = 0;
        for (int i = 0; i < a.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) if (a[j] < a[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
            best = Math.max(best, dp[i]);
        }
        return best;
    }

    /**
     * Patience sorting O(n log n): tails[k] = smallest possible tail of an increasing subsequence of length k+1.
     * tails is sorted, so binary-search where each element goes (replace the first tail ≥ x, or extend).
     */
    public static int patience(int[] a) {
        int[] tails = new int[a.length];
        int len = 0;
        for (int x : a) {
            int pos = Arrays.binarySearch(tails, 0, len, x);
            if (pos < 0) pos = -pos - 1;                     // insertion point = first tail >= x (strictly increasing)
            tails[pos] = x;
            if (pos == len) len++;
        }
        return len;
    }
}
