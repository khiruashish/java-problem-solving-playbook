package com.playbook.strings;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Return the first character that occurs exactly once, or {@code '\0'} if there is none. */
public final class FirstNonRepeatingChar {
    public static final char NONE = '\0';
    private FirstNonRepeatingChar() {}

    /** Brute force: for each index, scan the whole string. O(n^2). */
    public static char bruteForce(String s) {
        if (s == null) return NONE;
        for (int i = 0; i < s.length(); i++) {
            boolean repeated = false;
            for (int j = 0; j < s.length() && !repeated; j++) {
                repeated = i != j && s.charAt(i) == s.charAt(j);
            }
            if (!repeated) return s.charAt(i);
        }
        return NONE;
    }

    /** Optimal: two passes — count, then find the first with count 1. O(n) time, O(1) (fixed alphabet). */
    public static char optimal(String s) {
        if (s == null) return NONE;
        int[] counts = new int[Character.MAX_VALUE + 1];
        for (char c : s.toCharArray()) counts[c]++;
        for (char c : s.toCharArray()) if (counts[c] == 1) return c;   // second pass keeps original order
        return NONE;
    }

    /** Java 8 streams: ordered frequency map, first entry with value 1. */
    public static char streams(String s) {
        if (s == null) return NONE;
        return s.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(NONE);
    }

    /** Index-based variant (LeetCode 387): returns index or -1. Uses indexOf == lastIndexOf. */
    public static int firstUniqueIndex(String s) {
        if (s == null) return -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (s.indexOf(c) == s.lastIndexOf(c)) return i;  // simple but O(n^2) worst case
        }
        return -1;
    }
}
