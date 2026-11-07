package com.playbook.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * I  (LeetCode 39): distinct candidates, each reusable, all combinations summing to target.
 * II (LeetCode 40): candidates may repeat, each used at most once, no duplicate combinations.
 * Combinations are non-decreasing lists; the result is in lexicographic order.
 */
public final class CombinationSum {
    private CombinationSum() {}

    public static List<List<Integer>> reusable(int[] candidates, int target) {
        int[] c = candidates.clone();
        Arrays.sort(c);                                      // sorting enables the "break" prune below
        List<List<Integer>> out = new ArrayList<>();
        reuse(c, 0, target, new ArrayList<>(), out);
        return out;
    }

    private static void reuse(int[] c, int start, int remaining, List<Integer> cur, List<List<Integer>> out) {
        if (remaining == 0) {
            out.add(new ArrayList<>(cur));
            return;
        }
        for (int i = start; i < c.length; i++) {
            if (c[i] > remaining) break;                     // sorted: every later candidate is too big as well
            cur.add(c[i]);
            reuse(c, i, remaining - c[i], cur, out);         // i, not i+1: the same number may be used again
            cur.remove(cur.size() - 1);
        }
    }

    public static List<List<Integer>> onceEach(int[] candidates, int target) {
        int[] c = candidates.clone();
        Arrays.sort(c);
        List<List<Integer>> out = new ArrayList<>();
        once(c, 0, target, new ArrayList<>(), out);
        return out;
    }

    private static void once(int[] c, int start, int remaining, List<Integer> cur, List<List<Integer>> out) {
        if (remaining == 0) {
            out.add(new ArrayList<>(cur));
            return;
        }
        for (int i = start; i < c.length; i++) {
            if (i > start && c[i] == c[i - 1]) continue;     // same value at the same depth → duplicate combination
            if (c[i] > remaining) break;
            cur.add(c[i]);
            once(c, i + 1, remaining - c[i], cur, out);      // i+1: each element at most once
            cur.remove(cur.size() - 1);
        }
    }
}
