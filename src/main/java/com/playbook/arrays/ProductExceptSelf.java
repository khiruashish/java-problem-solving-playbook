package com.playbook.arrays;

/** out[i] = product of all elements except a[i], WITHOUT division, in O(n). */
public final class ProductExceptSelf {
    private ProductExceptSelf() {}

    /** Brute force: nested loop. O(n^2). */
    public static int[] bruteForce(int[] a) {
        int[] out = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            int p = 1;
            for (int j = 0; j < a.length; j++) if (i != j) p *= a[j];
            out[i] = p;
        }
        return out;
    }

    /** Prefix and suffix product arrays. O(n) time, O(n) extra. */
    public static int[] prefixSuffix(int[] a) {
        int n = a.length;
        int[] pre = new int[n], suf = new int[n], out = new int[n];
        for (int i = 0; i < n; i++) pre[i] = i == 0 ? 1 : pre[i - 1] * a[i - 1];
        for (int i = n - 1; i >= 0; i--) suf[i] = i == n - 1 ? 1 : suf[i + 1] * a[i + 1];
        for (int i = 0; i < n; i++) out[i] = pre[i] * suf[i];
        return out;
    }

    /** Optimal: store prefix in output, multiply by a running suffix on the way back. O(n), O(1) extra. */
    public static int[] optimal(int[] a) {
        int n = a.length;
        int[] out = new int[n];
        out[0] = 1;
        for (int i = 1; i < n; i++) out[i] = out[i - 1] * a[i - 1];   // product of everything left of i
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            out[i] *= suffix;                                         // × product of everything right of i
            suffix *= a[i];
        }
        return out;
    }
}
