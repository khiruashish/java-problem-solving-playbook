package com.playbook.advanced;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AdvancedTest {
    private final Random rnd = new Random(1616);

    private String word(int maxLen, String alphabet) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1 + rnd.nextInt(maxLen); i > 0; i--) sb.append(alphabet.charAt(rnd.nextInt(alphabet.length())));
        return sb.toString();
    }

    @Test void trie() {
        Trie t = new Trie();
        t.insert("apple");
        assertTrue(t.search("apple"));
        assertFalse(t.search("app"));
        assertTrue(t.startsWith("app"));
        t.insert("app");
        assertTrue(t.search("app"));
        assertEquals(2, t.countWordsWithPrefix("ap"));
        assertTrue(t.delete("apple"));
        assertFalse(t.search("apple"));
        assertTrue(t.search("app"));
        assertFalse(t.delete("apple"));
        Trie r = new Trie();
        Set<String> ref = new HashSet<>();
        for (int i = 0; i < 3000; i++) {
            String w = word(5, "abc");
            int op = rnd.nextInt(4);
            if (op < 2) {
                r.insert(w);
                ref.add(w);
            } else if (op == 2) assertEquals(ref.remove(w), r.delete(w));
            String p = word(3, "abc");
            assertEquals(ref.contains(w), r.search(w));
            assertEquals(ref.stream().filter(s -> s.startsWith(p)).count(), r.countWordsWithPrefix(p));
            assertEquals(ref.stream().anyMatch(s -> s.startsWith(p)), r.startsWith(p));
        }
    }

    @Test void trieLcp() {
        assertEquals("fl", Trie.longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        assertEquals("", Trie.longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
        assertEquals("ab", Trie.longestCommonPrefix(new String[]{"ab", "abc"}));
        for (int t = 0; t < 300; t++) {
            String[] w = new String[1 + rnd.nextInt(5)];
            for (int i = 0; i < w.length; i++) w[i] = word(5, "ab");
            assertEquals(com.playbook.strings.LongestCommonPrefix.verticalScan(w), Trie.longestCommonPrefix(w));
        }
    }

    @Test void wordSearch() {
        char[][] board = {"oaan".toCharArray(), "etae".toCharArray(), "ihkr".toCharArray(), "iflv".toCharArray()};
        String[] words = {"oath", "pea", "eat", "rain"};
        assertEquals(List.of("eat", "oath"), WordSearchII.trie(board, words));
        assertEquals(List.of("eat", "oath"), WordSearchII.perWord(board, words));
        for (int t = 0; t < 200; t++) {
            int r = 1 + rnd.nextInt(4), c = 1 + rnd.nextInt(4);
            char[][] b = new char[r][c];
            for (char[] row : b) for (int j = 0; j < c; j++) row[j] = "abc".charAt(rnd.nextInt(3));
            String[] ws = new String[1 + rnd.nextInt(8)];
            for (int i = 0; i < ws.length; i++) ws[i] = word(5, "abc");
            assertEquals(WordSearchII.perWord(b, ws), WordSearchII.trie(b, ws));
        }
    }

    @Test void autocomplete() {
        String[] products = {"mobile", "mouse", "moneypot", "monitor", "mousepad"};
        List<List<String>> expected = List.of(
                List.of("mobile", "moneypot", "monitor"), List.of("mobile", "moneypot", "monitor"),
                List.of("mouse", "mousepad"), List.of("mouse", "mousepad"), List.of("mouse", "mousepad"));
        assertEquals(expected, Autocomplete.trie(products, "mouse"));
        assertEquals(expected, Autocomplete.binarySearch(products, "mouse"));
        for (int t = 0; t < 300; t++) {
            String[] p = new String[1 + rnd.nextInt(10)];
            for (int i = 0; i < p.length; i++) p[i] = word(5, "ab");
            String s = word(5, "ab");
            List<List<String>> ref = Autocomplete.bruteForce(p, s);
            assertEquals(ref, Autocomplete.trie(p, s));
            assertEquals(ref, Autocomplete.binarySearch(p, s));
        }
    }

    @Test void segmentTree() {
        for (int t = 0; t < 100; t++) {
            int n = 1 + rnd.nextInt(40);
            int[] a = rnd.ints(n, -50, 50).toArray();
            SegmentTree st = new SegmentTree(a);
            for (int q = 0; q < 200; q++) {
                if (rnd.nextBoolean()) {
                    int i = rnd.nextInt(n), v = rnd.nextInt(100) - 50;
                    a[i] = v;
                    st.update(i, v);
                } else {
                    int l = rnd.nextInt(n), r = l + rnd.nextInt(n - l);
                    long s = 0;
                    int m = Integer.MAX_VALUE;
                    for (int k = l; k <= r; k++) {
                        s += a[k];
                        m = Math.min(m, a[k]);
                    }
                    assertEquals(s, st.sum(l, r));
                    assertEquals(m, st.min(l, r));
                }
            }
        }
    }

    @Test void fenwick() {
        for (int t = 0; t < 100; t++) {
            int n = 1 + rnd.nextInt(40);
            int[] a = rnd.ints(n, -50, 50).toArray();
            FenwickTree f;
            if (rnd.nextBoolean()) f = FenwickTree.of(a);        // O(n) build
            else {                                               // or n point updates: both must give the same tree
                f = new FenwickTree(n);
                for (int i = 0; i < n; i++) f.add(i, a[i]);
            }
            for (int q = 0; q < 200; q++) {
                if (rnd.nextBoolean()) {
                    int i = rnd.nextInt(n), d = rnd.nextInt(21) - 10;
                    a[i] += d;
                    f.add(i, d);
                } else {
                    int l = rnd.nextInt(n), r = l + rnd.nextInt(n - l);
                    long s = 0;
                    for (int k = l; k <= r; k++) s += a[k];
                    assertEquals(s, f.rangeSum(l, r));
                }
            }
        }
        for (int t = 0; t < 200; t++) {
            int[] a = rnd.ints(rnd.nextInt(30), 0, 20).toArray();
            assertEquals(com.playbook.searchsort.CountInversions.bruteForce(a), FenwickTree.countInversions(a, 19));
        }
    }

    /** Slow but obviously-correct LFU reference: scan for (min count, oldest last use). */
    private static final class NaiveLfu {
        final int cap;
        final Map<Integer, int[]> m = new HashMap<>();       // key → {value, count, lastUsedTick}
        int tick;

        NaiveLfu(int cap) {
            this.cap = cap;
        }

        int get(int k) {
            int[] e = m.get(k);
            if (e == null) return -1;
            e[1]++;
            e[2] = ++tick;
            return e[0];
        }

        void put(int k, int v) {
            if (cap <= 0) return;
            int[] e = m.get(k);
            if (e != null) {
                e[0] = v;
                e[1]++;
                e[2] = ++tick;
                return;
            }
            if (m.size() == cap) {
                int victim = -1;
                for (Map.Entry<Integer, int[]> x : m.entrySet()) {
                    int[] y = x.getValue();
                    if (victim == -1 || y[1] < m.get(victim)[1] || (y[1] == m.get(victim)[1] && y[2] < m.get(victim)[2]))
                        victim = x.getKey();
                }
                m.remove(victim);
            }
            m.put(k, new int[]{v, 1, ++tick});
        }
    }

    @Test void lfu() {
        LfuCache c = new LfuCache(2);
        c.put(1, 1);
        c.put(2, 2);
        assertEquals(1, c.get(1));
        c.put(3, 3);                                        // evicts 2 (count 1) — 1 has count 2
        assertEquals(-1, c.get(2));
        assertEquals(3, c.get(3));
        c.put(4, 4);                                        // 1 and 3 both count 2; 1 is older → evicted
        assertEquals(-1, c.get(1));
        assertEquals(3, c.get(3));
        assertEquals(4, c.get(4));
        for (int t = 0; t < 50; t++) {
            int cap = rnd.nextInt(5);
            LfuCache fast = new LfuCache(cap);
            NaiveLfu slow = new NaiveLfu(cap);
            for (int i = 0; i < 400; i++) {
                int k = rnd.nextInt(8);
                if (rnd.nextBoolean()) assertEquals(slow.get(k), fast.get(k));
                else {
                    int v = rnd.nextInt(100);
                    fast.put(k, v);
                    slow.put(k, v);
                }
            }
            assertEquals(slow.m.size(), fast.size());
        }
    }
}
