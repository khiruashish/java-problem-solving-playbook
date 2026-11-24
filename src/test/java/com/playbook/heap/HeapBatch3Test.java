package com.playbook.heap;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HeapBatch3Test {

    @Test void lastStoneWeight() {
        assertEquals(1, LastStoneWeight.maxHeap(new int[]{2, 7, 4, 1, 8, 1}));
        assertEquals(0, LastStoneWeight.maxHeap(new int[]{3, 3}));
        assertEquals(0, LastStoneWeight.maxHeap(new int[0]));
        assertEquals(5, LastStoneWeight.maxHeap(new int[]{5}));
        Random rnd = new Random(81);
        for (int t = 0; t < 1500; t++) {
            int[] s = new int[rnd.nextInt(9)];
            for (int i = 0; i < s.length; i++) s[i] = 1 + rnd.nextInt(10);
            int want = LastStoneWeight.sorting(s);
            assertEquals(want, LastStoneWeight.maxHeap(s), java.util.Arrays.toString(s));
        }
    }

    @Test void reorganizeString() {
        assertTrue(ReorganizeString.isValid("aab", ReorganizeString.maxHeap("aab")));
        assertEquals("aba", ReorganizeString.evenOddFill("aab"));
        assertEquals("", ReorganizeString.maxHeap("aaab"));
        assertEquals("", ReorganizeString.evenOddFill("aaab"));
        assertEquals("", ReorganizeString.maxHeap(""));
        Random rnd = new Random(82);
        for (int t = 0; t < 2000; t++) {
            int n = rnd.nextInt(10);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) sb.append((char) ('a' + rnd.nextInt(1 + rnd.nextInt(4))));
            String s = sb.toString();
            boolean possible = possibleBrute(s);
            String a = ReorganizeString.maxHeap(s), b = ReorganizeString.evenOddFill(s);
            if (possible) {
                assertTrue(ReorganizeString.isValid(s, a), s + " → " + a);
                assertTrue(ReorganizeString.isValid(s, b), s + " → " + b);
            } else {
                assertEquals("", a, s);
                assertEquals("", b, s);
            }
        }
    }

    /** Oracle: try every permutation (n ≤ 9). */
    private static boolean possibleBrute(String s) {
        return permute(s.toCharArray(), 0);
    }

    private static boolean permute(char[] c, int i) {
        if (i == c.length) {
            for (int k = 1; k < c.length; k++) if (c[k] == c[k - 1]) return false;
            return true;
        }
        for (int j = i; j < c.length; j++) {
            boolean dup = false;
            for (int p = i; p < j; p++) if (c[p] == c[j]) { dup = true; break; }   // skip repeated swaps
            if (dup) continue;
            char t = c[i]; c[i] = c[j]; c[j] = t;
            if (i > 0 && c[i] == c[i - 1]) { t = c[i]; c[i] = c[j]; c[j] = t; continue; }  // prune
            boolean ok = permute(c, i + 1);
            t = c[i]; c[i] = c[j]; c[j] = t;
            if (ok) return true;
        }
        return false;
    }

    @Test void smallestRange() {
        List<List<Integer>> lists = List.of(List.of(4, 10, 15, 24, 26), List.of(0, 9, 12, 20), List.of(5, 18, 22, 30));
        assertArrayEquals(new int[]{20, 24}, SmallestRange.minHeap(lists));
        assertArrayEquals(new int[]{20, 24}, SmallestRange.slidingWindow(lists));
        assertArrayEquals(new int[]{20, 24}, SmallestRange.bruteForce(lists));
        assertArrayEquals(new int[]{1, 1}, SmallestRange.minHeap(List.of(List.of(1, 2, 3), List.of(1, 2, 3), List.of(1, 2, 3))));
        assertArrayEquals(new int[]{5, 5}, SmallestRange.minHeap(List.of(List.of(5))));
        Random rnd = new Random(83);
        for (int t = 0; t < 800; t++) {
            int k = 1 + rnd.nextInt(4);
            List<List<Integer>> ls = new ArrayList<>();
            for (int i = 0; i < k; i++) {
                List<Integer> l = new ArrayList<>();
                int v = rnd.nextInt(5) - 2;
                for (int j = 0, len = 1 + rnd.nextInt(5); j < len; j++) { l.add(v); v += rnd.nextInt(4); }
                ls.add(l);
            }
            int[] want = SmallestRange.bruteForce(ls);
            assertArrayEquals(want, SmallestRange.minHeap(ls), ls.toString());
            assertArrayEquals(want, SmallestRange.slidingWindow(ls), ls.toString());
        }
    }
}
