package com.playbook.bits;

/** Number of 1 bits (Hamming weight / popcount) of an int, treating it as UNSIGNED (so -1 → 32). */
public final class CountSetBits {
    private CountSetBits() {}

    /** Check all 32 bits. O(32). Unsigned shift >>> so negative numbers terminate. */
    public static int shiftLoop(int n) {
        int count = 0;
        while (n != 0) {
            count += n & 1;
            n >>>= 1;                                        // >> would keep shifting in 1s for negatives → infinite loop
        }
        return count;
    }

    /** Brian Kernighan: n & (n-1) removes one set bit per iteration → O(number of set bits). */
    public static int kernighan(int n) {
        int count = 0;
        while (n != 0) {
            n &= n - 1;
            count++;
        }
        return count;
    }

    /** Library (a single CPU instruction on modern hardware). */
    public static int library(int n) {
        return Integer.bitCount(n);
    }

    /** Counting bits for every number 0..n (LeetCode 338): bits(i) = bits(i >> 1) + (i & 1). O(n) DP. */
    public static int[] countAll(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) bits[i] = bits[i >> 1] + (i & 1);
        return bits;
    }

    /** Bits to flip to turn a into b = popcount(a XOR b) (Hamming distance). */
    public static int hammingDistance(int a, int b) {
        return Integer.bitCount(a ^ b);
    }
}
