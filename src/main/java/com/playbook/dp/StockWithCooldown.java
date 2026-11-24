package com.playbook.dp;

import java.util.Arrays;

/**
 * Best time to buy and sell stock with cooldown: unlimited transactions, hold at most one share, and after a SELL
 * you must skip the next day before buying again. Return the maximum profit.
 */
public final class StockWithCooldown {
    private StockWithCooldown() {}

    /** Plain recursion over (day, holding): try every action. O(2ⁿ). */
    public static int recursive(int[] prices) {
        return go(prices, 0, false);
    }

    private static int go(int[] p, int day, boolean holding) {
        if (day >= p.length) return 0;
        int skip = go(p, day + 1, holding);
        int act = holding
                ? p[day] + go(p, day + 2, false)          // sell today, day+1 is the cooldown
                : -p[day] + go(p, day + 1, true);         // buy today
        return Math.max(skip, act);
    }

    /** Memoised recursion: only 2·n distinct states. O(n). */
    public static int memo(int[] prices) {
        int[][] m = new int[prices.length][2];
        for (int[] row : m) Arrays.fill(row, Integer.MIN_VALUE);
        return memo(prices, 0, 0, m);
    }

    private static int memo(int[] p, int day, int holding, int[][] m) {
        if (day >= p.length) return 0;
        if (m[day][holding] != Integer.MIN_VALUE) return m[day][holding];
        int skip = memo(p, day + 1, holding, m);
        int act = holding == 1 ? p[day] + memo(p, day + 2, 0, m) : -p[day] + memo(p, day + 1, 1, m);
        return m[day][holding] = Math.max(skip, act);
    }

    /**
     * State machine, O(n) time and O(1) space. Three states at the END of each day:
     *   hold — I own a share;   sold — I sold today (so tomorrow is a cooldown);   rest — no share, free to buy.
     *   hold' = max(hold, rest − price)     (keep, or buy — only from rest, never straight after selling)
     *   sold' = hold + price                (sell)
     *   rest' = max(rest, sold)             (do nothing, or finish a cooldown)
     */
    public static int stateMachine(int[] prices) {
        if (prices.length == 0) return 0;
        int hold = -prices[0], sold = Integer.MIN_VALUE / 2, rest = 0;   // can't have sold on day 0
        for (int i = 1; i < prices.length; i++) {
            int prevHold = hold, prevSold = sold;
            hold = Math.max(hold, rest - prices[i]);
            sold = prevHold + prices[i];
            rest = Math.max(rest, prevSold);
        }
        return Math.max(sold, rest);                    // ending while holding is never optimal
    }
}
