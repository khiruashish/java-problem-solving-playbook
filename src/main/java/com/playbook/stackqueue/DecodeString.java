package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Decode k[encoded] repeatedly: "3[a2[c]]" → "accaccacc". Input is valid: digits only appear as repeat counts,
 * brackets are balanced, k ≥ 1 (may have several digits, e.g. "10[a]").
 */
public final class DecodeString {
    private DecodeString() {}

    /** Two stacks: counts and the partial string built before each '['. O(output) time. */
    public static String twoStacks(String s) {
        Deque<Integer> counts = new ArrayDeque<>();
        Deque<StringBuilder> outer = new ArrayDeque<>();
        StringBuilder cur = new StringBuilder();
        int k = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                k = k * 10 + (c - '0');                 // multi-digit counts
            } else if (c == '[') {
                counts.push(k);                          // remember how often to repeat what comes next
                outer.push(cur);                         // and what came before it
                cur = new StringBuilder();
                k = 0;
            } else if (c == ']') {
                String inner = cur.toString();
                cur = outer.pop();
                cur.append(inner.repeat(counts.pop()));  // splice the repeated block back into its parent
            } else {
                cur.append(c);
            }
        }
        return cur.toString();
    }

    /** Recursive descent: decode(i) parses until the matching ']' and returns where it stopped. Mirrors the grammar. */
    public static String recursive(String s) {
        int[] pos = {0};
        return parse(s, pos);
    }

    // grammar: seq := ( letter | number '[' seq ']' )*
    private static String parse(String s, int[] pos) {
        StringBuilder sb = new StringBuilder();
        while (pos[0] < s.length() && s.charAt(pos[0]) != ']') {
            char c = s.charAt(pos[0]);
            if (Character.isDigit(c)) {
                int k = 0;
                while (Character.isDigit(s.charAt(pos[0]))) k = k * 10 + (s.charAt(pos[0]++) - '0');
                pos[0]++;                                // skip '['
                String inner = parse(s, pos);
                pos[0]++;                                // skip ']'
                sb.append(inner.repeat(k));
            } else {
                sb.append(c);
                pos[0]++;
            }
        }
        return sb.toString();
    }

    /** Brute force: repeatedly expand the innermost k[...] (one with no '[' inside) until none remain. */
    public static String innermostFirst(String s) {
        while (s.indexOf('[') >= 0) {
            int close = s.indexOf(']');
            int open = s.lastIndexOf('[', close);        // innermost: the '[' closest before the first ']'
            int start = open;
            while (start > 0 && Character.isDigit(s.charAt(start - 1))) start--;
            int k = Integer.parseInt(s.substring(start, open));
            s = s.substring(0, start) + s.substring(open + 1, close).repeat(k) + s.substring(close + 1);
        }
        return s;
    }
}
