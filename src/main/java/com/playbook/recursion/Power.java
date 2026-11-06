package com.playbook.recursion;

/** x^n for int n (may be negative). Shows linear vs logarithmic recursion ("fast exponentiation"). */
public final class Power {
    private Power() {}

    /** Naive recursion: x^n = x · x^(n-1). O(n) — stack overflow for large n. */
    public static double naive(double x, int n) {
        if (n < 0) return 1 / naive(x, -n);
        return n == 0 ? 1 : x * naive(x, n - 1);
    }

    /** Fast recursive: x^n = (x^(n/2))², times x if n odd. O(log n). */
    public static double fastRecursive(double x, int n) {
        long N = n;                                          // -Integer.MIN_VALUE overflows int → use long
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        return fast(x, N);
    }

    private static double fast(double x, long n) {
        if (n == 0) return 1;
        double half = fast(x, n / 2);                        // compute ONCE, don't call twice
        return n % 2 == 0 ? half * half : half * half * x;
    }

    /** Fast iterative (binary exponentiation): walk bits of n. O(log n), O(1). */
    public static double fastIterative(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        double result = 1;
        while (N > 0) {
            if ((N & 1) == 1) result *= x;
            x *= x;
            N >>= 1;
        }
        return result;
    }

    /** Modular exponentiation (a^b mod m) — used in hashing / cryptography problems. */
    public static long modPow(long a, long b, long m) {
        long r = 1;
        a %= m;
        while (b > 0) {
            if ((b & 1) == 1) r = r * a % m;
            a = a * a % m;
            b >>= 1;
        }
        return r;
    }
}
