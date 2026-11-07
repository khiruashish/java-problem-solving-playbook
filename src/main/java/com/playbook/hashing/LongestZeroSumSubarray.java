package com.playbook.hashing;

import java.util.HashMap;
import java.util.Map;

/**
 * Longest subarray with sum 0 (GfG) and its twin: longest subarray with equal numbers of 0s and 1s
 * (LeetCode 525 — map 0 → -1 and it becomes the same problem). Generalised to sum k.
 */
public final class LongestZeroSumSubarray {
    private LongestZeroSumSubarray() {}

    /** Brute force: every start, running sum. O(n^2). */
    public static int bruteForce(int[] a, int k) {
        int best = 0;
        for (int i = 0; i < a.length; i++) {
            long sum = 0;
            for (int j = i; j < a.length; j++) {
                sum += a[j];
                if (sum == k) best = Math.max(best, j - i + 1);
            }
        }
        return best;
    }

    /**
     * Optimal: prefix sums with the FIRST index each prefix was seen. If prefix[j] - k was first seen at i,
     * a[i+1..j] sums to k and is the longest such subarray ending at j. Never overwrite → keeps it longest. O(n).
     */
    public static int longestWithSum(int[] a, int k) {
        Map<Long, Integer> firstIndex = new HashMap<>();
        firstIndex.put(0L, -1);                              // empty prefix "ends" before index 0
        long prefix = 0;
        int best = 0;
        for (int j = 0; j < a.length; j++) {
            prefix += a[j];
            Integer i = firstIndex.get(prefix - k);
            if (i != null) best = Math.max(best, j - i);
            firstIndex.putIfAbsent(prefix, j);               // FIRST occurrence only
        }
        return best;
    }

    public static int longestZeroSum(int[] a) {
        return longestWithSum(a, 0);
    }

    /** Equal 0s and 1s: treat 0 as -1, then it is "longest subarray with sum 0". */
    public static int longestEqualZerosOnes(int[] bits) {
        int[] mapped = new int[bits.length];
        for (int i = 0; i < bits.length; i++) mapped[i] = bits[i] == 0 ? -1 : 1;
        return longestZeroSum(mapped);
    }
}
