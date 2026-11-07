package com.playbook.hashing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** Length of the longest run of consecutive integers (any order, duplicates allowed). [100,4,200,1,3,2] → 4 (1..4). */
public final class LongestConsecutiveSequence {
    private LongestConsecutiveSequence() {}

    /** Brute force: for each x, count x+1, x+2, … with a linear search each time. O(n^3) worst case. */
    public static int bruteForce(int[] a) {
        int best = 0;
        for (int x : a) {
            int len = 1;
            long next = (long) x + 1;
            while (contains(a, next)) {
                len++;
                next++;
            }
            best = Math.max(best, len);
        }
        return best;
    }

    /** Sort, then count runs, skipping duplicates. O(n log n). */
    public static int sorting(int[] a) {
        if (a.length == 0) return 0;
        int[] c = a.clone();
        Arrays.sort(c);
        int best = 1, run = 1;
        for (int i = 1; i < c.length; i++) {
            if (c[i] == c[i - 1]) continue;                  // duplicate: neither extends nor breaks
            run = (long) c[i] == (long) c[i - 1] + 1 ? run + 1 : 1;
            best = Math.max(best, run);
        }
        return best;
    }

    /**
     * Optimal: put everything in a HashSet. Only start counting at a sequence START (x-1 not present),
     * so every element is walked at most once overall. O(n) time, O(n) space.
     */
    public static int hashSet(int[] a) {
        Set<Integer> set = new HashSet<>();
        for (int x : a) set.add(x);
        int best = 0;
        for (int x : set) {                                  // iterate the set: duplicates already removed
            if (x != Integer.MIN_VALUE && set.contains(x - 1)) continue;   // not a start
            int len = 1;
            for (int y = x; y != Integer.MAX_VALUE && set.contains(y + 1); y++) len++;
            best = Math.max(best, len);
        }
        return best;
    }

    private static boolean contains(int[] a, long v) {
        for (int x : a) if (x == v) return true;
        return false;
    }
}
