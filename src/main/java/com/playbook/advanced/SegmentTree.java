package com.playbook.advanced;

/**
 * Segment tree over an int array: point update and range SUM / range MIN queries, each O(log n).
 * Node i covers a range; its children 2i and 2i+1 cover the two halves. Size 4n is always enough.
 */
public final class SegmentTree {
    private final int n;
    private final long[] sum;
    private final int[] min;

    public SegmentTree(int[] a) {
        n = a.length;
        sum = new long[4 * Math.max(1, n)];
        min = new int[4 * Math.max(1, n)];
        if (n > 0) build(a, 1, 0, n - 1);
    }

    private void build(int[] a, int node, int lo, int hi) {
        if (lo == hi) {
            sum[node] = min[node] = a[lo];
            return;
        }
        int mid = (lo + hi) >>> 1;
        build(a, 2 * node, lo, mid);
        build(a, 2 * node + 1, mid + 1, hi);
        pull(node);
    }

    private void pull(int node) {                            // recompute a parent from its children
        sum[node] = sum[2 * node] + sum[2 * node + 1];
        min[node] = Math.min(min[2 * node], min[2 * node + 1]);
    }

    /** a[index] = value. Walk root → leaf, then fix every ancestor on the way back. O(log n). */
    public void update(int index, int value) {
        update(1, 0, n - 1, index, value);
    }

    private void update(int node, int lo, int hi, int idx, int value) {
        if (lo == hi) {
            sum[node] = min[node] = value;
            return;
        }
        int mid = (lo + hi) >>> 1;
        if (idx <= mid) update(2 * node, lo, mid, idx, value);
        else update(2 * node + 1, mid + 1, hi, idx, value);
        pull(node);
    }

    /** Sum of a[l..r] inclusive. A query range is covered by O(log n) whole nodes. */
    public long sum(int l, int r) {
        return sum(1, 0, n - 1, l, r);
    }

    private long sum(int node, int lo, int hi, int l, int r) {
        if (r < lo || hi < l) return 0;                      // no overlap
        if (l <= lo && hi <= r) return sum[node];            // total overlap: use the stored answer
        int mid = (lo + hi) >>> 1;                           // partial overlap: ask both children
        return sum(2 * node, lo, mid, l, r) + sum(2 * node + 1, mid + 1, hi, l, r);
    }

    /** Minimum of a[l..r] inclusive. */
    public int min(int l, int r) {
        return min(1, 0, n - 1, l, r);
    }

    private int min(int node, int lo, int hi, int l, int r) {
        if (r < lo || hi < l) return Integer.MAX_VALUE;
        if (l <= lo && hi <= r) return min[node];
        int mid = (lo + hi) >>> 1;
        return Math.min(min(2 * node, lo, mid, l, r), min(2 * node + 1, mid + 1, hi, l, r));
    }
}
