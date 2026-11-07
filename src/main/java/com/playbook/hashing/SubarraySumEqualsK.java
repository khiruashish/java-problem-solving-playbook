package com.playbook.hashing;

import java.util.HashMap;
import java.util.Map;

/** Number of contiguous subarrays whose sum is exactly k. Values may be negative. [1,1,1], k=2 → 2. */
public final class SubarraySumEqualsK {
    private SubarraySumEqualsK() {}

    /** Brute force: every (start, end) with a running sum. O(n^2), O(1). */
    public static int bruteForce(int[] a, int k) {
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            long sum = 0;
            for (int j = i; j < a.length; j++) {
                sum += a[j];
                if (sum == k) count++;
            }
        }
        return count;
    }

    /**
     * Optimal: prefix sums + hash map. sum(i+1..j) = prefix[j] - prefix[i], so a subarray ending at j sums to k
     * exactly when an EARLIER prefix equals prefix[j] - k. Count those with a map prefix → occurrences. O(n), O(n).
     */
    public static int prefixSumMap(int[] a, int k) {
        Map<Long, Integer> seen = new HashMap<>();
        seen.put(0L, 1);                                     // empty prefix: lets subarrays starting at index 0 count
        long prefix = 0;
        int count = 0;
        for (int x : a) {
            prefix += x;
            count += seen.getOrDefault(prefix - k, 0);       // look up BEFORE inserting the current prefix
            seen.merge(prefix, 1, Integer::sum);
        }
        return count;
    }

    /** Only valid for NON-NEGATIVE values: sliding window (shrink while sum > k). O(n), O(1). */
    public static int slidingWindowNonNegative(int[] a, int k) {
        int count = 0, left = 0;
        long sum = 0;
        for (int right = 0; right < a.length; right++) {
            sum += a[right];
            while (sum > k && left <= right) sum -= a[left++];
            if (sum == k && left <= right) {                // left > right means an EMPTY window (k == 0)
                count++;
                // zeros at the left edge give extra windows with the same sum
                for (int l = left; l < right && a[l] == 0; l++) count++;
            }
        }
        return count;
    }
}
