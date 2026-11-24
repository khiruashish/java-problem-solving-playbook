package com.playbook.hashing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Number of distinct elements in every window of size k. [1,2,1,3,4,2,3], k=4 → [3,4,4,3]. */
public final class DistinctInWindow {
    private DistinctInWindow() {}

    /** Brute force: a fresh set per window. O(n·k). */
    public static int[] bruteForce(int[] a, int k) {
        if (k <= 0 || k > a.length) return new int[0];
        int[] out = new int[a.length - k + 1];
        for (int i = 0; i + k <= a.length; i++) {
            Set<Integer> set = new HashSet<>();
            for (int j = i; j < i + k; j++) set.add(a[j]);
            out[i] = set.size();
        }
        return out;
    }

    /**
     * Sliding window + frequency map: add the entering element, remove the leaving one, and delete keys whose
     * count drops to zero so that map.size() is always the distinct count. O(n) time, O(k) space.
     */
    public static int[] slidingWindow(int[] a, int k) {
        if (k <= 0 || k > a.length) return new int[0];
        int[] out = new int[a.length - k + 1];
        Map<Integer, Integer> freq = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            freq.merge(a[i], 1, Integer::sum);                          // element entering
            if (i >= k) {
                // merge returning null removes the key: the count reached zero
                freq.merge(a[i - k], -1, (old, d) -> old + d == 0 ? null : old + d);
            }
            if (i >= k - 1) out[i - k + 1] = freq.size();
        }
        return out;
    }

    /** Variant without a map when values are small and non-negative: a counts array + a running distinct counter. */
    public static int[] countsArray(int[] a, int k, int maxValue) {
        if (k <= 0 || k > a.length) return new int[0];
        int[] out = new int[a.length - k + 1], count = new int[maxValue + 1];
        int distinct = 0;
        for (int i = 0; i < a.length; i++) {
            if (count[a[i]]++ == 0) distinct++;                        // first copy in the window
            if (i >= k && --count[a[i - k]] == 0) distinct--;          // last copy left the window
            if (i >= k - 1) out[i - k + 1] = distinct;
        }
        return out;
    }
}
