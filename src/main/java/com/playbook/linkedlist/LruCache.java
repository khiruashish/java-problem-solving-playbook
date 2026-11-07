package com.playbook.linkedlist;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Least-Recently-Used cache with O(1) get and put. When full, put evicts the key used longest ago.
 * {@code get} returns -1 for a missing key.
 */
public final class LruCache {
    private final int capacity;
    private final Map<Integer, Entry> map = new HashMap<>();
    private final Entry head = new Entry(0, 0), tail = new Entry(0, 0);   // sentinels: no null checks

    /** Doubly linked node: most recent right after head, least recent right before tail. */
    private static final class Entry {
        final int key;
        int value;
        Entry prev, next;

        Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public LruCache(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Entry e = map.get(key);
        if (e == null) return -1;
        moveToFront(e);                                      // reading counts as "use"
        return e.value;
    }

    public void put(int key, int value) {
        Entry e = map.get(key);
        if (e != null) {
            e.value = value;
            moveToFront(e);
            return;
        }
        if (map.size() == capacity) {                        // evict least recently used
            Entry lru = tail.prev;
            unlink(lru);
            map.remove(lru.key);                             // that's why Entry stores its key
        }
        e = new Entry(key, value);
        map.put(key, e);
        addFront(e);
    }

    public int size() {
        return map.size();
    }

    private void moveToFront(Entry e) {
        unlink(e);
        addFront(e);
    }

    private void unlink(Entry e) {
        e.prev.next = e.next;
        e.next.prev = e.prev;
    }

    private void addFront(Entry e) {
        e.next = head.next;
        e.prev = head;
        head.next.prev = e;
        head.next = e;
    }

    /** Production shortcut: LinkedHashMap in access order + removeEldestEntry. Know it, but expect to hand-roll. */
    public static <K, V> Map<K, V> withLinkedHashMap(int capacity) {
        return new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity;
            }
        };
    }
}
