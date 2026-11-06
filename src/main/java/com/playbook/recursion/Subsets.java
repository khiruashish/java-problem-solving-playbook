package com.playbook.recursion;

import java.util.ArrayList;
import java.util.List;

/** All 2^n subsets (power set) of distinct ints. */
public final class Subsets {
    private Subsets() {}

    /** Recursive "pick / don't pick" — the binary recursion tree. */
    public static List<List<Integer>> pickOrSkip(int[] a) {
        List<List<Integer>> out = new ArrayList<>();
        pick(a, 0, new ArrayList<>(), out);
        return out;
    }

    private static void pick(int[] a, int i, List<Integer> current, List<List<Integer>> out) {
        if (i == a.length) {                                 // leaf: one decision made per element
            out.add(new ArrayList<>(current));
            return;
        }
        pick(a, i + 1, current, out);                        // skip a[i]
        current.add(a[i]);
        pick(a, i + 1, current, out);                        // pick a[i]
        current.remove(current.size() - 1);                  // backtrack
    }

    /** Backtracking with start index (every node is an answer) — the template reused for combinations. */
    public static List<List<Integer>> backtracking(int[] a) {
        List<List<Integer>> out = new ArrayList<>();
        bt(a, 0, new ArrayList<>(), out);
        return out;
    }

    private static void bt(int[] a, int start, List<Integer> current, List<List<Integer>> out) {
        out.add(new ArrayList<>(current));
        for (int i = start; i < a.length; i++) {
            current.add(a[i]);
            bt(a, i + 1, current, out);
            current.remove(current.size() - 1);
        }
    }

    /** Iterative cascading: start with [[]], for each x add x to every existing subset. */
    public static List<List<Integer>> iterative(int[] a) {
        List<List<Integer>> out = new ArrayList<>();
        out.add(new ArrayList<>());
        for (int x : a) {
            int size = out.size();
            for (int i = 0; i < size; i++) {
                List<Integer> copy = new ArrayList<>(out.get(i));
                copy.add(x);
                out.add(copy);
            }
        }
        return out;
    }

    /** Bitmask: number mask in [0, 2^n) — bit j set means include a[j]. */
    public static List<List<Integer>> bitmask(int[] a) {
        List<List<Integer>> out = new ArrayList<>();
        for (int mask = 0; mask < (1 << a.length); mask++) {
            List<Integer> s = new ArrayList<>();
            for (int j = 0; j < a.length; j++) if ((mask & (1 << j)) != 0) s.add(a[j]);
            out.add(s);
        }
        return out;
    }
}
