package com.playbook.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Two strings are anagrams if they contain the same characters with the same counts (case-sensitive). */
public final class Anagram {
    private Anagram() {}

    /** Sort both and compare. O(n log n) time, O(n) space. */
    public static boolean sorting(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) return false;
        char[] x = a.toCharArray(), y = b.toCharArray();
        Arrays.sort(x);
        Arrays.sort(y);
        return Arrays.equals(x, y);
    }

    /** Optimal: one count array; +1 for a, -1 for b; all zeros at the end. O(n) time, O(1) space. */
    public static boolean optimal(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) return false;
        int[] counts = new int[Character.MAX_VALUE + 1];     // covers every char; use int[26] for a–z only
        for (int i = 0; i < a.length(); i++) {
            counts[a.charAt(i)]++;
            counts[b.charAt(i)]--;
        }
        for (int c : counts) if (c != 0) return false;
        return true;
    }

    /** HashMap version — works for any alphabet, sparse memory. */
    public static boolean hashMap(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) return false;
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : a.toCharArray()) freq.merge(c, 1, Integer::sum);
        for (char c : b.toCharArray()) {
            int left = freq.getOrDefault(c, 0) - 1;
            if (left < 0) return false;                      // b has a char more often than a
            freq.put(c, left);
        }
        return true;
    }

    /** Java 8 streams: compare frequency maps. */
    public static boolean streams(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) return false;
        Function<String, Map<Integer, Long>> freq =
                s -> s.chars().boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return freq.apply(a).equals(freq.apply(b));
    }
}
