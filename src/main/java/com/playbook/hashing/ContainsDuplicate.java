package com.playbook.hashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Does any value appear at least twice? Plus the "within distance k" variant (LeetCode 219). */
public final class ContainsDuplicate {
    private ContainsDuplicate() {}

    /** Brute force: compare every pair. O(n^2), O(1). */
    public static boolean bruteForce(int[] a) {
        for (int i = 0; i < a.length; i++)
            for (int j = i + 1; j < a.length; j++)
                if (a[i] == a[j]) return true;
        return false;
    }

    /** Sort a copy: duplicates become neighbours. O(n log n), O(n) for the copy (O(1) if mutation is allowed). */
    public static boolean sorting(int[] a) {
        int[] c = a.clone();
        Arrays.sort(c);
        for (int i = 1; i < c.length; i++) if (c[i] == c[i - 1]) return true;
        return false;
    }

    /** Optimal: HashSet.add returns false when the value is already present. O(n), O(n). */
    public static boolean hashSet(int[] a) {
        Set<Integer> seen = new HashSet<>();
        for (int x : a) if (!seen.add(x)) return true;      // early exit on the first repeat
        return false;
    }

    /** Java 8 streams: distinct count smaller than total count. Always scans everything. */
    public static boolean streams(int[] a) {
        return Arrays.stream(a).distinct().count() < a.length;
    }

    /** Variant: two equal values whose indexes differ by at most k. Map value → last index. O(n). */
    public static boolean withinDistance(int[] a, int k) {
        Map<Integer, Integer> lastIndex = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            Integer prev = lastIndex.put(a[i], i);           // put returns the previous mapping
            if (prev != null && i - prev <= k) return true;
        }
        return false;
    }
}
