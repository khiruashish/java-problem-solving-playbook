package com.playbook.logical;

import java.math.BigInteger;

/** Smallest positive number divisible by every integer 1..n (Project Euler 5: n=10 → 2520, n=20 → 232792560). */
public final class LcmOfRange {
    private LcmOfRange() {}

    /** Brute force: test multiples of n until one works. Fine for n ≤ ~20, hopeless beyond. */
    public static long bruteForce(int n) {
        for (long x = n; ; x += n) {
            boolean ok = true;
            for (int d = 1; d <= n && ok; d++) ok = x % d == 0;
            if (ok) return x;
        }
    }

    /** Fold lcm(acc, k) = acc / gcd(acc, k) * k across 1..n. O(n log). Overflows long beyond n = 42. */
    public static long iterative(int n) {
        long acc = 1;
        for (int k = 2; k <= n; k++) acc = acc / GcdLcm.iterative((int) (acc % k), k) * k;   // gcd(acc,k) = gcd(acc mod k, k)
        return acc;
    }

    /** Prime-power view: the answer is Π p^⌊log_p n⌋ over primes p ≤ n. Exact for any n with BigInteger. */
    public static BigInteger primePowers(int n) {
        BigInteger result = BigInteger.ONE;
        for (int p : PrimeNumbers.sieve(n)) {
            long power = p;
            while (power * p <= n) power *= p;               // largest power of p not exceeding n
            result = result.multiply(BigInteger.valueOf(power));
        }
        return result;
    }
}
