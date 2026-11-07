package com.playbook.logical;

import java.util.stream.IntStream;

/**
 * Perfect number: equals the sum of its proper divisors (6 = 1+2+3, 28, 496, 8128).
 * Strong number: equals the sum of the factorials of its digits (145 = 1! + 4! + 5!).
 */
public final class PerfectStrongNumbers {
    private PerfectStrongNumbers() {}

    /** Brute force: try every d in 1..n-1. O(n). */
    public static boolean isPerfectBrute(int n) {
        if (n < 2) return false;
        long sum = 0;
        for (int d = 1; d < n; d++) if (n % d == 0) sum += d;
        return sum == n;
    }

    /** Divisors come in pairs (d, n/d): only scan to √n. O(√n). */
    public static boolean isPerfect(int n) {
        if (n < 2) return false;
        long sum = 1;                                        // 1 is always a proper divisor
        for (long d = 2; d * d <= n; d++) {
            if (n % d == 0) {
                sum += d;
                long pair = n / d;
                if (pair != d) sum += pair;                  // don't count √n twice
            }
        }
        return sum == n;
    }

    /** Java 8 version of the brute force (readable, O(n)). */
    public static boolean isPerfectStreams(int n) {
        return n > 1 && IntStream.range(1, n).filter(d -> n % d == 0).sum() == n;
    }

    private static final int[] FACT = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};   // 0!..9! precomputed

    /** Strong number: sum digit factorials with a lookup table. O(digits). */
    public static boolean isStrong(int n) {
        if (n <= 0) return false;
        int sum = 0;
        for (int x = n; x > 0; x /= 10) sum += FACT[x % 10];
        return sum == n;
    }

    /** Recursive digit-factorial sum. */
    public static boolean isStrongRecursive(int n) {
        return n > 0 && digitFactorialSum(n) == n;
    }

    private static int digitFactorialSum(int n) {
        return n == 0 ? 0 : FACT[n % 10] + digitFactorialSum(n / 10);
    }
}
