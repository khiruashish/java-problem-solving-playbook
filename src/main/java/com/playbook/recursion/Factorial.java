package com.playbook.recursion;

import java.math.BigInteger;
import java.util.stream.LongStream;

/** n! for n ≥ 0. long overflows past 20!, so use BigInteger beyond that. Negative → IllegalArgumentException. */
public final class Factorial {
    private Factorial() {}

    /** Recursive: n! = n × (n-1)!, base 0! = 1. O(n) time, O(n) stack. */
    public static long recursive(int n) {
        check(n);
        return n <= 1 ? 1 : n * recursive(n - 1);
    }

    /** Tail-recursive form (Java does NOT optimise tail calls, but the shape converts to a loop easily). */
    public static long tailRecursive(int n) {
        check(n);
        return tail(n, 1);
    }

    private static long tail(int n, long acc) {
        return n <= 1 ? acc : tail(n - 1, acc * n);
    }

    /** Iterative "normal" solution. O(n), O(1). */
    public static long iterative(int n) {
        check(n);
        long r = 1;
        for (int i = 2; i <= n; i++) r *= i;
        return r;
    }

    /** Java 8 streams. */
    public static long streams(int n) {
        check(n);
        return LongStream.rangeClosed(1, n).reduce(1, (a, b) -> a * b);
    }

    /** Arbitrary size. 100! has 158 digits. */
    public static BigInteger big(int n) {
        check(n);
        BigInteger r = BigInteger.ONE;
        for (int i = 2; i <= n; i++) r = r.multiply(BigInteger.valueOf(i));
        return r;
    }

    private static void check(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
    }
}
