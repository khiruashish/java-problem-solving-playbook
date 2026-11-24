package com.playbook.advanced;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Consistent hashing ring with virtual nodes. Keys and servers hash onto a ring of 2^32 positions; a key belongs to the
 * first server clockwise from it. Adding or removing a server only moves the keys in the arcs that server owned —
 * about K/N keys — instead of almost all keys as with hash(key) % N.
 */
public final class ConsistentHashing {
    private final NavigableMap<Long, String> ring = new TreeMap<>();
    private final int virtualNodes;

    /** @param virtualNodes points per server on the ring; more points → more even load (100–200 is typical). */
    public ConsistentHashing(int virtualNodes) {
        if (virtualNodes < 1) throw new IllegalArgumentException("virtualNodes must be >= 1");
        this.virtualNodes = virtualNodes;
    }

    public void addServer(String server) {
        for (int i = 0; i < virtualNodes; i++) ring.put(hash(server + "#" + i), server);
    }

    public void removeServer(String server) {
        for (int i = 0; i < virtualNodes; i++) ring.remove(hash(server + "#" + i), server);
    }

    /** First virtual node clockwise from the key's position (wrapping around). O(log(N · V)). */
    public String serverFor(String key) {
        if (ring.isEmpty()) throw new IllegalStateException("no servers");
        Map.Entry<Long, String> e = ring.ceilingEntry(hash(key));
        return e != null ? e.getValue() : ring.firstEntry().getValue();   // past the last point → wrap to the first
    }

    /** The same lookup written with tailMap, as many textbooks show it. */
    public String serverForTailMap(String key) {
        if (ring.isEmpty()) throw new IllegalStateException("no servers");
        SortedMap<Long, String> tail = ring.tailMap(hash(key));
        return tail.isEmpty() ? ring.get(ring.firstKey()) : tail.get(tail.firstKey());
    }

    public int size() {
        return ring.size();
    }

    /** The naive scheme for comparison: hash(key) mod N. Almost every key moves when N changes. */
    public static int moduloBucket(String key, int servers) {
        return (int) (hash(key) % servers);
    }

    /**
     * 32-bit FNV-1a over UTF-8 bytes, plus a final avalanche mix so similar strings ("s#1", "s#2") spread out.
     * Deterministic across JVMs (unlike String.hashCode-based schemes, which are fine but cluster for short keys).
     */
    static long hash(String s) {
        int h = 0x811C9DC5;
        for (byte b : s.getBytes(StandardCharsets.UTF_8)) {
            h ^= b & 0xFF;
            h *= 0x01000193;
        }
        h ^= h >>> 16;                                   // murmur3 fmix32
        h *= 0x85EBCA6B;
        h ^= h >>> 13;
        h *= 0xC2B2AE35;
        h ^= h >>> 16;
        return h & 0xFFFFFFFFL;                          // unsigned position on the ring
    }
}
