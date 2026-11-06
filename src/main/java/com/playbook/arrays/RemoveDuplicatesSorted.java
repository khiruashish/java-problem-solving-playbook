package com.playbook.arrays;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/** Remove duplicates from a SORTED array in place; return the new length k (first k slots hold uniques). */
public final class RemoveDuplicatesSorted {
    private RemoveDuplicatesSorted() {}

    /** Set-based (works unsorted too, but O(n) extra space). */
    public static int withSet(int[] a) {
        Set<Integer> seen = new LinkedHashSet<>();
        for (int x : a) seen.add(x);
        int k = 0;
        for (int x : seen) a[k++] = x;
        return k;
    }

    /** Optimal: slow/fast pointers — sorted means duplicates are adjacent. O(n), O(1). */
    public static int optimal(int[] a) {
        if (a.length == 0) return 0;
        int k = 1;                                           // a[0..k-1] are unique
        for (int i = 1; i < a.length; i++) {
            if (a[i] != a[k - 1]) a[k++] = a[i];
        }
        return k;
    }

    /** Variation (LeetCode 80): allow each value at most twice. */
    public static int atMostTwice(int[] a) {
        int k = 0;
        for (int x : a) if (k < 2 || x != a[k - 2]) a[k++] = x;
        return k;
    }

    /** Java 8: returns a new array of distinct values. */
    public static int[] streams(int[] a) {
        return Arrays.stream(a).distinct().toArray();
    }
}
