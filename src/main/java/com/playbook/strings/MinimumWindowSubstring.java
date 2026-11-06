package com.playbook.strings;

/**
 * Smallest substring of {@code s} containing every char of {@code t} (with multiplicity).
 * s="ADOBECODEBANC", t="ABC" → "BANC". Returns "" if impossible.
 */
public final class MinimumWindowSubstring {
    private MinimumWindowSubstring() {}

    /** Brute force: every substring, check coverage. O(n^2 · alphabet). Fine for teaching, not for prod. */
    public static String bruteForce(String s, String t) {
        if (s == null || t == null || t.isEmpty()) return "";
        String best = "";
        for (int i = 0; i < s.length(); i++) {
            int[] need = counts(t);
            int missing = t.length();
            for (int j = i; j < s.length(); j++) {
                if (need[s.charAt(j)]-- > 0) missing--;
                if (missing == 0) {
                    if (best.isEmpty() || j - i + 1 < best.length()) best = s.substring(i, j + 1);
                    break;                                   // longer j from same i can't be better
                }
            }
        }
        return best;
    }

    /**
     * Optimal: variable sliding window. Expand right until window covers t, then shrink left while it
     * still covers, recording the minimum. Each index enters and leaves once → O(|s| + |t|).
     */
    public static String optimal(String s, String t) {
        if (s == null || t == null || t.isEmpty() || t.length() > s.length()) return "";
        int[] need = counts(t);                              // need[c] > 0 → still missing c
        int missing = t.length(), left = 0, bestStart = 0, bestLen = Integer.MAX_VALUE;
        for (int right = 0; right < s.length(); right++) {
            if (need[s.charAt(right)]-- > 0) missing--;      // this char was useful
            while (missing == 0) {                           // window valid → try to shrink
                if (right - left + 1 < bestLen) {
                    bestLen = right - left + 1;
                    bestStart = left;
                }
                if (++need[s.charAt(left++)] > 0) missing++; // removed a required char
            }
        }
        return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLen);
    }

    private static int[] counts(String t) {
        int[] need = new int[Character.MAX_VALUE + 1];
        for (char c : t.toCharArray()) need[c]++;
        return need;
    }
}
