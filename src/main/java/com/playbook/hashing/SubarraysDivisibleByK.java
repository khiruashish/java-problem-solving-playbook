package com.playbook.hashing;

import java.util.HashMap;
import java.util.Map;

/**
 * Count non-empty contiguous subarrays whose sum is divisible by k (k ≥ 1). Values may be negative.
 * [4,5,0,-2,-3,1], k=5 → 7.
 */
public final class SubarraysDivisibleByK {
    private SubarraysDivisibleByK() {}

    /** Brute force: every (i, j) with a running sum. O(n²). */
    public static long bruteForce(int[] a, int k) {
        long count = 0;
        for (int i = 0; i < a.length; i++) {
            long sum = 0;
            for (int j = i; j < a.length; j++) {
                sum += a[j];
                if (sum % k == 0) count++;
            }
        }
        return count;
    }

    /**
     * Prefix sums + remainder counts. sum(i..j) = P[j+1] − P[i] is divisible by k ⇔ P[j+1] ≡ P[i] (mod k).
     * So for each prefix, add the number of EARLIER prefixes with the same remainder. Seed remainder 0 with count 1
     * (the empty prefix). Normalise negative remainders: Java's % keeps the sign of the dividend. O(n) time, O(k) space.
     */
    public static long prefixRemainders(int[] a, int k) {
        long[] seen = new long[k];
        seen[0] = 1;                                     // empty prefix P[0] = 0
        long count = 0, prefix = 0;
        for (int x : a) {
            prefix += x;
            int r = (int) (((prefix % k) + k) % k);      // −1 % 5 is −1 in Java; we want 4
            count += seen[r];                            // pair with every earlier prefix of the same remainder
            seen[r]++;
        }
        return count;
    }

    /** Same idea with a HashMap — what you'd write when k is huge and an array of size k is too big. */
    public static long prefixRemaindersMap(int[] a, int k) {
        Map<Long, Long> seen = new HashMap<>();
        seen.put(0L, 1L);
        long count = 0, prefix = 0;
        for (int x : a) {
            prefix += x;
            long r = Math.floorMod(prefix, (long) k);   // floorMod is always non-negative for k > 0
            long earlier = seen.getOrDefault(r, 0L);
            count += earlier;
            seen.put(r, earlier + 1);
        }
        return count;
    }

    /** Closed form: a remainder bucket with c prefixes contributes C(c, 2) pairs. Same O(n), two passes. */
    public static long combinatorial(int[] a, int k) {
        long[] bucket = new long[k];
        bucket[0] = 1;
        long prefix = 0;
        for (int x : a) {
            prefix += x;
            bucket[(int) Math.floorMod(prefix, (long) k)]++;
        }
        long count = 0;
        for (long c : bucket) count += c * (c - 1) / 2;
        return count;
    }
}
