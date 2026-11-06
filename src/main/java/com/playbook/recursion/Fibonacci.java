package com.playbook.recursion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** F(0)=0, F(1)=1, F(n)=F(n-1)+F(n-2). Shows how memoization turns exponential recursion into linear. */
public final class Fibonacci {
    private Fibonacci() {}

    /** Naive recursion: recomputes subproblems. O(2^n) time, O(n) stack. Fine for n ≤ ~35. */
    public static long naive(int n) {
        return n < 2 ? n : naive(n - 1) + naive(n - 2);
    }

    /** Top-down DP (memoization). O(n) time, O(n) space. */
    public static long memo(int n) {
        return memo(n, new HashMap<>());
    }

    private static long memo(int n, Map<Integer, Long> cache) {
        if (n < 2) return n;
        Long hit = cache.get(n);
        if (hit != null) return hit;
        long v = memo(n - 1, cache) + memo(n - 2, cache);
        cache.put(n, v);
        return v;
    }

    /** Bottom-up iterative "normal" solution: keep only the last two. O(n) time, O(1) space. */
    public static long iterative(int n) {
        long a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            long next = a + b;
            a = b;
            b = next;
        }
        return a;
    }

    /** Expert: matrix exponentiation [[1,1],[1,0]]^n. O(log n). */
    public static long matrix(int n) {
        long[][] result = {{1, 0}, {0, 1}}, m = {{1, 1}, {1, 0}};
        for (int p = n; p > 0; p >>= 1) {
            if ((p & 1) == 1) result = mul(result, m);
            m = mul(m, m);
        }
        return result[0][1];
    }

    private static long[][] mul(long[][] x, long[][] y) {
        return new long[][]{
                {x[0][0] * y[0][0] + x[0][1] * y[1][0], x[0][0] * y[0][1] + x[0][1] * y[1][1]},
                {x[1][0] * y[0][0] + x[1][1] * y[1][0], x[1][0] * y[0][1] + x[1][1] * y[1][1]}};
    }

    /** Java 8: first n terms with Stream.iterate over pairs. */
    public static List<Long> series(int count) {
        return Stream.iterate(new long[]{0, 1}, f -> new long[]{f[1], f[0] + f[1]})
                .limit(count).map(f -> f[0]).collect(Collectors.toList());
    }
}
