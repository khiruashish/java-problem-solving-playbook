package com.playbook.logical;

import java.util.Arrays;

/** Greatest common divisor and least common multiple. gcd(0, 0) is defined as 0 here. */
public final class GcdLcm {
    private GcdLcm() {}

    /** Brute force: largest d dividing both. O(min(a,b)). */
    public static int bruteForce(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (a == 0 || b == 0) return a + b;
        for (int d = Math.min(a, b); d >= 1; d--) if (a % d == 0 && b % d == 0) return d;
        return 1;
    }

    /** Euclid recursive: gcd(a, b) = gcd(b, a mod b); gcd(a, 0) = a. O(log min(a,b)). */
    public static int recursive(int a, int b) {
        return b == 0 ? Math.abs(a) : recursive(b, a % b);
    }

    /** Euclid iterative. */
    public static int iterative(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return Math.abs(a);
    }

    /** lcm = a / gcd * b — divide FIRST to avoid overflow. */
    public static long lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return Math.abs((long) a / iterative(a, b) * b);
    }

    /** GCD of an array (Java 8 reduce). */
    public static int gcdOfArray(int[] a) {
        return Arrays.stream(a).reduce(0, GcdLcm::iterative);
    }
}
