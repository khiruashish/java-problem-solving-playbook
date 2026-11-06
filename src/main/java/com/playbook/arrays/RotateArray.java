package com.playbook.arrays;

/** Rotate array to the RIGHT by k steps in place. [1..7], k=3 → [5,6,7,1,2,3,4]. */
public final class RotateArray {
    private RotateArray() {}

    /** Brute force: rotate by one, k times. O(n·k). */
    public static void bruteForce(int[] a, int k) {
        if (a.length == 0) return;
        k %= a.length;
        for (int step = 0; step < k; step++) {
            int last = a[a.length - 1];
            System.arraycopy(a, 0, a, 1, a.length - 1);
            a[0] = last;
        }
    }

    /** Extra array: element i goes to (i+k) % n. O(n), O(n). */
    public static void extraArray(int[] a, int k) {
        int n = a.length;
        if (n == 0) return;
        int[] out = new int[n];
        for (int i = 0; i < n; i++) out[(i + k) % n] = a[i];
        System.arraycopy(out, 0, a, 0, n);
    }

    /** Optimal: reverse all, reverse first k, reverse rest. O(n), O(1). */
    public static void reversal(int[] a, int k) {
        int n = a.length;
        if (n == 0) return;
        k %= n;                                              // k may exceed n
        reverse(a, 0, n - 1);
        reverse(a, 0, k - 1);
        reverse(a, k, n - 1);
    }

    /** Left rotation by d is right rotation by n - d. */
    public static void rotateLeft(int[] a, int d) {
        if (a.length == 0) return;
        reversal(a, a.length - d % a.length);
    }

    private static void reverse(int[] a, int l, int r) {
        while (l < r) {
            int t = a[l];
            a[l++] = a[r];
            a[r--] = t;
        }
    }
}
