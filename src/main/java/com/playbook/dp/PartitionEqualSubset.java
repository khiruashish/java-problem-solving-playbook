package com.playbook.dp;

import java.util.BitSet;

/** Can the positive numbers be split into two subsets of equal sum? [1,5,11,5] → true (11 | 1+5+5). */
public final class PartitionEqualSubset {
    private PartitionEqualSubset() {}

    /** Reduce to subset-sum: is there a subset summing to total/2? Recursive take/skip. O(2^n). */
    public static boolean recursive(int[] a) {
        int total = sum(a);
        return total % 2 == 0 && subset(a, a.length - 1, total / 2);
    }

    private static boolean subset(int[] a, int i, int target) {
        if (target == 0) return true;
        if (i < 0 || target < 0) return false;
        return subset(a, i - 1, target - a[i]) || subset(a, i - 1, target);
    }

    /** 1D boolean DP, sums iterated DOWNWARD (each number used once, like 0/1 knapsack). O(n · sum). */
    public static boolean dp(int[] a) {
        int total = sum(a);
        if (total % 2 != 0) return false;
        int target = total / 2;
        boolean[] can = new boolean[target + 1];
        can[0] = true;
        for (int x : a)
            for (int s = target; s >= x; s--) can[s] |= can[s - x];
        return can[target];
    }

    /** Bitset trick: bit s set = sum s reachable. Adding x is (bits | bits << x) — 64 sums per machine word. */
    public static boolean bitset(int[] a) {
        int total = sum(a);
        if (total % 2 != 0) return false;
        BitSet reachable = new BitSet(total + 1);
        reachable.set(0);
        for (int x : a) {
            BitSet shifted = new BitSet(total + 1);
            for (int s = reachable.nextSetBit(0); s >= 0 && s + x <= total; s = reachable.nextSetBit(s + 1)) shifted.set(s + x);
            reachable.or(shifted);
        }
        return reachable.get(total / 2);
    }

    private static int sum(int[] a) {
        int s = 0;
        for (int x : a) s += x;
        return s;
    }
}
