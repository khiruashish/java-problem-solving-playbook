package com.playbook.arrays;

/** Stock prices by day. Max profit from ONE buy then a later sell (0 if no profit). Plus multi-transaction variant. */
public final class BuySellStock {
    private BuySellStock() {}

    /** Brute force: every buy/sell pair. O(n^2). */
    public static int bruteForce(int[] prices) {
        int best = 0;
        for (int i = 0; i < prices.length; i++)
            for (int j = i + 1; j < prices.length; j++) best = Math.max(best, prices[j] - prices[i]);
        return best;
    }

    /** Optimal: track the minimum price so far; profit if sold today. O(n), O(1). */
    public static int optimal(int[] prices) {
        int minSoFar = Integer.MAX_VALUE, best = 0;
        for (int p : prices) {
            minSoFar = Math.min(minSoFar, p);
            best = Math.max(best, p - minSoFar);
        }
        return best;
    }

    /** Unlimited transactions (LeetCode 122): sum every positive day-to-day rise. Greedy, O(n). */
    public static int unlimitedTransactions(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) profit += Math.max(0, prices[i] - prices[i - 1]);
        return profit;
    }
}
