package com.playbook.arrays;

import java.util.Arrays;
import java.util.stream.IntStream;

/** Array has n distinct numbers from 0..n; one is missing. Find it. [3,0,1] → 2. */
public final class MissingNumber {
    private MissingNumber() {}

    /** Sort and find the first index where a[i] != i. O(n log n). */
    public static int sorting(int[] a) {
        int[] c = a.clone();
        Arrays.sort(c);
        for (int i = 0; i < c.length; i++) if (c[i] != i) return i;
        return c.length;
    }

    /** Gauss sum: n(n+1)/2 − sum. Use long to avoid overflow. O(n), O(1). */
    public static int sumFormula(int[] a) {
        long n = a.length, expected = n * (n + 1) / 2, sum = 0;
        for (int x : a) sum += x;
        return (int) (expected - sum);
    }

    /** XOR trick: x ^ x = 0, so XOR of all indexes and values leaves the missing one. Overflow-free. */
    public static int xor(int[] a) {
        int x = a.length;
        for (int i = 0; i < a.length; i++) x ^= i ^ a[i];
        return x;
    }

    /** Java 8. */
    public static int streams(int[] a) {
        return IntStream.rangeClosed(0, a.length).sum() - Arrays.stream(a).sum();
    }
}
