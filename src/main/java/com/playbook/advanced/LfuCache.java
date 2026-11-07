package com.playbook.advanced;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Least-Frequently-Used cache, O(1) get/put (LeetCode 460). On eviction remove the key with the LOWEST use count;
 * among ties, the least RECENTLY used. get returns -1 if missing.
 */
public final class LfuCache {
    private final int capacity;
    private final Map<Integer, Integer> values = new HashMap<>();
    private final Map<Integer, Integer> counts = new HashMap<>();
    /** frequency → keys with that frequency, in recency order (LinkedHashSet = O(1) remove + insertion order). */
    private final Map<Integer, LinkedHashSet<Integer>> buckets = new HashMap<>();
    private int minFreq;

    public LfuCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Integer v = values.get(key);
        if (v == null) return -1;
        touch(key);
        return v;
    }

    public void put(int key, int value) {
        if (capacity <= 0) return;
        if (values.containsKey(key)) {
            values.put(key, value);
            touch(key);
            return;
        }
        if (values.size() == capacity) {                     // evict LFU, oldest among ties = first in its bucket
            LinkedHashSet<Integer> lowest = buckets.get(minFreq);
            int victim = lowest.iterator().next();
            lowest.remove(victim);
            values.remove(victim);
            counts.remove(victim);
        }
        values.put(key, value);
        counts.put(key, 1);
        buckets.computeIfAbsent(1, f -> new LinkedHashSet<>()).add(key);
        minFreq = 1;                                         // a brand-new key always has the minimum frequency
    }

    /** Move key from bucket f to f+1; if bucket f was the minimum and is now empty, the minimum becomes f+1. */
    private void touch(int key) {
        int f = counts.get(key);
        counts.put(key, f + 1);
        LinkedHashSet<Integer> from = buckets.get(f);
        from.remove(key);
        if (from.isEmpty()) {
            buckets.remove(f);
            if (minFreq == f) minFreq = f + 1;
        }
        buckets.computeIfAbsent(f + 1, x -> new LinkedHashSet<>()).add(key);
    }

    public int size() {
        return values.size();
    }
}
