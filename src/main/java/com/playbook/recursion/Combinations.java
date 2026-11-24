package com.playbook.recursion;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/** All combinations of k numbers chosen from 1..n, each in ascending order, listed lexicographically. */
public final class Combinations {
    private Combinations() {}

    /** Backtracking with pruning: stop when too few numbers remain to fill the combination. O(C(n,k) · k). */
    public static List<List<Integer>> backtrack(int n, int k) {
        List<List<Integer>> out = new ArrayList<>();
        if (k < 0 || k > n) return out;
        backtrack(1, n, k, new ArrayDeque<>(), out);
        return out;
    }

    private static void backtrack(int start, int n, int k, Deque<Integer> path, List<List<Integer>> out) {
        if (path.size() == k) {                          // base case: a full combination
            out.add(new ArrayList<>(path));
            return;
        }
        int need = k - path.size();
        for (int x = start; x <= n - need + 1; x++) {   // prune: x must leave room for the remaining picks
            path.addLast(x);                             // choose
            backtrack(x + 1, n, k, path, out);           // explore
            path.removeLast();                           // un-choose
        }
    }

    /** Include / exclude recursion (the "subsets" tree, cut at size k). */
    public static List<List<Integer>> includeExclude(int n, int k) {
        List<List<Integer>> out = new ArrayList<>();
        if (k < 0 || k > n) return out;
        includeExclude(1, n, k, new ArrayList<>(), out);
        return out;
    }

    private static void includeExclude(int x, int n, int k, List<Integer> path, List<List<Integer>> out) {
        if (path.size() == k) {
            out.add(new ArrayList<>(path));
            return;
        }
        if (x > n || n - x + 1 < k - path.size()) return;   // not enough numbers left
        path.add(x);
        includeExclude(x + 1, n, k, path, out);                 // take x
        path.remove(path.size() - 1);
        includeExclude(x + 1, n, k, path, out);                 // skip x
    }

    /**
     * Iterative "odometer": keep k indices c[0] < c[1] < ... ; find the rightmost one that can still grow,
     * bump it, and reset everything to its right to consecutive values. Produces lexicographic order.
     */
    public static List<List<Integer>> iterative(int n, int k) {
        List<List<Integer>> out = new ArrayList<>();
        if (k < 0 || k > n) return out;
        int[] c = new int[k];
        for (int i = 0; i < k; i++) c[i] = i + 1;
        while (true) {
            List<Integer> combo = new ArrayList<>(k);
            for (int v : c) combo.add(v);
            out.add(combo);
            int i = k - 1;
            while (i >= 0 && c[i] == n - k + 1 + i) i--;      // c[i] already at its maximum
            if (i < 0) return out;
            c[i]++;
            for (int j = i + 1; j < k; j++) c[j] = c[j - 1] + 1;
        }
    }

    /** Bitmask brute force (n ≤ 20): every subset with exactly k bits set, then sorted lexicographically. */
    public static List<List<Integer>> bitmask(int n, int k) {
        List<List<Integer>> out = new ArrayList<>();
        if (k < 0 || k > n) return out;
        for (int mask = 0; mask < (1 << n); mask++) {
            if (Integer.bitCount(mask) != k) continue;
            List<Integer> combo = new ArrayList<>();
            for (int b = 0; b < n; b++) if ((mask & (1 << b)) != 0) combo.add(b + 1);
            out.add(combo);
        }
        out.sort((a, b) -> {
            for (int i = 0; i < a.size(); i++) {
                int c = Integer.compare(a.get(i), b.get(i));
                if (c != 0) return c;
            }
            return 0;
        });
        return out;
    }

    /** C(n, k) computed exactly (multiplicative formula; each prefix product is itself a binomial). */
    public static long count(int n, int k) {
        if (k < 0 || k > n) return 0;
        k = Math.min(k, n - k);
        long r = 1;
        for (int i = 1; i <= k; i++) r = r * (n - k + i) / i;
        return r;
    }
}
