package com.playbook.dp;

/** Minimum insert/delete/replace operations to turn a into b ("horse" → "ros" = 3). Levenshtein distance. */
public final class EditDistance {
    private EditDistance() {}

    /** Recursion on prefixes. O(3^(m+n)). */
    public static int recursive(String a, String b) {
        return rec(a, b, a.length(), b.length());
    }

    private static int rec(String a, String b, int i, int j) {
        if (i == 0) return j;                                // insert the remaining j chars
        if (j == 0) return i;                                // delete the remaining i chars
        if (a.charAt(i - 1) == b.charAt(j - 1)) return rec(a, b, i - 1, j - 1);   // free match
        return 1 + Math.min(rec(a, b, i - 1, j - 1),        // replace
                Math.min(rec(a, b, i - 1, j),                // delete from a
                        rec(a, b, i, j - 1)));               // insert into a
    }

    /** Table dp[i][j] = distance between a[0..i) and b[0..j). O(m·n). */
    public static int table(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                dp[i][j] = a.charAt(i - 1) == b.charAt(j - 1) ? dp[i - 1][j - 1]
                        : 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
        return dp[m][n];
    }

    /** One row + a saved diagonal. O(m·n) time, O(n) space. */
    public static int oneRow(String a, String b) {
        int n = b.length();
        int[] row = new int[n + 1];
        for (int j = 0; j <= n; j++) row[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            int diag = row[0];                               // dp[i-1][j-1]
            row[0] = i;
            for (int j = 1; j <= n; j++) {
                int above = row[j];                          // dp[i-1][j], about to be overwritten
                row[j] = a.charAt(i - 1) == b.charAt(j - 1) ? diag : 1 + Math.min(diag, Math.min(above, row[j - 1]));
                diag = above;
            }
        }
        return row[n];
    }
}
