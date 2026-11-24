package com.playbook.bits;

/** For every i in 0..n, the number of 1 bits in i. Target: O(n) total — no per-number popcount loop. */
public final class CountingBits {
    private CountingBits() {}

    /** Brute force: Kernighan's popcount for each i. O(n · bits set). */
    public static int[] kernighanEach(int n) {
        int[] out = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            int x = i, c = 0;
            while (x != 0) { x &= x - 1; c++; }               // drop the lowest set bit
            out[i] = c;
        }
        return out;
    }

    /** DP on the last bit: i has the bits of i/2, plus its own lowest bit. bits[i] = bits[i >> 1] + (i & 1). O(n). */
    public static int[] halfPlusLowBit(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) bits[i] = bits[i >> 1] + (i & 1);
        return bits;
    }

    /** DP on the lowest set bit: i & (i − 1) is i without its lowest 1, so bits[i] = bits[i & (i−1)] + 1. O(n). */
    public static int[] dropLowestBit(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) bits[i] = bits[i & (i - 1)] + 1;
        return bits;
    }

    /** DP on the highest power of two: bits[i] = 1 + bits[i − 2^k] where 2^k is the largest power ≤ i. O(n). */
    public static int[] highestPower(int n) {
        int[] bits = new int[n + 1];
        int power = 1;
        for (int i = 1; i <= n; i++) {
            if (i == power * 2) power *= 2;                    // entered the next block [2^k, 2^(k+1))
            bits[i] = 1 + bits[i - power];
        }
        return bits;
    }

    /** Library check: Integer.bitCount (a few CPU instructions via POPCNT on modern JVMs). */
    public static int[] library(int n) {
        int[] out = new int[n + 1];
        for (int i = 0; i <= n; i++) out[i] = Integer.bitCount(i);
        return out;
    }
}
