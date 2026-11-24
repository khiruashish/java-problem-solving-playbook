package com.playbook.dp;

import java.util.Arrays;

/**
 * Egg drop: k eggs, a building with n floors. Some floor f (0..n) is the highest safe floor. Find the minimum number of
 * drops that GUARANTEES finding f in the worst case. A dropped egg that survives can be reused.
 */
public final class EggDrop {
    private EggDrop() {}

    /**
     * Classic DP over (eggs, floors): dp[e][f] = 1 + min over x in 1..f of max(dp[e−1][x−1] (breaks: below x),
     * dp[e][f−x] (survives: above x)). O(k · n²).
     */
    public static int classic(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        for (int f = 1; f <= n; f++) dp[1][f] = f;            // one egg: must go floor by floor
        for (int e = 2; e <= k; e++) {
            for (int f = 1; f <= n; f++) {
                int best = Integer.MAX_VALUE;
                for (int x = 1; x <= f; x++) {
                    int worst = 1 + Math.max(dp[e - 1][x - 1], dp[e][f - x]);
                    best = Math.min(best, worst);
                }
                dp[e][f] = best;
            }
        }
        return k == 0 ? (n == 0 ? 0 : Integer.MAX_VALUE) : dp[k][n];
    }

    /**
     * Same recurrence, but binary search the drop floor x: "breaks" cost grows with x, "survives" cost shrinks with x,
     * so the max is minimised where they cross. O(k · n log n).
     */
    public static int binarySearch(int k, int n) {
        int[][] memo = new int[k + 1][n + 1];
        for (int[] row : memo) Arrays.fill(row, -1);
        return solve(k, n, memo);
    }

    private static int solve(int e, int f, int[][] memo) {
        if (f == 0) return 0;
        if (e == 1) return f;
        if (memo[e][f] != -1) return memo[e][f];
        int lo = 1, hi = f;
        while (lo + 1 < hi) {                                 // find the crossing of an increasing and a decreasing curve
            int x = (lo + hi) >>> 1;
            int breaks = solve(e - 1, x - 1, memo), survives = solve(e, f - x, memo);
            if (breaks < survives) lo = x;
            else if (breaks > survives) hi = x;
            else lo = hi = x;
        }
        int best = Integer.MAX_VALUE;
        for (int x = lo; x <= hi; x++) best = Math.min(best, 1 + Math.max(solve(e - 1, x - 1, memo), solve(e, f - x, memo)));
        return memo[e][f] = best;
    }

    /**
     * Flip the question: with m moves and e eggs, how many floors can I fully cover?
     *   cover[m][e] = cover[m−1][e−1] (floors below, if it breaks) + 1 (this floor) + cover[m−1][e] (floors above).
     * The answer is the smallest m with cover[m][k] ≥ n. m ≤ n, and with ≥ log₂ n eggs, m ≈ log₂ n. O(k · m) time, O(k) space.
     */
    public static int movesDp(int k, int n) {
        if (n == 0) return 0;
        if (k == 0) return Integer.MAX_VALUE;
        long[] cover = new long[k + 1];                       // cover[e] for the current number of moves
        int m = 0;
        while (cover[k] < n) {
            m++;
            for (int e = k; e >= 1; e--) cover[e] = cover[e - 1] + 1 + cover[e];   // right-to-left: uses m−1 values
        }
        return m;
    }
}
