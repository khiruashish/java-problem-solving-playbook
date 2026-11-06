package com.playbook.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/** All permutations of a string. "abc" → abc, acb, bac, bca, cab, cba. n! results. */
public final class StringPermutations {
    private StringPermutations() {}

    /** Recursive (prefix + remaining): pick each char as next, recurse on what is left. */
    public static List<String> recursive(String s) {
        List<String> out = new ArrayList<>();
        if (s != null) permute("", s, out);
        return out;
    }

    private static void permute(String prefix, String rest, List<String> out) {
        if (rest.isEmpty()) {                                // base case: nothing left to place
            out.add(prefix);
            return;
        }
        for (int i = 0; i < rest.length(); i++) {
            permute(prefix + rest.charAt(i), rest.substring(0, i) + rest.substring(i + 1), out);
        }
    }

    /** Backtracking with swaps on a char[] (no substring garbage). Distinct results via a sorted set. */
    public static Set<String> distinctBacktracking(String s) {
        Set<String> out = new TreeSet<>();
        if (s != null) swapPermute(s.toCharArray(), 0, out);
        return out;
    }

    private static void swapPermute(char[] a, int k, Set<String> out) {
        if (k == a.length) {
            out.add(new String(a));
            return;
        }
        for (int i = k; i < a.length; i++) {
            swap(a, k, i);                                   // choose
            swapPermute(a, k + 1, out);                      // explore
            swap(a, k, i);                                   // un-choose (backtrack)
        }
    }

    /** Iterative "normal" solution: next lexicographic permutation, repeated (handles duplicates). */
    public static List<String> iterative(String s) {
        List<String> out = new ArrayList<>();
        if (s == null) return out;
        char[] a = s.toCharArray();
        Arrays.sort(a);
        do {
            out.add(new String(a));
        } while (nextPermutation(a));
        return out;
    }

    private static boolean nextPermutation(char[] a) {
        int i = a.length - 2;
        while (i >= 0 && a[i] >= a[i + 1]) i--;              // find first descent from the right
        if (i < 0) return false;                             // already the last permutation
        int j = a.length - 1;
        while (a[j] <= a[i]) j--;
        swap(a, i, j);
        for (int l = i + 1, r = a.length - 1; l < r; l++, r--) swap(a, l, r);
        return true;
    }

    private static void swap(char[] a, int i, int j) {
        char t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
