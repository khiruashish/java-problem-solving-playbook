package com.playbook.strings;

import java.util.ArrayList;
import java.util.List;

/** Find all start indexes of {@code pattern} in {@code text} (overlaps allowed). Empty pattern → none. */
public final class KmpPatternSearch {
    private KmpPatternSearch() {}

    /** Naive: try every alignment. O(n · m) worst case ("aaaa…a" vs "aa…ab"). */
    public static List<Integer> naive(String text, String pattern) {
        List<Integer> hits = new ArrayList<>();
        if (text == null || pattern == null || pattern.isEmpty()) return hits;
        for (int i = 0; i + pattern.length() <= text.length(); i++) {
            int j = 0;
            while (j < pattern.length() && text.charAt(i + j) == pattern.charAt(j)) j++;
            if (j == pattern.length()) hits.add(i);
        }
        return hits;
    }

    /**
     * KMP: precompute lps[i] = length of the longest proper prefix of pattern[0..i] that is also a suffix.
     * On mismatch we fall back to lps instead of restarting, so the text pointer never moves back. O(n + m).
     */
    public static List<Integer> kmp(String text, String pattern) {
        List<Integer> hits = new ArrayList<>();
        if (text == null || pattern == null || pattern.isEmpty()) return hits;
        int[] lps = lps(pattern);
        int j = 0;                                           // chars of pattern currently matched
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) j = lps[j - 1];
            if (text.charAt(i) == pattern.charAt(j)) j++;
            if (j == pattern.length()) {
                hits.add(i - j + 1);
                j = lps[j - 1];                              // continue to find overlapping matches
            }
        }
        return hits;
    }

    /** Longest-proper-prefix-which-is-also-suffix table. "AABAACAABAA" → [0,1,0,1,2,0,1,2,3,4,5]. */
    public static int[] lps(String p) {
        int[] lps = new int[p.length()];
        int len = 0;
        for (int i = 1; i < p.length(); i++) {
            while (len > 0 && p.charAt(i) != p.charAt(len)) len = lps[len - 1];
            if (p.charAt(i) == p.charAt(len)) len++;
            lps[i] = len;
        }
        return lps;
    }

    /** Rabin–Karp rolling hash (alternative). Average O(n + m); verify on hash hit to avoid false positives. */
    public static List<Integer> rabinKarp(String text, String pattern) {
        List<Integer> hits = new ArrayList<>();
        if (text == null || pattern == null || pattern.isEmpty() || pattern.length() > text.length()) return hits;
        final long base = 256, mod = 1_000_000_007L;
        int m = pattern.length();
        long pHash = 0, tHash = 0, high = 1;
        for (int i = 0; i < m - 1; i++) high = high * base % mod;   // base^(m-1)
        for (int i = 0; i < m; i++) {
            pHash = (pHash * base + pattern.charAt(i)) % mod;
            tHash = (tHash * base + text.charAt(i)) % mod;
        }
        for (int i = 0; ; i++) {
            if (pHash == tHash && text.regionMatches(i, pattern, 0, m)) hits.add(i);
            if (i + m >= text.length()) break;
            tHash = (tHash - text.charAt(i) * high % mod + mod) % mod;   // drop left char
            tHash = (tHash * base + text.charAt(i + m)) % mod;           // add right char
        }
        return hits;
    }
}
