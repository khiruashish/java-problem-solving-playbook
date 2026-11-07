package com.playbook.dp;

import java.util.Arrays;

/** Unlimited coins of each denomination. (1) fewest coins for amount, -1 if impossible. (2) number of combinations. */
public final class CoinChange {
    private CoinChange() {}

    /** Recursion: try every coin as the last one. Exponential. */
    public static int minCoinsRecursive(int[] coins, int amount) {
        if (amount == 0) return 0;
        int best = Integer.MAX_VALUE;
        for (int c : coins) {
            if (c > amount) continue;
            int sub = minCoinsRecursive(coins, amount - c);
            if (sub != -1) best = Math.min(best, sub + 1);
        }
        return best == Integer.MAX_VALUE ? -1 : best;
    }

    /** Memoized: cache by remaining amount. O(amount · coins). */
    public static int minCoinsMemo(int[] coins, int amount) {
        int[] cache = new int[amount + 1];
        Arrays.fill(cache, -2);                              // -2 = not computed, -1 = impossible
        return memo(coins, amount, cache);
    }

    private static int memo(int[] coins, int amount, int[] cache) {
        if (amount == 0) return 0;
        if (cache[amount] != -2) return cache[amount];
        int best = Integer.MAX_VALUE;
        for (int c : coins) {
            if (c > amount) continue;
            int sub = memo(coins, amount - c, cache);
            if (sub != -1) best = Math.min(best, sub + 1);
        }
        return cache[amount] = best == Integer.MAX_VALUE ? -1 : best;
    }

    /** Bottom-up: dp[a] = 1 + min(dp[a - c]). "amount + 1" acts as infinity (can never be reached). O(amount · coins). */
    public static int minCoins(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int a = 1; a <= amount; a++)
            for (int c : coins) if (c <= a) dp[a] = Math.min(dp[a], dp[a - c] + 1);
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * Number of COMBINATIONS (order doesn't matter, LeetCode 518). Coins in the OUTER loop: each coin is "unlocked" once,
     * so {1,2} and {2,1} are counted once. Swapping the loops counts permutations instead.
     */
    public static long combinations(int[] coins, int amount) {
        long[] dp = new long[amount + 1];
        dp[0] = 1;
        for (int c : coins) for (int a = c; a <= amount; a++) dp[a] += dp[a - c];
        return dp[amount];
    }

    /** Number of ordered sequences (permutations) — loops swapped. */
    public static long permutations(int[] coins, int amount) {
        long[] dp = new long[amount + 1];
        dp[0] = 1;
        for (int a = 1; a <= amount; a++) for (int c : coins) if (c <= a) dp[a] += dp[a - c];
        return dp[amount];
    }
}
