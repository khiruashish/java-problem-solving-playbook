package com.playbook.java8;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Advanced Map API and Collectors questions (Medium → Hard). Every result is deterministic (sorted maps). */
public final class MapCollectorsAdvanced {
    private MapCollectorsAdvanced() {}

    /** Q1. Word frequency with Map.merge — the one-liner replacement for "get, null-check, put". */
    public static Map<String, Integer> wordCountMerge(List<String> words) {
        Map<String, Integer> counts = new TreeMap<>();
        for (String w : words) counts.merge(w, 1, Integer::sum);
        return counts;
    }

    /** Q2. Group into lists with computeIfAbsent (the imperative twin of groupingBy). Key = first letter. */
    public static Map<Character, List<String>> groupByFirstLetter(List<String> words) {
        Map<Character, List<String>> groups = new TreeMap<>();
        for (String w : words) groups.computeIfAbsent(w.charAt(0), k -> new java.util.ArrayList<>()).add(w);
        return groups;
    }

    /** Q3. toMap with duplicate keys: keep the LONGEST word per first letter; ties keep the first one seen. */
    public static Map<Character, String> longestPerFirstLetter(List<String> words) {
        return words.stream().collect(Collectors.toMap(
                w -> w.charAt(0),
                Function.identity(),
                (a, b) -> b.length() > a.length() ? b : a,   // merge function: without it, duplicates throw
                TreeMap::new));
    }

    /** Q4. Invert a map (value → keys). Many keys can share a value, so the values become sorted sets. */
    public static Map<Integer, Set<String>> invert(Map<String, Integer> m) {
        return m.entrySet().stream().collect(Collectors.groupingBy(
                Map.Entry::getValue, TreeMap::new,
                Collectors.mapping(Map.Entry::getKey, Collectors.toCollection(TreeSet::new))));
    }

    /** Q5. Two-level grouping: length → first letter → count. */
    public static Map<Integer, Map<Character, Long>> lengthThenLetter(List<String> words) {
        return words.stream().collect(Collectors.groupingBy(String::length, TreeMap::new,
                Collectors.groupingBy(w -> w.charAt(0), TreeMap::new, Collectors.counting())));
    }

    /** Q6. Java 9 filtering(): keep EVERY group key, even when no element passes the filter. */
    public static Map<Character, List<String>> longWordsPerLetterKeepEmpty(List<String> words, int minLen) {
        return words.stream().collect(Collectors.groupingBy(w -> w.charAt(0), TreeMap::new,
                Collectors.filtering(w -> w.length() >= minLen, Collectors.toList())));
    }

    /** Q6b. The same with filter() BEFORE grouping: letters with no long word disappear. */
    public static Map<Character, List<String>> longWordsPerLetterDropEmpty(List<String> words, int minLen) {
        return words.stream().filter(w -> w.length() >= minLen)
                .collect(Collectors.groupingBy(w -> w.charAt(0), TreeMap::new, Collectors.toList()));
    }

    /** Q7. Java 9 flatMapping(): distinct characters used per word length. */
    public static Map<Integer, Set<Character>> charsPerLength(List<String> words) {
        return words.stream().collect(Collectors.groupingBy(String::length, TreeMap::new,
                Collectors.flatMapping(w -> w.chars().mapToObj(c -> (char) c),
                        Collectors.toCollection(TreeSet::new))));
    }

    /** Q8. Top-N per group: the 2 longest words per first letter (ties broken alphabetically). */
    public static Map<Character, List<String>> topTwoLongestPerLetter(List<String> words) {
        Comparator<String> longestFirst = Comparator.comparingInt(String::length).reversed()
                .thenComparing(Comparator.naturalOrder());
        return words.stream().collect(Collectors.groupingBy(w -> w.charAt(0), TreeMap::new,
                Collectors.collectingAndThen(Collectors.toList(),
                        list -> list.stream().sorted(longestFirst).limit(2).toList())));
    }

    /** Q9. Sort a map by value descending, then key, into a LinkedHashMap (insertion order = sorted order). */
    public static LinkedHashMap<String, Integer> sortByValueDesc(Map<String, Integer> m) {
        return m.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }

    /** Q10. Merge two count maps, summing shared keys (Map.merge inside forEach). */
    public static Map<String, Integer> mergeCounts(Map<String, Integer> a, Map<String, Integer> b) {
        Map<String, Integer> out = new TreeMap<>(a);
        b.forEach((k, v) -> out.merge(k, v, Integer::sum));
        return out;
    }

    /** Q11. compute() to keep a running maximum per key (null-safe: first value wins initially). */
    public static Map<String, Integer> maxPerKey(List<Map.Entry<String, Integer>> readings) {
        Map<String, Integer> max = new HashMap<>();
        for (Map.Entry<String, Integer> r : readings) {
            max.compute(r.getKey(), (k, old) -> old == null ? r.getValue() : Math.max(old, r.getValue()));
        }
        return new TreeMap<>(max);
    }
}
