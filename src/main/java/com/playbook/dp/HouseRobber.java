package com.playbook.dp;

import java.util.Arrays;

/** Max money robbing houses in a row without robbing two adjacent ones. [2,7,9,3,1] → 12 (2 + 9 + 1). */
public final class HouseRobber {
    private HouseRobber() {}

    /** Recursion from the end: rob house i (skip i-1) or skip it. O(2^n). */
    public static int recursive(int[] a) {
        return rob(a, a.length - 1);
    }

    private static int rob(int[] a, int i) {
        if (i < 0) return 0;
        return Math.max(rob(a, i - 1), a[i] + rob(a, i - 2));
    }

    /** Memoized. O(n). */
    public static int memo(int[] a) {
        int[] cache = new int[a.length];
        Arrays.fill(cache, -1);
        return memo(a, a.length - 1, cache);
    }

    private static int memo(int[] a, int i, int[] cache) {
        if (i < 0) return 0;
        if (cache[i] != -1) return cache[i];
        return cache[i] = Math.max(memo(a, i - 1, cache), a[i] + memo(a, i - 2, cache));
    }

    /** Tabulation with O(1) space: best including up to previous house, and up to the one before. */
    public static int optimal(int[] a) {
        int prev2 = 0, prev1 = 0;
        for (int x : a) {
            int cur = Math.max(prev1, prev2 + x);            // skip x, or take x on top of best two back
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }

    /** Circular street (LeetCode 213): first and last are neighbours → max(rob without last, rob without first). */
    public static int circular(int[] a) {
        if (a.length == 1) return a[0];
        return Math.max(optimal(Arrays.copyOfRange(a, 0, a.length - 1)), optimal(Arrays.copyOfRange(a, 1, a.length)));
    }
}
