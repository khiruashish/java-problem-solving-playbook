package com.playbook.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

/** k most frequent words; ties broken ALPHABETICALLY (LeetCode 692). ["i","love","leetcode","i","love","coding"], 2 → [i, love]. */
public final class TopKFrequentWords {
    private TopKFrequentWords() {}

    /** "Better" order: higher count first, then alphabetical. */
    private static Comparator<Map.Entry<String, Long>> better() {
        return Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey());
    }

    /** Count, sort all distinct words. O(n + d log d). */
    public static List<String> sorting(String[] words, int k) {
        return count(words).entrySet().stream().sorted(better()).limit(k).map(Map.Entry::getKey).toList();
    }

    /**
     * Heap of size k ordered WORST-first (lower count, or alphabetically LATER on a tie) so the root is the one to evict.
     * O(n + d log k). Pop gives worst → best, so reverse at the end.
     */
    public static List<String> heap(String[] words, int k) {
        PriorityQueue<Map.Entry<String, Long>> pq = new PriorityQueue<>(better().reversed());
        for (Map.Entry<String, Long> e : count(words).entrySet()) {
            pq.offer(e);
            if (pq.size() > k) pq.poll();
        }
        List<String> out = new ArrayList<>();
        while (!pq.isEmpty()) out.add(pq.poll().getKey());
        Collections.reverse(out);
        return out;
    }

    private static Map<String, Long> count(String[] words) {
        return java.util.Arrays.stream(words).collect(Collectors.groupingBy(Function.identity(), HashMap::new, Collectors.counting()));
    }
}
