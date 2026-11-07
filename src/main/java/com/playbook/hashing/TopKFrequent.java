package com.playbook.hashing;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * The k most frequent values. Ties broken by smaller value first so results are deterministic.
 * [1,1,1,2,2,3], k=2 → [1,2]. Output ordered from most to least frequent.
 */
public final class TopKFrequent {
    private TopKFrequent() {}

    private static final Comparator<Map.Entry<Integer, Integer>> BY_FREQ_DESC_THEN_VALUE =
            Map.Entry.<Integer, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey());

    /** Count, then sort all distinct values by frequency. O(n + d log d). */
    public static List<Integer> sorting(int[] a, int k) {
        return counts(a).entrySet().stream().sorted(BY_FREQ_DESC_THEN_VALUE).limit(k).map(Map.Entry::getKey).toList();
    }

    /** Min-heap of size k: the root is the weakest of the current top k. O(n + d log k), O(d + k). */
    public static List<Integer> heap(int[] a, int k) {
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(BY_FREQ_DESC_THEN_VALUE.reversed());
        for (Map.Entry<Integer, Integer> e : counts(a).entrySet()) {
            pq.offer(e);
            if (pq.size() > k) pq.poll();                    // evict the weakest — heap never exceeds k
        }
        List<Integer> out = new ArrayList<>();
        while (!pq.isEmpty()) out.add(0, pq.poll().getKey()); // weakest comes out first → prepend
        return out;
    }

    /**
     * Bucket sort: frequency is at most n, so bucket[f] = values occurring f times. Walk buckets from high to low.
     * O(n) time, O(n) space — beats O(n log n).
     */
    @SuppressWarnings("unchecked")
    public static List<Integer> bucketSort(int[] a, int k) {
        List<Integer>[] buckets = new List[a.length + 1];
        for (Map.Entry<Integer, Integer> e : counts(a).entrySet()) {
            int f = e.getValue();
            if (buckets[f] == null) buckets[f] = new ArrayList<>();
            buckets[f].add(e.getKey());
        }
        List<Integer> out = new ArrayList<>();
        for (int f = a.length; f > 0 && out.size() < k; f--) {
            if (buckets[f] == null) continue;
            buckets[f].sort(null);                           // tie-break: smaller value first
            for (int v : buckets[f]) {
                if (out.size() == k) break;
                out.add(v);
            }
        }
        return out;
    }

    private static Map<Integer, Integer> counts(int[] a) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : a) m.merge(x, 1, Integer::sum);
        return m;
    }
}
