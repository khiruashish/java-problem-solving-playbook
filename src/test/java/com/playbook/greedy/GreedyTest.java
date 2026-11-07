package com.playbook.greedy;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GreedyTest {
    private final Random rnd = new Random(1414);

    @Test void cookies() {
        assertEquals(1, AssignCookies.greedy(new int[]{1, 2, 3}, new int[]{1, 1}));
        assertEquals(2, AssignCookies.greedy(new int[]{1, 2}, new int[]{1, 2, 3}));
        for (int t = 0; t < 300; t++) {
            int[] g = rnd.ints(rnd.nextInt(6), 1, 8).toArray(), s = rnd.ints(rnd.nextInt(6), 1, 8).toArray();
            assertEquals(AssignCookies.bruteForce(g, s), AssignCookies.greedy(g, s));
        }
    }

    private int[][] randomIntervals(int n) {
        int[][] iv = new int[n][];
        for (int i = 0; i < n; i++) {
            int s = rnd.nextInt(20);
            iv[i] = new int[]{s, s + 1 + rnd.nextInt(6)};
        }
        return iv;
    }

    @Test void activities() {
        int[][] iv = {{1, 2}, {3, 4}, {0, 6}, {5, 7}, {8, 9}, {5, 9}};
        assertEquals(4, ActivitySelection.greedy(iv));
        assertEquals(1, ActivitySelection.minRemovals(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}}));
        for (int t = 0; t < 300; t++) {
            int[][] r = randomIntervals(rnd.nextInt(10));
            int expected = ActivitySelection.bruteForce(r);
            assertEquals(expected, ActivitySelection.dp(r));
            assertEquals(expected, ActivitySelection.greedy(r));
        }
    }

    @Test void jumpGame() {
        assertTrue(JumpGame.canReach(new int[]{2, 3, 1, 1, 4}));
        assertFalse(JumpGame.canReach(new int[]{3, 2, 1, 0, 4}));
        assertEquals(2, JumpGame.minJumps(new int[]{2, 3, 1, 1, 4}));
        assertEquals(0, JumpGame.minJumps(new int[]{0}));
        assertEquals(-1, JumpGame.minJumps(new int[]{3, 2, 1, 0, 4}));
        for (int t = 0; t < 400; t++) {
            int[] a = rnd.ints(1 + rnd.nextInt(12), 0, 4).toArray();
            assertEquals(JumpGame.canReachDp(a), JumpGame.canReach(a));
            assertEquals(JumpGame.minJumpsDp(a), JumpGame.minJumps(a), java.util.Arrays.toString(a));
        }
    }

    @Test void gasStation() {
        assertEquals(3, GasStation.greedy(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}));
        assertEquals(-1, GasStation.greedy(new int[]{2, 3, 4}, new int[]{3, 4, 3}));
        for (int t = 0; t < 400; t++) {
            int n = 1 + rnd.nextInt(8);
            int[] gas = rnd.ints(n, 0, 6).toArray(), cost = rnd.ints(n, 0, 6).toArray();
            int brute = GasStation.bruteForce(gas, cost), greedy = GasStation.greedy(gas, cost);
            assertEquals(brute == -1, greedy == -1);
            if (greedy != -1)                                     // rotated so greedy's start is index 0: it must work
                assertEquals(0, GasStation.bruteForce(rotate(gas, greedy), rotate(cost, greedy)));
        }
    }

    private static int[] rotate(int[] a, int s) {
        int[] r = new int[a.length];
        for (int i = 0; i < a.length; i++) r[i] = a[(s + i) % a.length];
        return r;
    }

    @Test void platforms() {
        int[] arr = {900, 940, 950, 1100, 1500, 1800}, dep = {910, 1200, 1120, 1130, 1900, 2000};
        assertEquals(3, MinimumPlatforms.sweep(arr, dep));
        assertEquals(3, MinimumPlatforms.bruteForce(arr, dep));
        assertEquals(2, MinimumPlatforms.sweep(new int[]{900, 1000}, new int[]{1000, 1100}));   // touching → 2
        for (int t = 0; t < 300; t++) {
            int n = 1 + rnd.nextInt(10);
            int[] a = new int[n], d = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(30);
                d[i] = a[i] + rnd.nextInt(10);
            }
            assertEquals(MinimumPlatforms.bruteForce(a, d), MinimumPlatforms.sweep(a, d));
        }
    }

    @Test void fractionalKnapsack() {
        assertEquals(240.0, FractionalKnapsack.maxValue(new int[]{10, 20, 30}, new int[]{60, 100, 120}, 50), 1e-9);
        assertEquals(0.0, FractionalKnapsack.maxValue(new int[]{5}, new int[]{10}, 0), 1e-9);
        assertEquals(10.0, FractionalKnapsack.maxValue(new int[]{5}, new int[]{10}, 100), 1e-9);
    }

    @Test void huffman() {
        Map<Character, Long> freq = Map.of('a', 5L, 'b', 9L, 'c', 12L, 'd', 13L, 'e', 16L, 'f', 45L);
        Map<Character, String> codes = HuffmanCoding.codes(freq);
        assertEquals(224, HuffmanCoding.encodedBits(freq, codes));          // GfG example
        assertEquals(224, HuffmanCoding.minMergeCost(new long[]{5, 9, 12, 13, 16, 45}));
        assertEquals(1, HuffmanCoding.codes(Map.of('x', 7L)).get('x').length());
        for (int t = 0; t < 200; t++) {
            Map<Character, Long> f = new HashMap<>();
            for (int i = 0; i < 1 + rnd.nextInt(8); i++) f.put((char) ('a' + i), 1L + rnd.nextInt(30));
            Map<Character, String> c = HuffmanCoding.codes(f);
            assertPrefixFree(c);
            if (f.size() > 1)
                assertEquals(HuffmanCoding.minMergeCost(f.values().stream().mapToLong(Long::longValue).toArray()),
                        HuffmanCoding.encodedBits(f, c));
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < 30; i++) text.append((char) ('a' + rnd.nextInt(f.size())));
            assertEquals(text.toString(), HuffmanCoding.decode(HuffmanCoding.encode(text.toString(), c), c));
        }
    }

    private static void assertPrefixFree(Map<Character, String> codes) {
        Set<String> all = new HashSet<>(codes.values());
        assertEquals(codes.size(), all.size());
        for (String a : all) for (String b : all) if (!a.equals(b)) assertFalse(b.startsWith(a), a + " prefixes " + b);
    }
}
