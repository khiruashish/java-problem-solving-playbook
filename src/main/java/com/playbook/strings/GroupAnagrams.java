package com.playbook.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Group words that are anagrams of each other. Groups appear in first-occurrence order. */
public final class GroupAnagrams {
    private GroupAnagrams() {}

    /** Key = sorted chars. O(n · k log k) for n words of length k. */
    public static List<List<String>> sortedKey(String[] words) {
        Map<String, List<String>> groups = new LinkedHashMap<>();
        if (words == null) return new ArrayList<>();
        for (String w : words) {
            char[] c = w.toCharArray();
            Arrays.sort(c);
            groups.computeIfAbsent(new String(c), k -> new ArrayList<>()).add(w);
        }
        return new ArrayList<>(groups.values());
    }

    /** Optimal: key = 26-letter count signature ("#1#0#2..."). O(n · k). Lowercase a–z input. */
    public static List<List<String>> countKey(String[] words) {
        Map<String, List<String>> groups = new LinkedHashMap<>();
        if (words == null) return new ArrayList<>();
        for (String w : words) {
            int[] counts = new int[26];
            for (char c : w.toCharArray()) counts[c - 'a']++;
            groups.computeIfAbsent(Arrays.toString(counts), k -> new ArrayList<>()).add(w);
        }
        return new ArrayList<>(groups.values());
    }

    /** Java 8 streams: groupingBy on the sorted-chars key. */
    public static List<List<String>> streams(String[] words) {
        if (words == null) return new ArrayList<>();
        Map<String, List<String>> m = Arrays.stream(words).collect(Collectors.groupingBy(
                w -> w.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString(),
                LinkedHashMap::new, Collectors.toList()));
        return new ArrayList<>(m.values());
    }

}
