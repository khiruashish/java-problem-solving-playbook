package com.playbook.hashing;

import java.util.Objects;

/**
 * A HashMap from scratch: array of buckets with separate chaining, hash spreading, load-factor resize.
 * Supports null keys like java.util.HashMap. Not thread-safe.
 */
public final class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;          // always a power of two
    private static final float LOAD_FACTOR = 0.75f;

    /** One entry in a bucket's singly linked chain. */
    private static final class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Node<K, V>[] table;
    private int size;

    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity) {
        int cap = 1;
        while (cap < Math.max(1, initialCapacity)) cap <<= 1; // round up to a power of two
        table = (Node<K, V>[]) new Node[cap];
    }

    /**
     * Spread the high bits into the low bits (same trick as java.util.HashMap): the index uses only the low bits,
     * so without this, hashCodes differing only in high bits would all collide.
     */
    static int hash(Object key) {
        int h = key == null ? 0 : key.hashCode();
        return h ^ (h >>> 16);
    }

    /** index = hash mod capacity; with a power-of-two capacity that is a cheap bit mask, and never negative. */
    private static int indexFor(int hash, int capacity) {
        return hash & (capacity - 1);
    }

    /** Insert or replace. Returns the previous value or null. Average O(1). */
    public V put(K key, V value) {
        int h = hash(key);
        int i = indexFor(h, table.length);
        for (Node<K, V> n = table[i]; n != null; n = n.next) {
            if (n.hash == h && Objects.equals(n.key, key)) { // cheap hash check first, then equals
                V old = n.value;
                n.value = value;
                return old;
            }
        }
        table[i] = new Node<>(h, key, value, table[i]);      // prepend to the chain
        if (++size > table.length * LOAD_FACTOR) resize();
        return null;
    }

    public V get(K key) {
        Node<K, V> n = find(key);
        return n == null ? null : n.value;
    }

    public boolean containsKey(K key) {
        return find(key) != null;                            // distinguishes "absent" from "mapped to null"
    }

    /** Remove the mapping; returns the old value or null. Average O(1). */
    public V remove(K key) {
        int h = hash(key);
        int i = indexFor(h, table.length);
        Node<K, V> prev = null;
        for (Node<K, V> n = table[i]; n != null; prev = n, n = n.next) {
            if (n.hash == h && Objects.equals(n.key, key)) {
                if (prev == null) table[i] = n.next;
                else prev.next = n.next;
                size--;
                return n.value;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Current bucket count (exposed for tests and teaching). */
    int capacity() {
        return table.length;
    }

    private Node<K, V> find(K key) {
        int h = hash(key);
        for (Node<K, V> n = table[indexFor(h, table.length)]; n != null; n = n.next) {
            if (n.hash == h && Objects.equals(n.key, key)) return n;
        }
        return null;
    }

    /** Double the table and re-bucket every node. O(n), but happens rarely enough to be amortised O(1) per put. */
    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] old = table;
        table = (Node<K, V>[]) new Node[old.length * 2];
        for (Node<K, V> head : old) {
            for (Node<K, V> n = head; n != null; ) {
                Node<K, V> next = n.next;
                int i = indexFor(n.hash, table.length);      // stored hash: no need to call hashCode() again
                n.next = table[i];
                table[i] = n;
                n = next;
            }
        }
    }
}
