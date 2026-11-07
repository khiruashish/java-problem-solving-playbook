package com.playbook.dp;

import java.util.Arrays;

/** Ways to climb n stairs taking 1 or 2 steps (n ≥ 0; 0 stairs → 1 way). The "hello world" of DP. */
public final class ClimbingStairs {
    private ClimbingStairs() {}

    /** 1. Plain recursion: last move was 1 or 2 steps → f(n) = f(n-1) + f(n-2). O(2^n). */
    public static long recursive(int n) {
        if (n <= 1) return 1;
        return recursive(n - 1) + recursive(n - 2);
    }

    /** 2. Memoization (top-down): cache each f(k). O(n) time, O(n) space. */
    public static long memo(int n) {
        long[] cache = new long[n + 1];
        Arrays.fill(cache, -1);
        return memo(n, cache);
    }

    private static long memo(int n, long[] cache) {
        if (n <= 1) return 1;
        if (cache[n] != -1) return cache[n];
        return cache[n] = memo(n - 1, cache) + memo(n - 2, cache);
    }

    /** 3. Tabulation (bottom-up): fill dp[0..n] in order. O(n), O(n). */
    public static long table(int n) {
        long[] dp = new long[Math.max(2, n + 1)];
        dp[0] = dp[1] = 1;
        for (int i = 2; i <= n; i++) dp[i] = dp[i - 1] + dp[i - 2];
        return dp[n];
    }

    /** 4. Space-optimised: only the last two values matter. O(n), O(1). */
    public static long optimal(int n) {
        long prev2 = 1, prev1 = 1;                           // f(0), f(1)
        for (int i = 2; i <= n; i++) {
            long cur = prev1 + prev2;
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }

    /** Generalisation: allowed step sizes are given (e.g. {1, 3, 5}). dp[i] = Σ dp[i - s]. O(n·k). */
    public static long withSteps(int n, int[] steps) {
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) for (int s : steps) if (i >= s) dp[i] += dp[i - s];
        return dp[n];
    }
}
