package com.playbook.dp;

/** Longest common subsequence of two strings ("abcde", "ace" → 3, "ace"). */
public final class LongestCommonSubsequence {
    private LongestCommonSubsequence() {}

    /** Recursion on prefixes: last chars equal → 1 + lcs(both shorter); else max(drop one from either). O(2^(m+n)). */
    public static int recursive(String a, String b) {
        return rec(a, b, a.length(), b.length());
    }

    private static int rec(String a, String b, int i, int j) {
        if (i == 0 || j == 0) return 0;
        if (a.charAt(i - 1) == b.charAt(j - 1)) return 1 + rec(a, b, i - 1, j - 1);
        return Math.max(rec(a, b, i - 1, j), rec(a, b, i, j - 1));
    }

    /** Memoized. O(m·n). */
    public static int memo(String a, String b) {
        Integer[][] cache = new Integer[a.length() + 1][b.length() + 1];
        return memo(a, b, a.length(), b.length(), cache);
    }

    private static int memo(String a, String b, int i, int j, Integer[][] cache) {
        if (i == 0 || j == 0) return 0;
        if (cache[i][j] != null) return cache[i][j];
        int r = a.charAt(i - 1) == b.charAt(j - 1) ? 1 + memo(a, b, i - 1, j - 1, cache)
                : Math.max(memo(a, b, i - 1, j, cache), memo(a, b, i, j - 1, cache));
        return cache[i][j] = r;
    }

    /** Table dp[i][j] = LCS of a[0..i) and b[0..j). O(m·n). */
    public static int[][] table(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                dp[i][j] = a.charAt(i - 1) == b.charAt(j - 1) ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
        return dp;
    }

    public static int length(String a, String b) {
        return table(a, b)[a.length()][b.length()];
    }

    /** Reconstruct one LCS by walking the table back from the bottom-right corner. */
    public static String sequence(String a, String b) {
        int[][] dp = table(a, b);
        StringBuilder sb = new StringBuilder();
        for (int i = a.length(), j = b.length(); i > 0 && j > 0; ) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                sb.append(a.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] >= dp[i][j - 1]) i--;
            else j--;
        }
        return sb.reverse().toString();
    }

    /** Two rows instead of a full table: O(min(m,n)) space when only the length is needed. */
    public static int twoRows(String a, String b) {
        if (b.length() > a.length()) return twoRows(b, a);
        int[] prev = new int[b.length() + 1], cur = new int[b.length() + 1];
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++)
                cur[j] = a.charAt(i - 1) == b.charAt(j - 1) ? prev[j - 1] + 1 : Math.max(prev[j], cur[j - 1]);
            int[] t = prev;
            prev = cur;
            cur = t;
        }
        return prev[b.length()];
    }
}
