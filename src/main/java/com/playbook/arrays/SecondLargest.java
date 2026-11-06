package com.playbook.arrays;

import java.util.Arrays;
import java.util.OptionalInt;

/** Second largest DISTINCT element. [10, 5, 10] → 5. Empty if it does not exist. */
public final class SecondLargest {
    private SecondLargest() {}

    /** Sort and walk back skipping duplicates of the max. O(n log n). */
    public static OptionalInt sorting(int[] a) {
        if (a == null || a.length < 2) return OptionalInt.empty();
        int[] c = a.clone();
        Arrays.sort(c);
        for (int i = c.length - 2; i >= 0; i--) if (c[i] != c[c.length - 1]) return OptionalInt.of(c[i]);
        return OptionalInt.empty();
    }

    /** Optimal: track first and second in one pass. O(n), O(1). Uses long sentinels so MIN_VALUE works. */
    public static OptionalInt optimal(int[] a) {
        if (a == null) return OptionalInt.empty();
        long first = Long.MIN_VALUE, second = Long.MIN_VALUE;
        for (int x : a) {
            if (x > first) {
                second = first;                              // old max becomes second
                first = x;
            } else if (x < first && x > second) {
                second = x;                                  // strictly less than max → candidate
            }
        }
        return second == Long.MIN_VALUE ? OptionalInt.empty() : OptionalInt.of((int) second);
    }

    /** Java 8: distinct, sort descending, skip 1. */
    public static OptionalInt streams(int[] a) {
        if (a == null) return OptionalInt.empty();
        return Arrays.stream(a).boxed().distinct().sorted((x, y) -> Integer.compare(y, x))
                .skip(1).mapToInt(Integer::intValue).findFirst();
    }
}
