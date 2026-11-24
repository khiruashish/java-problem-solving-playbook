package com.playbook.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Majority element: the value that appears MORE than n/2 times.
 * The "guaranteed" variants assume one exists (LeetCode 169); {@link #verified} returns empty when none does.
 */
public final class MajorityElement {
    private MajorityElement() {}

    /** Brute force: count every candidate. O(n²) time, O(1) space. */
    public static OptionalInt bruteForce(int[] a) {
        for (int x : a) {
            int count = 0;
            for (int y : a) if (y == x) count++;
            if (count > a.length / 2) return OptionalInt.of(x);
        }
        return OptionalInt.empty();
    }

    /** Hash map counts. O(n) time, O(n) space. */
    public static OptionalInt hashing(int[] a) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int x : a) if (counts.merge(x, 1, Integer::sum) > a.length / 2) return OptionalInt.of(x);
        return OptionalInt.empty();
    }

    /** Sorting: if a majority exists it must occupy the middle index. O(n log n). Assumes one exists. */
    public static int sorting(int[] a) {
        int[] b = a.clone();
        Arrays.sort(b);
        return b[b.length / 2];
    }

    /**
     * Boyer–Moore voting. Pair each majority vote with a different value and cancel both; the majority survives
     * because it has more votes than all others combined. O(n) time, O(1) space. Assumes one exists.
     */
    public static int boyerMoore(int[] a) {
        int candidate = 0, votes = 0;
        for (int x : a) {
            if (votes == 0) candidate = x;            // previous prefix cancelled out completely: restart
            votes += (x == candidate) ? 1 : -1;
        }
        return candidate;
    }

    /** Boyer–Moore + a second pass to confirm, so arrays without a majority return empty. O(n), O(1). */
    public static OptionalInt verified(int[] a) {
        if (a.length == 0) return OptionalInt.empty();
        int candidate = boyerMoore(a), count = 0;
        for (int x : a) if (x == candidate) count++;
        return count > a.length / 2 ? OptionalInt.of(candidate) : OptionalInt.empty();
    }

    /** Java 8: frequency map, then the entry above n/2. */
    public static OptionalInt streams(int[] a) {
        return Arrays.stream(a).boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > a.length / 2)
                .mapToInt(Map.Entry::getKey)
                .findFirst();
    }
}
