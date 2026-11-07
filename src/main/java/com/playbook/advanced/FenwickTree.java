package com.playbook.advanced;

/**
 * Fenwick tree (Binary Indexed Tree): point add and prefix sum in O(log n) with a single array and ~10 lines.
 * Internally 1-based: tree[i] stores the sum of the (i & -i) elements ending at i.
 */
public final class FenwickTree {
    private final long[] tree;

    public FenwickTree(int n) {
        tree = new long[n + 1];
    }

    /** Build in O(n): each node pushes its total to the next node that covers it. */
    public static FenwickTree of(int[] a) {
        FenwickTree f = new FenwickTree(a.length);
        for (int i = 1; i <= a.length; i++) {
            f.tree[i] += a[i - 1];
            int parent = i + (i & -i);
            if (parent <= a.length) f.tree[parent] += f.tree[i];
        }
        return f;
    }

    /** a[index] += delta (0-based index). Climb to every node whose range covers index: i += lowbit(i). */
    public void add(int index, long delta) {
        for (int i = index + 1; i < tree.length; i += i & -i) tree[i] += delta;
    }

    /** Sum of a[0..index] inclusive. Descend by removing the lowest set bit: i -= lowbit(i). */
    public long prefixSum(int index) {
        long s = 0;
        for (int i = index + 1; i > 0; i -= i & -i) s += tree[i];
        return s;
    }

    /** Sum of a[l..r] inclusive = prefix(r) − prefix(l−1). */
    public long rangeSum(int l, int r) {
        return prefixSum(r) - (l == 0 ? 0 : prefixSum(l - 1));
    }

    /**
     * Application: count inversions in O(n log M) for values in [0, maxValue]. Walk from the right; for each x, the
     * number of already-seen values < x are inversions with x.
     */
    public static long countInversions(int[] a, int maxValue) {
        FenwickTree f = new FenwickTree(maxValue + 1);
        long inv = 0;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] > 0) inv += f.prefixSum(a[i] - 1);
            f.add(a[i], 1);
        }
        return inv;
    }
}
