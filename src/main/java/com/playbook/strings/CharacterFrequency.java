package com.playbook.strings;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Count how many times each character occurs (preserving first-occurrence order) and list duplicates.
 */
public final class CharacterFrequency {
    private CharacterFrequency() {}

    /** Brute force: for each char count it with an inner loop. O(n^2). */
    public static Map<Character, Integer> bruteForce(String s) {
        Map<Character, Integer> result = new LinkedHashMap<>();
        if (s == null) return result;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (result.containsKey(c)) continue;             // already counted
            int count = 0;
            for (int j = i; j < s.length(); j++) if (s.charAt(j) == c) count++;
            result.put(c, count);
        }
        return result;
    }

    /** Optimal: one pass with a hash map. O(n) time, O(k) space for k distinct chars. */
    public static Map<Character, Integer> optimal(String s) {
        Map<Character, Integer> freq = new LinkedHashMap<>();
        if (s == null) return freq;
        for (char c : s.toCharArray()) freq.merge(c, 1, Integer::sum);
        return freq;
    }

    /** Fixed alphabet trick: int[26] for lowercase a–z only. Fastest; O(1) space. */
    public static int[] lowercaseCounts(String s) {
        int[] counts = new int[26];
        if (s == null) return counts;
        for (char c : s.toCharArray()) if (c >= 'a' && c <= 'z') counts[c - 'a']++;
        return counts;
    }

    /** Java 8 streams: groupingBy with LinkedHashMap to keep order. */
    public static Map<Character, Long> streams(String s) {
        if (s == null) return new LinkedHashMap<>();
        return s.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
    }

    /** Duplicates: characters occurring more than once, in first-occurrence order. */
    public static List<Character> duplicates(String s) {
        return optimal(s).entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
    }
}
