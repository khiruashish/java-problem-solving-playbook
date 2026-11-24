package com.playbook.strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Z-algorithm. z[i] = length of the longest substring starting at i that is also a prefix of s (z[0] := 0).
 * Pattern search: build Z of pattern + '\u0000' + text; every z == m marks an occurrence.
 */
public final class ZAlgorithm {
    private ZAlgorithm() {}

    /** Brute force Z array: compare from every i. O(n²) worst case ("aaaa…"). */
    public static int[] zBrute(String s) {
        int n = s.length();
        int[] z = new int[n];
        for (int i = 1; i < n; i++) {
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;
        }
        return z;
    }

    /**
     * Linear Z array. [l, r) is the rightmost "Z-box": s[l..r) equals the prefix s[0..r-l).
     * Inside the box, z[i] starts from the mirrored value z[i-l] (capped at the box edge), so each char past r is
     * compared once overall. O(n) time.
     */
    public static int[] z(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;
        for (int i = 1; i < n; i++) {
            if (i < r) z[i] = Math.min(r - i, z[i - l]);              // reuse what the box already proved
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;  // extend past the box
            if (i + z[i] > r) {                                       // new rightmost box
                l = i;
                r = i + z[i];
            }
        }
        return z;
    }

    /** All start indexes of pattern in text (overlaps included), via Z of pattern + sentinel + text. O(n + m). */
    public static List<Integer> search(String text, String pattern) {
        List<Integer> out = new ArrayList<>();
        if (pattern.isEmpty() || pattern.length() > text.length()) return out;
        String joined = pattern + '\u0000' + text;                    // sentinel never matches, so z ≤ m
        int[] z = z(joined);
        int m = pattern.length();
        for (int i = m + 1; i < joined.length(); i++) if (z[i] == m) out.add(i - m - 1);
        return out;
    }

    /** Naive search, used as the oracle in tests. O(n·m). */
    public static List<Integer> searchBrute(String text, String pattern) {
        List<Integer> out = new ArrayList<>();
        if (pattern.isEmpty()) return out;
        for (int i = 0; i + pattern.length() <= text.length(); i++) if (text.startsWith(pattern, i)) out.add(i);
        return out;
    }
}
