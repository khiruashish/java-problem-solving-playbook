package com.playbook.arrays;

import java.util.Arrays;
import java.util.IntSummaryStatistics;

/** Find {min, max} of a non-empty int array. Throws IllegalArgumentException on null/empty. */
public final class MaxMin {
    private MaxMin() {}

    /** Sort then take ends. O(n log n) — mutates a copy only. */
    public static int[] sorting(int[] a) {
        requireNonEmpty(a);
        int[] c = a.clone();
        Arrays.sort(c);
        return new int[]{c[0], c[c.length - 1]};
    }

    /** Optimal: one linear scan. O(n) time, O(1) space, ~2n comparisons. */
    public static int[] linear(int[] a) {
        requireNonEmpty(a);
        int min = a[0], max = a[0];
        for (int x : a) {
            if (x < min) min = x;
            if (x > max) max = x;
        }
        return new int[]{min, max};
    }

    /** Pairwise comparison: ~1.5n comparisons (classic GfG "minimum comparisons" answer). */
    public static int[] pairwise(int[] a) {
        requireNonEmpty(a);
        int i, min, max;
        if (a.length % 2 == 0) {
            min = Math.min(a[0], a[1]);
            max = Math.max(a[0], a[1]);
            i = 2;
        } else {
            min = max = a[0];
            i = 1;
        }
        for (; i < a.length; i += 2) {                       // compare the pair first, then 1 vs min, 1 vs max
            int small = Math.min(a[i], a[i + 1]), big = Math.max(a[i], a[i + 1]);
            if (small < min) min = small;
            if (big > max) max = big;
        }
        return new int[]{min, max};
    }

    /** Recursive (divide & conquer): min/max of halves. O(n) time, O(log n) stack. */
    public static int[] recursive(int[] a) {
        requireNonEmpty(a);
        return dc(a, 0, a.length - 1);
    }

    private static int[] dc(int[] a, int lo, int hi) {
        if (lo == hi) return new int[]{a[lo], a[lo]};
        int mid = (lo + hi) >>> 1;
        int[] l = dc(a, lo, mid), r = dc(a, mid + 1, hi);
        return new int[]{Math.min(l[0], r[0]), Math.max(l[1], r[1])};
    }

    /** Java 8: IntSummaryStatistics gives min, max, sum, avg, count in one pass. */
    public static int[] streams(int[] a) {
        requireNonEmpty(a);
        IntSummaryStatistics s = Arrays.stream(a).summaryStatistics();
        return new int[]{s.getMin(), s.getMax()};
    }

    private static void requireNonEmpty(int[] a) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("array must be non-empty");
    }
}
