package com.playbook.greedy;

import java.util.Arrays;

/** a[i] = max jump length from index i. (I) Can you reach the last index? (II) Minimum jumps to reach it. */
public final class JumpGame {
    private JumpGame() {}

    /** DP O(n^2): reachable[i] if some reachable j < i can jump to i. */
    public static boolean canReachDp(int[] a) {
        boolean[] ok = new boolean[a.length];
        ok[0] = true;
        for (int i = 1; i < a.length; i++)
            for (int j = 0; j < i && !ok[i]; j++) ok[i] = ok[j] && j + a[j] >= i;
        return ok[a.length - 1];
    }

    /** Greedy O(n): track the furthest index reachable so far; if we ever stand beyond it, we're stuck. */
    public static boolean canReach(int[] a) {
        int furthest = 0;
        for (int i = 0; i < a.length; i++) {
            if (i > furthest) return false;
            furthest = Math.max(furthest, i + a[i]);
        }
        return true;
    }

    /** DP O(n^2) min jumps; Integer.MAX_VALUE marks unreachable. Returns -1 if the end is unreachable. */
    public static int minJumpsDp(int[] a) {
        int[] dp = new int[a.length];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i < a.length; i++)
            for (int j = 0; j < i; j++)
                if (dp[j] != Integer.MAX_VALUE && j + a[j] >= i) dp[i] = Math.min(dp[i], dp[j] + 1);
        return dp[a.length - 1] == Integer.MAX_VALUE ? -1 : dp[a.length - 1];
    }

    /**
     * Greedy O(n) — "implicit BFS": [start, currentEnd] is the set of indexes reachable with `jumps` jumps.
     * Scanning it, track the furthest reach; when we pass currentEnd we must jump once more. -1 if stuck.
     */
    public static int minJumps(int[] a) {
        int jumps = 0, currentEnd = 0, furthest = 0;
        for (int i = 0; i < a.length - 1; i++) {
            if (i > furthest) return -1;
            furthest = Math.max(furthest, i + a[i]);
            if (i == currentEnd) {                           // exhausted this BFS level
                if (furthest <= i) return -1;
                jumps++;
                currentEnd = furthest;
            }
        }
        return jumps;
    }
}
