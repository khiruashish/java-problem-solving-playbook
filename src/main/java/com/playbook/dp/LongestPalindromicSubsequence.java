package com.playbook.dp;

/**
 * Longest palindromic SUBSEQUENCE (characters need not be contiguous). "bbbab" → 4 ("bbbb"); "cbbd" → 2.
 * Contrast: longest palindromic SUBSTRING is contiguous (see strings).
 */
public final class LongestPalindromicSubsequence {
    private LongestPalindromicSubsequence() {}

    /** Recursion on the interval [i, j]. O(2ⁿ). */
    public static int recursive(String s) {
        return s.isEmpty() ? 0 : go(s, 0, s.length() - 1);
    }

    private static int go(String s, int i, int j) {
        if (i > j) return 0;
        if (i == j) return 1;
        if (s.charAt(i) == s.charAt(j)) return 2 + go(s, i + 1, j - 1);   // both ends join the palindrome
        return Math.max(go(s, i + 1, j), go(s, i, j - 1));               // drop one end
    }

    /**
     * Interval DP. dp[i][j] = LPS of s[i..j]. Fill by i from right to left so dp[i+1][*] is ready. O(n²) time/space.
     */
    public static int intervalDp(String s) {
        int n = s.length();
        if (n == 0) return 0;
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j)
                        ? 2 + dp[i + 1][j - 1]                  // for j == i+1 this reads dp[i+1][i] = 0
                        : Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
        return dp[0][n - 1];
    }

    /** Same DP with two rows: dp[i][*] needs only dp[i+1][*] and the current row. O(n) space. */
    public static int twoRows(String s) {
        int n = s.length();
        if (n == 0) return 0;
        int[] next = new int[n], cur = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            cur[i] = 1;
            for (int j = i + 1; j < n; j++) {
                cur[j] = s.charAt(i) == s.charAt(j) ? 2 + next[j - 1] : Math.max(next[j], cur[j - 1]);
            }
            int[] t = next; next = cur; cur = t;
        }
        return next[n - 1];
    }

    /** The classic trick: LPS(s) = LCS(s, reverse(s)). O(n²). */
    public static int viaLcs(String s) {
        String r = new StringBuilder(s).reverse().toString();
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                dp[i][j] = s.charAt(i - 1) == r.charAt(j - 1) ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
        return dp[n][n];
    }

    /** Follow-up: minimum insertions (or deletions) to make s a palindrome = n − LPS. */
    public static int minInsertionsToPalindrome(String s) {
        return s.length() - intervalDp(s);
    }
}
