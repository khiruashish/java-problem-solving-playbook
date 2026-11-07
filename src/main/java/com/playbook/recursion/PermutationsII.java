package com.playbook.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** All DISTINCT permutations of an int array that may contain duplicates, in lexicographic order. [1,1,2] → 3. */
public final class PermutationsII {
    private PermutationsII() {}

    /** Generate all n! then dedupe with a set (wasteful: [1,1,1,1,1,1] builds 720 lists for 1 answer). */
    public static List<List<Integer>> withSet(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        Set<List<Integer>> seen = new LinkedHashSet<>();
        all(a, new boolean[a.length], new ArrayList<>(), seen);
        return new ArrayList<>(seen);
    }

    private static void all(int[] a, boolean[] used, List<Integer> cur, Set<List<Integer>> out) {
        if (cur.size() == a.length) {
            out.add(new ArrayList<>(cur));
            return;
        }
        for (int i = 0; i < a.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            cur.add(a[i]);
            all(a, used, cur, out);
            cur.remove(cur.size() - 1);
            used[i] = false;
        }
    }

    /**
     * Pruned backtracking: sort, and at each position skip a value equal to its left neighbour unless that neighbour
     * is currently in use. Equal values are then always placed in their original order → each distinct permutation once.
     */
    public static List<List<Integer>> pruned(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        List<List<Integer>> out = new ArrayList<>();
        prune(a, new boolean[a.length], new ArrayList<>(), out);
        return out;
    }

    private static void prune(int[] a, boolean[] used, List<Integer> cur, List<List<Integer>> out) {
        if (cur.size() == a.length) {
            out.add(new ArrayList<>(cur));
            return;
        }
        for (int i = 0; i < a.length; i++) {
            if (used[i]) continue;
            if (i > 0 && a[i] == a[i - 1] && !used[i - 1]) continue;   // the twin to the left must go first
            used[i] = true;
            cur.add(a[i]);
            prune(a, used, cur, out);
            cur.remove(cur.size() - 1);
            used[i] = false;
        }
    }
}
