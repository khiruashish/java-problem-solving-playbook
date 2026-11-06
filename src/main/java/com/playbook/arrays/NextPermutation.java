package com.playbook.arrays;

/** Rearrange to the next lexicographically greater permutation; if none, the smallest (sorted). In place. */
public final class NextPermutation {
    private NextPermutation() {}

    /**
     * 1. From the right, find the first i with a[i] < a[i+1] (the "pivot").
     * 2. If none → array is fully descending → reverse whole array.
     * 3. From the right, find first j with a[j] > a[i]; swap a[i], a[j].
     * 4. Reverse the suffix a[i+1..] (it was descending, now ascending = smallest).
     * O(n) time, O(1) space.
     */
    public static void next(int[] a) {
        int i = a.length - 2;
        while (i >= 0 && a[i] >= a[i + 1]) i--;
        if (i >= 0) {
            int j = a.length - 1;
            while (a[j] <= a[i]) j--;
            swap(a, i, j);
        }
        for (int l = i + 1, r = a.length - 1; l < r; l++, r--) swap(a, l, r);
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
