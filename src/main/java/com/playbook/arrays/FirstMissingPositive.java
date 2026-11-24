package com.playbook.arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** Smallest positive integer that is NOT in the array. Required: O(n) time and O(1) extra space. */
public final class FirstMissingPositive {
    private FirstMissingPositive() {}

    /** Brute force: try 1, 2, 3, ... with a linear scan each. O(n²). */
    public static int bruteForce(int[] a) {
        for (int k = 1; ; k++) {
            boolean found = false;
            for (int x : a) if (x == k) { found = true; break; }
            if (!found) return k;
        }
    }

    /** Hash set. O(n) time, O(n) space. The answer is at most n + 1, so the loop is bounded. */
    public static int hashing(int[] a) {
        Set<Integer> set = new HashSet<>();
        for (int x : a) set.add(x);
        int k = 1;
        while (set.contains(k)) k++;
        return k;
    }

    /** Sorting, then walk the positives. O(n log n) time. */
    public static int sorting(int[] a) {
        int[] b = a.clone();
        Arrays.sort(b);
        int expect = 1;
        for (int x : b) {
            if (x == expect) expect++;
            else if (x > expect) break;               // gap found
        }
        return expect;
    }

    /**
     * Optimal — cyclic sort: the answer is in [1, n+1], so value v belongs at index v-1. Swap every in-range value
     * to its home; then the first index i with a[i] != i+1 gives i+1. Each swap fixes one value permanently, so
     * there are at most n swaps: O(n) time, O(1) extra space. Mutates its input.
     */
    public static int cyclicSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            // keep swapping a[i] home while it is in range and its home doesn't already hold it (handles duplicates)
            while (a[i] >= 1 && a[i] <= n && a[a[i] - 1] != a[i]) {
                int home = a[i] - 1;
                int t = a[home];
                a[home] = a[i];
                a[i] = t;
            }
        }
        for (int i = 0; i < n; i++) if (a[i] != i + 1) return i + 1;
        return n + 1;
    }

    /**
     * Alternative O(1) space: index-as-hash with sign marking. First replace non-positives / out-of-range by n+1,
     * then for each value v in [1, n] make a[v-1] negative. The first positive slot is missing. Mutates its input.
     */
    public static int signMarking(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) if (a[i] <= 0 || a[i] > n) a[i] = n + 1;
        for (int i = 0; i < n; i++) {
            int v = Math.abs(a[i]);
            if (v <= n && a[v - 1] > 0) a[v - 1] = -a[v - 1];
        }
        for (int i = 0; i < n; i++) if (a[i] > 0) return i + 1;
        return n + 1;
    }
}
