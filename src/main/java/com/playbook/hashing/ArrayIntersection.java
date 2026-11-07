package com.playbook.hashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Intersection of two arrays.
 * {@link #unique}: each common value once (LeetCode 349). {@link #withCounts}: as many times as it appears in BOTH (LeetCode 350).
 * Results are sorted so they are easy to compare.
 */
public final class ArrayIntersection {
    private ArrayIntersection() {}

    /** Brute force: for each value of a, linear search in b. O(n·m). */
    public static int[] bruteForceUnique(int[] a, int[] b) {
        Set<Integer> out = new HashSet<>();
        for (int x : a)
            for (int y : b)
                if (x == y) {
                    out.add(x);
                    break;
                }
        return sorted(out);
    }

    /** Set of a, then keep values of b that are in it. O(n + m). */
    public static int[] unique(int[] a, int[] b) {
        Set<Integer> setA = new HashSet<>();
        for (int x : a) setA.add(x);
        Set<Integer> out = new HashSet<>();
        for (int y : b) if (setA.contains(y)) out.add(y);
        return sorted(out);
    }

    /** Library: Set.retainAll does exactly "keep only what is also in the other collection". */
    public static int[] retainAll(int[] a, int[] b) {
        Set<Integer> setA = new HashSet<>(Arrays.stream(a).boxed().toList());
        setA.retainAll(Arrays.stream(b).boxed().toList());
        return sorted(setA);
    }

    /** With multiplicity: count a, then consume counts while walking b. O(n + m). */
    public static int[] withCounts(int[] a, int[] b) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int x : a) count.merge(x, 1, Integer::sum);
        List<Integer> out = new ArrayList<>();
        for (int y : b) {
            int c = count.getOrDefault(y, 0);
            if (c > 0) {
                out.add(y);
                count.put(y, c - 1);                         // each copy in a can be used once
            }
        }
        return out.stream().mapToInt(Integer::intValue).sorted().toArray();
    }

    /** Both arrays already sorted: two pointers, O(1) extra space. Follow-up interviewers love. */
    public static int[] sortedTwoPointers(int[] a, int[] b) {
        List<Integer> out = new ArrayList<>();
        int i = 0, j = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) i++;
            else if (a[i] > b[j]) j++;
            else {
                out.add(a[i]);
                i++;
                j++;
            }
        }
        return out.stream().mapToInt(Integer::intValue).toArray();
    }

    private static int[] sorted(Set<Integer> s) {
        return s.stream().mapToInt(Integer::intValue).sorted().toArray();
    }
}
