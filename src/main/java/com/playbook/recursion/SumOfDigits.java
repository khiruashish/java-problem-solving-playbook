package com.playbook.recursion;

/** Sum of digits of a non-negative number, and digital root (repeat until one digit). */
public final class SumOfDigits {
    private SumOfDigits() {}

    /** Recursive: last digit + sum of the rest. Base: n == 0. O(d). */
    public static int recursive(long n) {
        return n == 0 ? 0 : (int) (n % 10) + recursive(n / 10);
    }

    /** Iterative. */
    public static int iterative(long n) {
        int sum = 0;
        for (; n > 0; n /= 10) sum += (int) (n % 10);
        return sum;
    }

    /** Java 8. */
    public static int streams(long n) {
        return String.valueOf(n).chars().map(c -> c - '0').sum();
    }

    /** Digital root, recursive: keep summing until one digit. */
    public static int digitalRoot(long n) {
        return n < 10 ? (int) n : digitalRoot(iterative(n));
    }

    /** Digital root in O(1): 1 + (n-1) % 9 (the "casting out nines" rule). */
    public static int digitalRootFormula(long n) {
        return n == 0 ? 0 : (int) (1 + (n - 1) % 9);
    }
}
