package com.playbook.bits;

/**
 * Integer division WITHOUT *, / or %. Truncate toward zero (like Java's /). The one overflowing case,
 * MIN_VALUE / −1, returns MAX_VALUE (LeetCode 29 convention).
 */
public final class DivideTwoIntegers {
    private DivideTwoIntegers() {}

    /** Brute force: subtract the divisor repeatedly. O(quotient) — up to 2³¹ steps, far too slow in general. */
    public static int repeatedSubtraction(int dividend, int divisor) {
        if (divisor == 0) throw new ArithmeticException("divide by zero");
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        boolean negative = (dividend < 0) ^ (divisor < 0);
        long a = Math.abs((long) dividend), b = Math.abs((long) divisor);
        int q = 0;
        while (a >= b) { a -= b; q++; }
        return negative ? -q : q;
    }

    /**
     * Doubling ("long division in binary"): for bits from 31 down to 0, if b·2^k fits into what is left of a,
     * subtract it and set bit k of the quotient. Uses only shifts and subtraction. O(32) = O(log) steps.
     * Works in long so |MIN_VALUE| is representable.
     */
    public static int shifts(int dividend, int divisor) {
        if (divisor == 0) throw new ArithmeticException("divide by zero");
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        boolean negative = (dividend < 0) ^ (divisor < 0);
        long a = Math.abs((long) dividend), b = Math.abs((long) divisor);
        long q = 0;
        for (int k = 31; k >= 0; k--) {
            if ((b << k) <= a) {                             // b · 2^k still fits
                a -= b << k;
                q |= 1L << k;
            }
        }
        return (int) (negative ? -q : q);
    }

    /**
     * No long allowed: work with NEGATIVE numbers (the negative int range is one larger, so MIN_VALUE is safe).
     * Repeatedly find the largest doubling of the divisor that still fits. O(log² q).
     */
    public static int negativesOnly(int dividend, int divisor) {
        if (divisor == 0) throw new ArithmeticException("divide by zero");
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        boolean negative = (dividend < 0) ^ (divisor < 0);
        int a = dividend > 0 ? -dividend : dividend;          // both ≤ 0
        int b = divisor > 0 ? -divisor : divisor;
        int q = 0;
        while (a <= b) {                                      // |a| ≥ |b|
            int chunk = b, times = -1;                         // counting negatively too
            // double while chunk + chunk stays ≥ a and doesn't overflow (chunk ≥ MIN_VALUE / 2)
            while (chunk >= (Integer.MIN_VALUE >> 1) && chunk + chunk >= a) {
                chunk += chunk;
                times += times;
            }
            a -= chunk;
            q += times;
        }
        return negative ? q : -q;
    }
}
