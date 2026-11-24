package com.playbook.advanced;

/**
 * Segment tree with LAZY PROPAGATION: range add + range sum, both O(log n).
 * Without laziness a range update touches every leaf in the range — O(n). With it, an update stops at nodes fully
 * inside the range and leaves a "pending add" tag that is pushed to the children only when someone looks below.
 */
public final class LazySegmentTree {
    private final int n;
    private final long[] sum;       // sum of the node's segment (already including its own pending add)
    private final long[] lazy;      // pending add for every element of the node's segment, not yet given to children

    public LazySegmentTree(int[] a) {
        n = a.length;
        if (n == 0) throw new IllegalArgumentException("empty array");
        sum = new long[4 * n];
        lazy = new long[4 * n];
        build(1, 0, n - 1, a);
    }

    private void build(int node, int lo, int hi, int[] a) {
        if (lo == hi) { sum[node] = a[lo]; return; }
        int mid = (lo + hi) >>> 1;
        build(2 * node, lo, mid, a);
        build(2 * node + 1, mid + 1, hi, a);
        sum[node] = sum[2 * node] + sum[2 * node + 1];
    }

    /** Add v to every element of a[l..r]. O(log n). */
    public void add(int l, int r, long v) {
        check(l, r);
        add(1, 0, n - 1, l, r, v);
    }

    private void add(int node, int lo, int hi, int l, int r, long v) {
        if (r < lo || hi < l) return;                              // no overlap
        if (l <= lo && hi <= r) {                                  // fully covered: tag and stop here
            apply(node, lo, hi, v);
            return;
        }
        push(node, lo, hi);                                        // partial: children must be up to date first
        int mid = (lo + hi) >>> 1;
        add(2 * node, lo, mid, l, r, v);
        add(2 * node + 1, mid + 1, hi, l, r, v);
        sum[node] = sum[2 * node] + sum[2 * node + 1];
    }

    /** Sum of a[l..r]. O(log n). */
    public long sum(int l, int r) {
        check(l, r);
        return sum(1, 0, n - 1, l, r);
    }

    private long sum(int node, int lo, int hi, int l, int r) {
        if (r < lo || hi < l) return 0;
        if (l <= lo && hi <= r) return sum[node];
        push(node, lo, hi);
        int mid = (lo + hi) >>> 1;
        return sum(2 * node, lo, mid, l, r) + sum(2 * node + 1, mid + 1, hi, l, r);
    }

    /** Add v to a whole node: its sum grows by v × length, and the add is remembered for its children. */
    private void apply(int node, int lo, int hi, long v) {
        sum[node] += v * (hi - lo + 1);
        lazy[node] += v;
    }

    /** Hand a node's pending add down to its two children. */
    private void push(int node, int lo, int hi) {
        if (lazy[node] == 0) return;
        int mid = (lo + hi) >>> 1;
        apply(2 * node, lo, mid, lazy[node]);
        apply(2 * node + 1, mid + 1, hi, lazy[node]);
        lazy[node] = 0;
    }

    private void check(int l, int r) {
        if (l < 0 || r >= n || l > r) throw new IndexOutOfBoundsException("bad range [" + l + ", " + r + "]");
    }

    /** Naive reference: a plain array, O(n) per operation. Used by the tests. */
    public static final class Naive {
        private final long[] a;

        public Naive(int[] init) {
            a = new long[init.length];
            for (int i = 0; i < init.length; i++) a[i] = init[i];
        }

        public void add(int l, int r, long v) { for (int i = l; i <= r; i++) a[i] += v; }

        public long sum(int l, int r) {
            long s = 0;
            for (int i = l; i <= r; i++) s += a[i];
            return s;
        }
    }
}
