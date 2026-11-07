package com.playbook.bits;

import java.util.ArrayList;
import java.util.List;

/** Enumerating subsets with bitmasks: all subsets, all submasks of a mask, and Gray code. */
public final class BitmaskSubsets {
    private BitmaskSubsets() {}

    /** Each mask in [0, 2^n) IS a subset: bit j set ⇔ include a[j]. O(n·2^n). */
    public static List<List<Integer>> subsets(int[] a) {
        List<List<Integer>> out = new ArrayList<>();
        for (int mask = 0; mask < (1 << a.length); mask++) {
            List<Integer> s = new ArrayList<>();
            for (int j = 0; j < a.length; j++) if ((mask >> j & 1) == 1) s.add(a[j]);
            out.add(s);
        }
        return out;
    }

    /**
     * All submasks of `mask` in decreasing order (including mask and 0): sub = (sub - 1) & mask.
     * Subtracting 1 flips the lowest set bit and everything below; & mask keeps only allowed bits.
     * Iterating submasks of every mask costs O(3^n) total — the basis of subset-DP.
     */
    public static List<Integer> submasks(int mask) {
        List<Integer> out = new ArrayList<>();
        for (int sub = mask; ; sub = (sub - 1) & mask) {
            out.add(sub);
            if (sub == 0) break;
        }
        return out;
    }

    /** Gray code: successive values differ in exactly one bit; g(i) = i ^ (i >> 1). */
    public static List<Integer> grayCode(int n) {
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) out.add(i ^ (i >> 1));
        return out;
    }

    /** Subsets whose sum equals target, counted by brute-force mask enumeration (n ≤ ~20). */
    public static int countSubsetsWithSum(int[] a, int target) {
        int count = 0;
        for (int mask = 0; mask < (1 << a.length); mask++) {
            int sum = 0;
            for (int j = 0; j < a.length; j++) if ((mask >> j & 1) == 1) sum += a[j];
            if (sum == target) count++;
        }
        return count;
    }
}
