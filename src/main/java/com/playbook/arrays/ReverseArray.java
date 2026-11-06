package com.playbook.arrays;

import java.util.stream.IntStream;

/** Reverse an int array in place (and a copy-returning streams version). */
public final class ReverseArray {
    private ReverseArray() {}

    /** Optimal: two pointers swap. O(n) time, O(1) space. */
    public static void inPlace(int[] a) {
        if (a == null) return;
        for (int l = 0, r = a.length - 1; l < r; l++, r--) {
            int t = a[l];
            a[l] = a[r];
            a[r] = t;
        }
    }

    /** Recursive: swap ends, recurse inward. O(n) time, O(n) stack. */
    public static void recursive(int[] a) {
        if (a != null) recursive(a, 0, a.length - 1);
    }

    private static void recursive(int[] a, int l, int r) {
        if (l >= r) return;
        int t = a[l];
        a[l] = a[r];
        a[r] = t;
        recursive(a, l + 1, r - 1);
    }

    /** Java 8: new reversed array. */
    public static int[] streams(int[] a) {
        if (a == null) return null;
        return IntStream.range(0, a.length).map(i -> a[a.length - 1 - i]).toArray();
    }
}
