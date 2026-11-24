package com.playbook.hashing;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HashingBatch3Test {

    @Test void isomorphicStrings() {
        String[][] yes = {{"egg", "add"}, {"paper", "title"}, {"", ""}, {"a", "b"}, {"ab", "ba"}};
        String[][] no = {{"foo", "bar"}, {"badc", "baba"}, {"ab", "aa"}, {"abc", "ab"}};
        for (String[] p : yes) {
            assertTrue(IsomorphicStrings.twoMaps(p[0], p[1]), p[0] + "/" + p[1]);
            assertTrue(IsomorphicStrings.lastSeen(p[0], p[1]));
            assertTrue(IsomorphicStrings.canonical(p[0], p[1]));
        }
        for (String[] p : no) {
            assertFalse(IsomorphicStrings.twoMaps(p[0], p[1]), p[0] + "/" + p[1]);
            assertFalse(IsomorphicStrings.lastSeen(p[0], p[1]));
            assertFalse(IsomorphicStrings.canonical(p[0], p[1]));
        }
        Random rnd = new Random(51);
        for (int t = 0; t < 2000; t++) {
            int n = rnd.nextInt(7);
            String s = random(rnd, n, 3), u = random(rnd, n, 3);
            boolean want = IsomorphicStrings.canonical(s, u);
            assertEquals(want, IsomorphicStrings.twoMaps(s, u), s + "/" + u);
            assertEquals(want, IsomorphicStrings.lastSeen(s, u), s + "/" + u);
        }
    }

    @Test void distinctInWindow() {
        int[] a = {1, 2, 1, 3, 4, 2, 3};
        assertArrayEquals(new int[]{3, 4, 4, 3}, DistinctInWindow.slidingWindow(a, 4));
        assertArrayEquals(new int[]{3, 4, 4, 3}, DistinctInWindow.countsArray(a, 4, 4));
        assertArrayEquals(new int[0], DistinctInWindow.slidingWindow(a, 8));
        assertArrayEquals(new int[]{1, 1, 1}, DistinctInWindow.slidingWindow(new int[]{7, 7, 7}, 1));
        Random rnd = new Random(52);
        for (int t = 0; t < 800; t++) {
            int[] x = new int[1 + rnd.nextInt(12)];
            for (int i = 0; i < x.length; i++) x[i] = rnd.nextInt(4);
            int k = 1 + rnd.nextInt(x.length);
            int[] want = DistinctInWindow.bruteForce(x, k);
            assertArrayEquals(want, DistinctInWindow.slidingWindow(x, k));
            assertArrayEquals(want, DistinctInWindow.countsArray(x, k, 3));
        }
    }

    @Test void subarraysDivisibleByK() {
        int[] a = {4, 5, 0, -2, -3, 1};
        assertEquals(7, SubarraysDivisibleByK.prefixRemainders(a, 5));
        assertEquals(7, SubarraysDivisibleByK.prefixRemaindersMap(a, 5));
        assertEquals(7, SubarraysDivisibleByK.combinatorial(a, 5));
        assertEquals(7, SubarraysDivisibleByK.bruteForce(a, 5));
        assertEquals(0, SubarraysDivisibleByK.prefixRemainders(new int[]{5}, 9));
        assertEquals(1, SubarraysDivisibleByK.prefixRemainders(new int[]{-5}, 5));
        Random rnd = new Random(53);
        for (int t = 0; t < 1000; t++) {
            int[] x = new int[rnd.nextInt(12)];
            for (int i = 0; i < x.length; i++) x[i] = rnd.nextInt(21) - 10;
            int k = 1 + rnd.nextInt(7);
            long want = SubarraysDivisibleByK.bruteForce(x, k);
            assertEquals(want, SubarraysDivisibleByK.prefixRemainders(x, k));
            assertEquals(want, SubarraysDivisibleByK.prefixRemaindersMap(x, k));
            assertEquals(want, SubarraysDivisibleByK.combinatorial(x, k));
        }
    }

    private static String random(Random rnd, int n, int alphabet) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append((char) ('a' + rnd.nextInt(alphabet)));
        return sb.toString();
    }
}
