package com.playbook.strings;

/** Longest palindromic substring ("babad" → "bab" or "aba"; we return the first found). */
public final class LongestPalindromicSubstring {
    private LongestPalindromicSubstring() {}

    /** Brute force: check all O(n^2) substrings, each in O(n). O(n^3). */
    public static String bruteForce(String s) {
        if (s == null || s.isEmpty()) return "";
        String best = s.substring(0, 1);
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + best.length(); j < s.length(); j++) {   // only longer candidates matter
                if (Palindrome.isPalindrome(s.substring(i, j + 1))) best = s.substring(i, j + 1);
            }
        }
        return best;
    }

    /** Dynamic programming: dp[i][j] = s[i]==s[j] && dp[i+1][j-1]. O(n^2) time and space. */
    public static String dynamicProgramming(String s) {
        if (s == null || s.isEmpty()) return "";
        int n = s.length(), start = 0, len = 1;
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {                   // i goes down so dp[i+1] is ready
            for (int j = i; j < n; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1]);
                if (dp[i][j] && j - i + 1 > len) {
                    start = i;
                    len = j - i + 1;
                }
            }
        }
        return s.substring(start, start + len);
    }

    /** Optimal in practice: expand around each of the 2n-1 centres. O(n^2) time, O(1) space. */
    public static String expandAroundCenter(String s) {
        if (s == null || s.isEmpty()) return "";
        int start = 0, end = 0;
        for (int c = 0; c < s.length(); c++) {
            int odd = expand(s, c, c), even = expand(s, c, c + 1);
            int len = Math.max(odd, even);
            if (len > end - start + 1) {
                start = c - (len - 1) / 2;
                end = c + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private static int expand(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return r - l - 1;                                    // length of the palindrome found
    }

    /** Expert: Manacher's algorithm. O(n) time. Works on "^#a#b#a#$" to unify odd/even. */
    public static String manacher(String s) {
        if (s == null || s.isEmpty()) return "";
        StringBuilder t = new StringBuilder("^");
        for (char c : s.toCharArray()) t.append('#').append(c);
        t.append("#$");
        int[] p = new int[t.length()];
        int center = 0, right = 0;
        for (int i = 1; i < t.length() - 1; i++) {
            if (i < right) p[i] = Math.min(right - i, p[2 * center - i]);   // reuse mirror's radius
            while (t.charAt(i + 1 + p[i]) == t.charAt(i - 1 - p[i])) p[i]++;
            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }
        }
        int bestLen = 0, bestCenter = 0;
        for (int i = 1; i < p.length - 1; i++) {
            if (p[i] > bestLen) {
                bestLen = p[i];
                bestCenter = i;
            }
        }
        int start = (bestCenter - bestLen) / 2;              // map back to original indexes
        return s.substring(start, start + bestLen);
    }
}
