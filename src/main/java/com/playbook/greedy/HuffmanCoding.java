package com.playbook.greedy;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/** Optimal prefix-free binary codes for symbol frequencies (data compression). */
public final class HuffmanCoding {
    private HuffmanCoding() {}

    /** Tree node: leaves carry a symbol. */
    private record Node(long freq, char symbol, Node left, Node right, int order) {
        boolean isLeaf() {
            return left == null;
        }
    }

    /**
     * Greedy: repeatedly merge the two LEAST frequent trees (min-heap); the merged weight is their sum.
     * Rare symbols end up deepest → longest codes. Ties broken by creation order for deterministic codes. O(n log n).
     */
    public static Map<Character, String> codes(Map<Character, Long> freq) {
        Map<Character, String> out = new HashMap<>();
        if (freq.isEmpty()) return out;
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) ->
                a.freq != b.freq ? Long.compare(a.freq, b.freq) : Integer.compare(a.order, b.order));
        int order = 0;
        for (Map.Entry<Character, Long> e : new java.util.TreeMap<>(freq).entrySet())
            pq.offer(new Node(e.getValue(), e.getKey(), null, null, order++));
        if (pq.size() == 1) {                                // single symbol still needs a 1-bit code
            out.put(pq.peek().symbol(), "0");
            return out;
        }
        while (pq.size() > 1) {
            Node a = pq.poll(), b = pq.poll();
            pq.offer(new Node(a.freq + b.freq, '\0', a, b, order++));
        }
        assign(pq.poll(), "", out);
        return out;
    }

    private static void assign(Node n, String prefix, Map<Character, String> out) {
        if (n.isLeaf()) {
            out.put(n.symbol(), prefix);
            return;
        }
        assign(n.left(), prefix + "0", out);                 // left edge = 0
        assign(n.right(), prefix + "1", out);                // right edge = 1
    }

    /** Total encoded length in bits = Σ freq × codeLength (the quantity Huffman minimises). */
    public static long encodedBits(Map<Character, Long> freq, Map<Character, String> codes) {
        long bits = 0;
        for (Map.Entry<Character, Long> e : freq.entrySet()) bits += e.getValue() * codes.get(e.getKey()).length();
        return bits;
    }

    /** Same total via merge costs only (classic GfG "minimum cost of ropes"): sum of all merged weights. */
    public static long minMergeCost(long[] freq) {
        PriorityQueue<Long> pq = new PriorityQueue<>();
        for (long f : freq) pq.offer(f);
        long cost = 0;
        while (pq.size() > 1) {
            long merged = pq.poll() + pq.poll();
            cost += merged;
            pq.offer(merged);
        }
        return cost;
    }

    public static String encode(String text, Map<Character, String> codes) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) sb.append(codes.get(c));
        return sb.toString();
    }

    /** Prefix-free codes decode greedily: read bits until they match a code. */
    public static String decode(String bits, Map<Character, String> codes) {
        Map<String, Character> reverse = new HashMap<>();
        codes.forEach((k, v) -> reverse.put(v, k));
        StringBuilder out = new StringBuilder(), cur = new StringBuilder();
        for (char b : bits.toCharArray()) {
            cur.append(b);
            Character c = reverse.get(cur.toString());
            if (c != null) {
                out.append(c);
                cur.setLength(0);
            }
        }
        return out.toString();
    }
}
