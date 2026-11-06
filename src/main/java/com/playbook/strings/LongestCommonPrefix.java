package com.playbook.strings;

import java.util.Arrays;

/** Longest common prefix of an array of strings ("flower","flow","flight" → "fl"). Empty/null array → "". */
public final class LongestCommonPrefix {
    private LongestCommonPrefix() {}

    /** Vertical scan: compare column by column. O(S) where S = total chars. O(1) extra space. */
    public static String verticalScan(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        for (int col = 0; col < strs[0].length(); col++) {
            char c = strs[0].charAt(col);
            for (int row = 1; row < strs.length; row++) {
                if (col == strs[row].length() || strs[row].charAt(col) != c) return strs[0].substring(0, col);
            }
        }
        return strs[0];
    }

    /** Horizontal scan: shrink the prefix until each string starts with it. */
    public static String horizontalScan(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) prefix = prefix.substring(0, prefix.length() - 1);
        }
        return prefix;
    }

    /** Sort trick: after sorting, only first and last strings need comparing. O(S log n). */
    public static String sorting(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        String[] copy = strs.clone();
        Arrays.sort(copy);
        String first = copy[0], last = copy[copy.length - 1];
        int i = 0;
        while (i < first.length() && i < last.length() && first.charAt(i) == last.charAt(i)) i++;
        return first.substring(0, i);
    }

    /** Divide and conquer (recursive): LCP(left half) ∩ LCP(right half). */
    public static String divideAndConquer(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        return lcp(strs, 0, strs.length - 1);
    }

    private static String lcp(String[] s, int lo, int hi) {
        if (lo == hi) return s[lo];                          // base case: one string
        int mid = (lo + hi) >>> 1;
        String a = lcp(s, lo, mid), b = lcp(s, mid + 1, hi);
        int i = 0;
        while (i < a.length() && i < b.length() && a.charAt(i) == b.charAt(i)) i++;
        return a.substring(0, i);
    }
}
