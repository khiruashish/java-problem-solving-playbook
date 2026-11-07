package com.playbook.advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Search suggestions (LeetCode 1268): after each typed character of `search`, return up to 3 products that start
 * with the typed prefix, in lexicographic order.
 */
public final class Autocomplete {
    private Autocomplete() {}

    /** Brute force: for every prefix, filter + sort all products. O(L · n · m log). */
    public static List<List<String>> bruteForce(String[] products, String search) {
        List<List<String>> out = new ArrayList<>();
        for (int i = 1; i <= search.length(); i++) {
            String p = search.substring(0, i);
            out.add(Arrays.stream(products).filter(s -> s.startsWith(p)).sorted().distinct().limit(3).toList());
        }
        return out;
    }

    /** Sort once + binary search: products with a prefix form a contiguous block starting at lowerBound(prefix). */
    public static List<List<String>> binarySearch(String[] products, String search) {
        String[] sorted = Arrays.stream(products).sorted().distinct().toArray(String[]::new);
        List<List<String>> out = new ArrayList<>();
        int start = 0;
        for (int i = 1; i <= search.length(); i++) {
            String p = search.substring(0, i);
            int lo = start, hi = sorted.length;
            while (lo < hi) {                                // lower bound of p (only moves right as p grows)
                int mid = (lo + hi) >>> 1;
                if (sorted[mid].compareTo(p) < 0) lo = mid + 1;
                else hi = mid;
            }
            start = lo;
            List<String> level = new ArrayList<>();
            for (int k = lo; k < Math.min(lo + 3, sorted.length) && sorted[k].startsWith(p); k++) level.add(sorted[k]);
            out.add(level);
        }
        return out;
    }

    /** Trie where every node stores its top-3 words (inserted in sorted order, so the first 3 are the smallest). */
    public static List<List<String>> trie(String[] products, String search) {
        Node root = new Node();
        for (String p : Arrays.stream(products).sorted().distinct().toArray(String[]::new)) {
            Node n = root;
            for (char c : p.toCharArray()) {
                n = n.next[c - 'a'] == null ? (n.next[c - 'a'] = new Node()) : n.next[c - 'a'];
                if (n.top.size() < 3) n.top.add(p);
            }
        }
        List<List<String>> out = new ArrayList<>();
        Node n = root;
        for (char c : search.toCharArray()) {
            n = n == null ? null : n.next[c - 'a'];
            out.add(n == null ? List.of() : List.copyOf(n.top));
        }
        return out;
    }

    private static final class Node {
        final Node[] next = new Node[26];
        final List<String> top = new ArrayList<>(3);
    }
}
