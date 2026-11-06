package com.playbook.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** Indices of two numbers adding to target (exactly one solution assumed). Returns {} if none. */
public final class TwoSum {
    private TwoSum() {}

    /** Brute force: every pair. O(n^2), O(1). */
    public static int[] bruteForce(int[] a, int target) {
        for (int i = 0; i < a.length; i++)
            for (int j = i + 1; j < a.length; j++)
                if (a[i] + a[j] == target) return new int[]{i, j};
        return new int[0];
    }

    /** Optimal: one-pass hash map value → index; look up the complement first. O(n), O(n). */
    public static int[] optimal(int[] a, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            Integer j = seen.get(target - a[i]);
            if (j != null) return new int[]{j, i};
            seen.put(a[i], i);                               // insert AFTER lookup so we don't pair with self
        }
        return new int[0];
    }

    /** Sorted input variant: two pointers. O(n), O(1). Returns indices into the sorted array. */
    public static int[] sortedTwoPointers(int[] sorted, int target) {
        int l = 0, r = sorted.length - 1;
        while (l < r) {
            int sum = sorted[l] + sorted[r];
            if (sum == target) return new int[]{l, r};
            if (sum < target) l++;                           // need bigger → move left up
            else r--;                                        // need smaller → move right down
        }
        return new int[0];
    }

    /** Unsorted + two pointers: sort index array by value (O(n log n)), keep original indices. */
    public static int[] sortThenTwoPointers(int[] a, int target) {
        Integer[] idx = new Integer[a.length];
        for (int i = 0; i < a.length; i++) idx[i] = i;
        Arrays.sort(idx, (x, y) -> Integer.compare(a[x], a[y]));
        int l = 0, r = a.length - 1;
        while (l < r) {
            int sum = a[idx[l]] + a[idx[r]];
            if (sum == target) return new int[]{Math.min(idx[l], idx[r]), Math.max(idx[l], idx[r])};
            if (sum < target) l++;
            else r--;
        }
        return new int[0];
    }
}
