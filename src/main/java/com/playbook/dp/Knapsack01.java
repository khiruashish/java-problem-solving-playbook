package com.playbook.dp;

/** 0/1 knapsack: each item once; maximise value with total weight ≤ capacity. */
public final class Knapsack01 {
    private Knapsack01() {}

    /** Recursion: for item i, skip it or take it (if it fits). O(2^n). */
    public static int recursive(int[] wt, int[] val, int cap) {
        return rec(wt, val, wt.length - 1, cap);
    }

    private static int rec(int[] wt, int[] val, int i, int cap) {
        if (i < 0 || cap == 0) return 0;
        int skip = rec(wt, val, i - 1, cap);
        if (wt[i] > cap) return skip;
        return Math.max(skip, val[i] + rec(wt, val, i - 1, cap - wt[i]));
    }

    /** Memoized on (item, capacity). O(n·W). */
    public static int memo(int[] wt, int[] val, int cap) {
        Integer[][] cache = new Integer[wt.length][cap + 1];
        return memo(wt, val, wt.length - 1, cap, cache);
    }

    private static int memo(int[] wt, int[] val, int i, int cap, Integer[][] cache) {
        if (i < 0 || cap == 0) return 0;
        if (cache[i][cap] != null) return cache[i][cap];
        int skip = memo(wt, val, i - 1, cap, cache);
        int best = wt[i] > cap ? skip : Math.max(skip, val[i] + memo(wt, val, i - 1, cap - wt[i], cache));
        return cache[i][cap] = best;
    }

    /** 2D table: dp[i][w] = best using the first i items with capacity w. O(n·W) time and space. */
    public static int table(int[] wt, int[] val, int cap) {
        int n = wt.length;
        int[][] dp = new int[n + 1][cap + 1];
        for (int i = 1; i <= n; i++)
            for (int w = 0; w <= cap; w++) {
                dp[i][w] = dp[i - 1][w];
                if (wt[i - 1] <= w) dp[i][w] = Math.max(dp[i][w], val[i - 1] + dp[i - 1][w - wt[i - 1]]);
            }
        return dp[n][cap];
    }

    /**
     * 1D: one row, capacity iterated DOWNWARD so dp[w - wt] still holds the previous item's row
     * (iterating upward would let an item be reused = unbounded knapsack). O(n·W) time, O(W) space.
     */
    public static int optimal(int[] wt, int[] val, int cap) {
        int[] dp = new int[cap + 1];
        for (int i = 0; i < wt.length; i++)
            for (int w = cap; w >= wt[i]; w--) dp[w] = Math.max(dp[w], val[i] + dp[w - wt[i]]);
        return dp[cap];
    }

    /** Unbounded knapsack (items reusable): the same loop, capacity iterated UPWARD. */
    public static int unbounded(int[] wt, int[] val, int cap) {
        int[] dp = new int[cap + 1];
        for (int i = 0; i < wt.length; i++)
            for (int w = wt[i]; w <= cap; w++) dp[w] = Math.max(dp[w], val[i] + dp[w - wt[i]]);
        return dp[cap];
    }
}
