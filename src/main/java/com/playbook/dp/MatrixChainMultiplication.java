package com.playbook.dp;

/**
 * Matrices A1..An where Ai has dims[i-1] × dims[i]. Minimum scalar multiplications to compute the product
 * (choosing the parenthesisation). dims = [10, 30, 5, 60] → 4500: (A1·A2)·A3.
 */
public final class MatrixChainMultiplication {
    private MatrixChainMultiplication() {}

    /** Recursion: try every split point k for the chain i..j. Exponential (Catalan number of orders). */
    public static long recursive(int[] dims) {
        return rec(dims, 1, dims.length - 1);
    }

    private static long rec(int[] d, int i, int j) {
        if (i >= j) return 0;                                // one matrix: no multiplication
        long best = Long.MAX_VALUE;
        for (int k = i; k < j; k++)
            best = Math.min(best, rec(d, i, k) + rec(d, k + 1, j) + (long) d[i - 1] * d[k] * d[j]);
        return best;
    }

    /** Memoized on (i, j). O(n^3). */
    public static long memo(int[] dims) {
        int n = dims.length;
        return memo(dims, 1, n - 1, new long[n][n]);
    }

    private static long memo(int[] d, int i, int j, long[][] cache) {
        if (i >= j) return 0;
        if (cache[i][j] != 0) return cache[i][j];
        long best = Long.MAX_VALUE;
        for (int k = i; k < j; k++)
            best = Math.min(best, memo(d, i, k, cache) + memo(d, k + 1, j, cache) + (long) d[i - 1] * d[k] * d[j]);
        return cache[i][j] = best;
    }

    /** Interval DP: solve chains by increasing LENGTH so sub-chains are always ready. O(n^3) time, O(n^2) space. */
    public static long table(int[] dims) {
        int n = dims.length - 1;                             // number of matrices
        long[][] dp = new long[n + 1][n + 1];
        for (int len = 2; len <= n; len++)
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                dp[i][j] = Long.MAX_VALUE;
                for (int k = i; k < j; k++)
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + (long) dims[i - 1] * dims[k] * dims[j]);
            }
        return dp[1][n];
    }
}
