package com.playbook.logical;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/** Primality test and generating primes up to n. */
public final class PrimeNumbers {
    private PrimeNumbers() {}

    /** Brute force: try every divisor 2..n-1. O(n). */
    public static boolean bruteForce(int n) {
        if (n < 2) return false;
        for (int d = 2; d < n; d++) if (n % d == 0) return false;
        return true;
    }

    /** Optimal trial division: only up to √n, skip evens, then 6k±1. O(√n). */
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n < 4) return true;                              // 2 and 3
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (long i = 5; i * i <= n; i += 6) {               // every prime > 3 is 6k-1 or 6k+1
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    /** Java 8. */
    public static boolean streams(int n) {
        return n >= 2 && IntStream.rangeClosed(2, (int) Math.sqrt(n)).noneMatch(d -> n % d == 0);
    }

    /** Sieve of Eratosthenes: all primes ≤ n. O(n log log n) time, O(n) space. */
    public static List<Integer> sieve(int n) {
        List<Integer> primes = new ArrayList<>();
        if (n < 2) return primes;
        boolean[] composite = new boolean[n + 1];
        for (long i = 2; i * i <= n; i++) {
            if (!composite[(int) i]) {
                for (long j = i * i; j <= n; j += i) composite[(int) j] = true;   // start at i², smaller multiples done
            }
        }
        for (int i = 2; i <= n; i++) if (!composite[i]) primes.add(i);
        return primes;
    }
}
