package com.playbook.dp;

/** Robot moves only right/down in an m×n grid from top-left to bottom-right. Count paths; variant with obstacles. */
public final class UniquePaths {
    private UniquePaths() {}

    /** Recursion: paths(r, c) = paths(r-1, c) + paths(r, c-1). O(2^(m+n)). */
    public static long recursive(int m, int n) {
        if (m == 1 || n == 1) return 1;
        return recursive(m - 1, n) + recursive(m, n - 1);
    }

    /** Memoized. O(m·n). */
    public static long memo(int m, int n) {
        return memo(m, n, new long[m + 1][n + 1]);
    }

    private static long memo(int m, int n, long[][] cache) {
        if (m == 1 || n == 1) return 1;
        if (cache[m][n] != 0) return cache[m][n];
        return cache[m][n] = memo(m - 1, n, cache) + memo(m, n - 1, cache);
    }

    /** 2D table. O(m·n) time and space. */
    public static long table(int m, int n) {
        long[][] dp = new long[m][n];
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++) dp[r][c] = (r == 0 || c == 0) ? 1 : dp[r - 1][c] + dp[r][c - 1];
        return dp[m - 1][n - 1];
    }

    /** 1D rolling row: row[c] already holds "from above"; add "from the left". O(m·n) time, O(n) space. */
    public static long rolling(int m, int n) {
        long[] row = new long[n];
        java.util.Arrays.fill(row, 1);
        for (int r = 1; r < m; r++) for (int c = 1; c < n; c++) row[c] += row[c - 1];
        return row[n - 1];
    }

    /** Combinatorics: choose which (m-1) of the (m+n-2) moves are "down". O(min(m, n)). */
    public static long combinatorics(int m, int n) {
        long result = 1;
        int moves = m + n - 2, k = Math.min(m - 1, n - 1);
        for (int i = 1; i <= k; i++) result = result * (moves - k + i) / i;   // stays an integer at every step
        return result;
    }

    /** With obstacles (1 = blocked): same rolling row, but a blocked cell has 0 paths. */
    public static long withObstacles(int[][] grid) {
        int n = grid[0].length;
        long[] row = new long[n];
        row[0] = grid[0][0] == 1 ? 0 : 1;
        for (int[] cells : grid)
            for (int c = 0; c < n; c++) {
                if (cells[c] == 1) row[c] = 0;
                else if (c > 0) row[c] += row[c - 1];
            }
        return row[n - 1];
    }
}
