package com.playbook.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * Palindrome partitioning: every way to cut s into pieces that are all palindromes (LeetCode 131),
 * plus the follow-up: the minimum number of cuts (LeetCode 132).
 * Partitions are listed in the natural DFS order: shorter first piece before longer.
 */
public final class PalindromePartitioning {
    private PalindromePartitioning() {}

    /** Backtracking with an O(len) palindrome check per candidate piece. O(n · 2ⁿ). */
    public static List<List<String>> backtrack(String s) {
        List<List<String>> out = new ArrayList<>();
        dfs(s, 0, new ArrayList<>(), out, null);
        return out;
    }

    /**
     * Same search, but isPal[i][j] is precomputed in O(n²) (expand from shorter to longer), so each check is O(1).
     * isPal[i][j] = s[i] == s[j] && (j - i < 2 || isPal[i+1][j-1]).
     */
    public static List<List<String>> backtrackWithTable(String s) {
        List<List<String>> out = new ArrayList<>();
        dfs(s, 0, new ArrayList<>(), out, palindromeTable(s));
        return out;
    }

    private static void dfs(String s, int start, List<String> path, List<List<String>> out, boolean[][] isPal) {
        if (start == s.length()) {                         // base case: the whole string is cut
            out.add(new ArrayList<>(path));
            return;
        }
        for (int end = start; end < s.length(); end++) {   // try every first piece s[start..end]
            boolean ok = isPal != null ? isPal[start][end] : isPalindrome(s, start, end);
            if (!ok) continue;                             // prune: this cut can never be valid
            path.add(s.substring(start, end + 1));
            dfs(s, end + 1, path, out, isPal);
            path.remove(path.size() - 1);
        }
    }

    /** Brute force for tests: each of the n-1 gaps is cut or not (2^(n-1) masks); keep the all-palindrome ones. */
    public static List<List<String>> bruteForce(String s) {
        List<List<String>> out = new ArrayList<>();
        if (s.isEmpty()) {
            out.add(new ArrayList<>());
            return out;
        }
        int gaps = s.length() - 1;
        for (int mask = 0; mask < (1 << gaps); mask++) {
            List<String> parts = new ArrayList<>();
            int from = 0;
            boolean ok = true;
            for (int g = 0; g <= gaps && ok; g++) {
                if (g == gaps || (mask & (1 << g)) != 0) {
                    ok = isPalindrome(s, from, g);
                    parts.add(s.substring(from, g + 1));
                    from = g + 1;
                }
            }
            if (ok) out.add(parts);
        }
        return out;
    }

    /** Follow-up: minimum cuts. cuts[j] = min over palindromic s[i..j] of cuts[i-1] + 1. O(n²) time. */
    public static int minCuts(String s) {
        int n = s.length();
        if (n == 0) return 0;
        boolean[][] isPal = palindromeTable(s);
        int[] cuts = new int[n];                           // cuts[j] = min cuts for s[0..j]
        for (int j = 0; j < n; j++) {
            if (isPal[0][j]) { cuts[j] = 0; continue; }    // whole prefix is a palindrome
            cuts[j] = j;                                   // worst case: cut after every char
            for (int i = 1; i <= j; i++) if (isPal[i][j]) cuts[j] = Math.min(cuts[j], cuts[i - 1] + 1);
        }
        return cuts[n - 1];
    }

    static boolean[][] palindromeTable(String s) {
        int n = s.length();
        boolean[][] p = new boolean[n][n];
        for (int len = 1; len <= n; len++)
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                p[i][j] = s.charAt(i) == s.charAt(j) && (len <= 2 || p[i + 1][j - 1]);
            }
        return p;
    }

    private static boolean isPalindrome(String s, int i, int j) {
        while (i < j) if (s.charAt(i++) != s.charAt(j--)) return false;
        return true;
    }
}
