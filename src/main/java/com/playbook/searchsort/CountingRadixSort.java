package com.playbook.searchsort;

/**
 * Non-comparison sorts. Comparison sorts can't beat Ω(n log n); these beat it by using the VALUES as indexes.
 * Counting sort: O(n + range). Radix sort (LSD, base 256): O(d · (n + 256)) with d = 4 bytes for an int.
 */
public final class CountingRadixSort {
    private CountingRadixSort() {}

    /**
     * Counting sort for any ints with a small spread (max − min). Stable variant: prefix sums turn counts into end
     * positions, then place elements from right to left. O(n + k) time and space, k = max − min + 1.
     */
    public static int[] countingSort(int[] a) {
        if (a.length == 0) return new int[0];
        int min = a[0], max = a[0];
        for (int x : a) { min = Math.min(min, x); max = Math.max(max, x); }
        long span = (long) max - min + 1;
        if (span > 50_000_000) throw new IllegalArgumentException("range too large for counting sort: " + span);
        int[] count = new int[(int) span];
        for (int x : a) count[x - min]++;
        for (int i = 1; i < count.length; i++) count[i] += count[i - 1];   // count[v] = #elements ≤ v
        int[] out = new int[a.length];
        for (int i = a.length - 1; i >= 0; i--) out[--count[a[i] - min]] = a[i];   // right→left keeps it stable
        return out;
    }

    /**
     * LSD radix sort on 32-bit ints, one byte (base 256) per pass, using stable counting sort on each byte.
     * Negative numbers: flip the sign bit (x ^ 0x80000000) so that two's complement orders like unsigned.
     * 4 passes · O(n + 256) = O(n). Stable.
     */
    public static int[] radixSort(int[] a) {
        int n = a.length;
        int[] src = new int[n], dst = new int[n];
        for (int i = 0; i < n; i++) src[i] = a[i] ^ Integer.MIN_VALUE;     // map signed order onto unsigned order
        for (int shift = 0; shift < 32; shift += 8) {
            int[] count = new int[257];
            for (int x : src) count[((x >>> shift) & 0xFF) + 1]++;
            for (int b = 0; b < 256; b++) count[b + 1] += count[b];          // count[b] = start index of bucket b
            for (int x : src) dst[count[(x >>> shift) & 0xFF]++] = x;      // left→right into start positions: stable
            int[] t = src; src = dst; dst = t;
        }
        for (int i = 0; i < n; i++) src[i] ^= Integer.MIN_VALUE;           // undo the sign flip
        return src;
    }

    /** Counting sort on a tiny alphabet: sort the letters of a lowercase word in O(n) (the anagram-key trick). */
    public static String sortLetters(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            if (c < 'a' || c > 'z') throw new IllegalArgumentException("lowercase a-z only: " + c);
            count[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < 26; i++) sb.repeat((char) ('a' + i), count[i]);
        return sb.toString();
    }
}
