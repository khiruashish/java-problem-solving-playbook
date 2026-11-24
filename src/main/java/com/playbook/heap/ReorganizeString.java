package com.playbook.heap;

import java.util.PriorityQueue;

/**
 * Rearrange s so that no two adjacent characters are equal; return "" if impossible.
 * Possible iff the most frequent char appears at most ceil(n / 2) = (n + 1) / 2 times.
 */
public final class ReorganizeString {
    private ReorganizeString() {}

    /**
     * Greedy with a max-heap of (count, char): always place the most frequent char that differs from the previous one.
     * Hold the just-used char out of the heap for one step so it can't be placed twice in a row. O(n log σ).
     */
    public static String maxHeap(String s) {
        int[] count = counts(s);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? b[0] - a[0] : a[1] - b[1]);
        for (int c = 0; c < 26; c++) if (count[c] > 0) pq.offer(new int[]{count[c], c});
        StringBuilder sb = new StringBuilder(s.length());
        int[] held = null;                                    // the char used last step, waiting to re-enter
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            sb.append((char) ('a' + top[1]));
            top[0]--;
            if (held != null && held[0] > 0) pq.offer(held); // previous char may be used again now
            held = top;
        }
        return sb.length() == s.length() ? sb.toString() : "";   // leftover in `held` means it was impossible
    }

    /**
     * Even-index fill: if possible, put the most frequent char at indexes 0, 2, 4, ..., then the rest of the chars
     * continuing at even indexes and wrapping to 1, 3, 5, ... Equal chars end up ≥ 2 apart. O(n + σ).
     */
    public static String evenOddFill(String s) {
        int n = s.length();
        int[] count = counts(s);
        int most = 0;
        for (int c = 1; c < 26; c++) if (count[c] > count[most]) most = c;
        if (count[most] > (n + 1) / 2) return "";
        char[] out = new char[n];
        int i = 0;
        while (count[most] > 0) { out[i] = (char) ('a' + most); i += 2; count[most]--; }
        for (int c = 0; c < 26; c++) {
            while (count[c] > 0) {
                if (i >= n) i = 1;                           // even slots full: switch to odd slots
                out[i] = (char) ('a' + c);
                i += 2;
                count[c]--;
            }
        }
        return new String(out);
    }

    /** Validity check used by tests: a permutation of s with no equal neighbours. */
    public static boolean isValid(String original, String arranged) {
        if (original.length() != arranged.length()) return false;
        if (!java.util.Arrays.equals(counts(original), counts(arranged))) return false;
        for (int i = 1; i < arranged.length(); i++) if (arranged.charAt(i) == arranged.charAt(i - 1)) return false;
        return true;
    }

    private static int[] counts(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            if (c < 'a' || c > 'z') throw new IllegalArgumentException("lowercase a-z only");
            count[c - 'a']++;
        }
        return count;
    }
}
