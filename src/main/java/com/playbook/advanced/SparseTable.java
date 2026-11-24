package com.playbook.advanced;

/**
 * Sparse table for range-minimum queries (RMQ) on a STATIC array.
 * Build O(n log n); query min(a[l..r]) in O(1) — the two power-of-two blocks may overlap, which is fine for min
 * (an "idempotent" operation: min(x, x) = x). Sums can't overlap, so they get an O(log n) query instead.
 */
public final class SparseTable {
    private final int[][] min;      // min[k][i] = min of a[i .. i + 2^k − 1]
    private final long[][] sum;     // sum[k][i] = sum of the same block
    private final int[] log2;       // floor(log2(len)) for len = 1..n

    public SparseTable(int[] a) {
        int n = a.length;
        if (n == 0) throw new IllegalArgumentException("empty array");
        log2 = new int[n + 1];
        for (int len = 2; len <= n; len++) log2[len] = log2[len / 2] + 1;
        int levels = log2[n] + 1;
        min = new int[levels][];
        sum = new long[levels][];
        min[0] = a.clone();
        sum[0] = new long[n];
        for (int i = 0; i < n; i++) sum[0][i] = a[i];
        for (int k = 1; k < levels; k++) {
            int half = 1 << (k - 1), count = n - (1 << k) + 1;
            min[k] = new int[count];
            sum[k] = new long[count];
            for (int i = 0; i < count; i++) {                       // a block of 2^k = two blocks of 2^(k−1)
                min[k][i] = Math.min(min[k - 1][i], min[k - 1][i + half]);
                sum[k][i] = sum[k - 1][i] + sum[k - 1][i + half];
            }
        }
    }

    /** min(a[l..r]) in O(1): cover [l, r] with two blocks of length 2^k that may overlap. */
    public int min(int l, int r) {
        check(l, r);
        int k = log2[r - l + 1];
        return Math.min(min[k][l], min[k][r - (1 << k) + 1]);
    }

    /** sum(a[l..r]) in O(log n): blocks must NOT overlap, so take the binary decomposition of the length. */
    public long sum(int l, int r) {
        check(l, r);
        long total = 0;
        for (int k = log2[r - l + 1]; k >= 0; k--) {
            if (l + (1 << k) - 1 <= r) {
                total += sum[k][l];
                l += 1 << k;
            }
        }
        return total;
    }

    /** Brute-force oracle: scan the range. O(n) per query. */
    public static int minBrute(int[] a, int l, int r) {
        int m = Integer.MAX_VALUE;
        for (int i = l; i <= r; i++) m = Math.min(m, a[i]);
        return m;
    }

    private void check(int l, int r) {
        if (l < 0 || r >= min[0].length || l > r) throw new IndexOutOfBoundsException("bad range [" + l + ", " + r + "]");
    }
}
