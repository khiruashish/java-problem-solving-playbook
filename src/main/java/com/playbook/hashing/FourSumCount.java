package com.playbook.hashing;

import java.util.HashMap;
import java.util.Map;

/** Count tuples (i, j, k, l) with a[i] + b[j] + c[k] + d[l] == 0 across four equal-length arrays (LeetCode 454). */
public final class FourSumCount {
    private FourSumCount() {}

    /** Brute force: four nested loops. O(n^4). */
    public static int bruteForce(int[] a, int[] b, int[] c, int[] d) {
        int count = 0;
        for (int x : a) for (int y : b) for (int z : c) for (int w : d) if ((long) x + y + z + w == 0) count++;
        return count;
    }

    /** Hash one array: three loops + lookup. O(n^3). A useful middle step when explaining. */
    public static int threeLoops(int[] a, int[] b, int[] c, int[] d) {
        Map<Integer, Integer> dCount = new HashMap<>();
        for (int w : d) dCount.merge(w, 1, Integer::sum);
        int count = 0;
        for (int x : a) for (int y : b) for (int z : c) count += dCount.getOrDefault(-(x + y + z), 0);
        return count;
    }

    /**
     * Optimal meet-in-the-middle: count every pair sum of (a, b), then for each pair sum of (c, d) look up its
     * negation. O(n^2) time, O(n^2) space.
     */
    public static int meetInTheMiddle(int[] a, int[] b, int[] c, int[] d) {
        Map<Integer, Integer> abSums = new HashMap<>();
        for (int x : a) for (int y : b) abSums.merge(x + y, 1, Integer::sum);
        int count = 0;
        for (int z : c) for (int w : d) count += abSums.getOrDefault(-(z + w), 0);
        return count;
    }
}
