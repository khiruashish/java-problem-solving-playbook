package com.playbook.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * Burst balloon i to earn left·nums[i]·right (neighbours at that moment; outside = 1). Max total coins.
 * [3,1,5,8] → 167. (LeetCode 312)
 */
public final class BurstBalloons {
    private BurstBalloons() {}

    /** Brute force: try every balloon as the NEXT one to burst, recursively. O(n!). Only for tiny inputs. */
    public static int bruteForce(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int x : nums) list.add(x);
        return brute(list);
    }

    private static int brute(List<Integer> b) {
        if (b.isEmpty()) return 0;
        int best = 0;
        for (int i = 0; i < b.size(); i++) {
            int left = i == 0 ? 1 : b.get(i - 1), right = i == b.size() - 1 ? 1 : b.get(i + 1);
            int coins = left * b.get(i) * right;
            Integer removed = b.remove(i);
            best = Math.max(best, coins + brute(b));
            b.add(i, removed);                               // backtrack
        }
        return best;
    }

    /**
     * Interval DP, thinking BACKWARDS: choose k = the LAST balloon to burst inside the open interval (i, j).
     * When k bursts last, its neighbours are exactly the interval boundaries i and j — fixed, independent of the order
     * inside. So dp[i][j] = max over k of dp[i][k] + a[i]·a[k]·a[j] + dp[k][j]. O(n^3).
     */
    public static int intervalDp(int[] nums) {
        int n = nums.length;
        int[] a = new int[n + 2];
        a[0] = a[n + 1] = 1;                                 // virtual boundary balloons
        System.arraycopy(nums, 0, a, 1, n);
        int[][] dp = new int[n + 2][n + 2];
        for (int len = 2; len <= n + 1; len++)               // len = j - i
            for (int i = 0; i + len <= n + 1; i++) {
                int j = i + len;
                for (int k = i + 1; k < j; k++)
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + a[i] * a[k] * a[j] + dp[k][j]);
            }
        return dp[0][n + 1];
    }
}
