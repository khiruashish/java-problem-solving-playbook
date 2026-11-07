package com.playbook.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Activity selection: max number of non-overlapping intervals [start, end) (touching is fine).
 * Twin (LeetCode 435): minimum intervals to REMOVE so the rest don't overlap = n - max kept.
 */
public final class ActivitySelection {
    private ActivitySelection() {}

    /** Brute force: try every subset (2^n) — reference for small n. */
    public static int bruteForce(int[][] iv) {
        int n = iv.length, best = 0;
        for (int mask = 0; mask < (1 << n); mask++) {
            if (Integer.bitCount(mask) <= best) continue;
            boolean ok = true;
            for (int i = 0; i < n && ok; i++)
                for (int j = i + 1; j < n && ok; j++)
                    if ((mask >> i & 1) == 1 && (mask >> j & 1) == 1 && iv[i][0] < iv[j][1] && iv[j][0] < iv[i][1]) ok = false;
            if (ok) best = Integer.bitCount(mask);
        }
        return best;
    }

    /** DP O(n^2): sort by end; dp[i] = 1 + max dp[j] with end[j] ≤ start[i]. Correct but slower than greedy. */
    public static int dp(int[][] iv) {
        int[][] a = sortedByEnd(iv);
        int[] dp = new int[a.length];
        int best = 0;
        for (int i = 0; i < a.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) if (a[j][1] <= a[i][0]) dp[i] = Math.max(dp[i], dp[j] + 1);
            best = Math.max(best, dp[i]);
        }
        return best;
    }

    /**
     * Greedy: sort by END time, always take the activity that finishes first among those that fit.
     * Finishing earliest leaves the most room for the rest (exchange argument). O(n log n).
     */
    public static int greedy(int[][] iv) {
        int count = 0;
        long lastEnd = Long.MIN_VALUE;
        for (int[] x : sortedByEnd(iv)) {
            if (x[0] >= lastEnd) {
                count++;
                lastEnd = x[1];
            }
        }
        return count;
    }

    public static int minRemovals(int[][] iv) {
        return iv.length - greedy(iv);
    }

    private static int[][] sortedByEnd(int[][] iv) {
        int[][] a = iv.clone();
        Arrays.sort(a, Comparator.comparingInt(x -> x[1]));
        return a;
    }
}
