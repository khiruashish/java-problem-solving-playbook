package com.playbook.advanced;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AdvancedBatch3Test {

    @Test void sparseTable() {
        int[] a = {5, 2, 4, 7, 1, 3, 6};
        SparseTable st = new SparseTable(a);
        assertEquals(1, st.min(0, 6));
        assertEquals(2, st.min(0, 3));
        assertEquals(4, st.min(2, 3));
        assertEquals(28, st.sum(0, 6));
        assertEquals(12, st.sum(2, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> st.min(3, 2));
        Random rnd = new Random(131);
        for (int t = 0; t < 200; t++) {
            int[] x = new int[1 + rnd.nextInt(40)];
            for (int i = 0; i < x.length; i++) x[i] = rnd.nextInt(201) - 100;
            SparseTable s = new SparseTable(x);
            for (int l = 0; l < x.length; l++)
                for (int r = l; r < x.length; r++) {
                    assertEquals(SparseTable.minBrute(x, l, r), s.min(l, r));
                    long want = 0;
                    for (int i = l; i <= r; i++) want += x[i];
                    assertEquals(want, s.sum(l, r));
                }
        }
    }

    @Test void lazySegmentTree() {
        LazySegmentTree t = new LazySegmentTree(new int[]{1, 2, 3, 4, 5});
        assertEquals(15, t.sum(0, 4));
        t.add(1, 3, 10);                   // [1, 12, 13, 14, 5]
        assertEquals(39, t.sum(1, 3));
        assertEquals(13, t.sum(2, 2));
        t.add(0, 4, -1);                   // [0, 11, 12, 13, 4]
        assertEquals(40, t.sum(0, 4));
        assertEquals(4, t.sum(4, 4));
        Random rnd = new Random(132);
        for (int trial = 0; trial < 100; trial++) {
            int n = 1 + rnd.nextInt(30);
            int[] init = new int[n];
            for (int i = 0; i < n; i++) init[i] = rnd.nextInt(21) - 10;
            LazySegmentTree seg = new LazySegmentTree(init);
            LazySegmentTree.Naive ref = new LazySegmentTree.Naive(init);
            for (int op = 0; op < 300; op++) {
                int l = rnd.nextInt(n), r = l + rnd.nextInt(n - l);
                if (rnd.nextBoolean()) {
                    long v = rnd.nextInt(21) - 10;
                    seg.add(l, r, v);
                    ref.add(l, r, v);
                } else {
                    assertEquals(ref.sum(l, r), seg.sum(l, r));
                }
            }
        }
    }

    @Test void consistentHashing() {
        ConsistentHashing ring = new ConsistentHashing(150);
        List<String> servers = List.of("cache-a", "cache-b", "cache-c", "cache-d");
        servers.forEach(ring::addServer);
        assertEquals(600, ring.size());

        int keys = 20_000;
        Map<String, String> before = new HashMap<>();
        Map<String, Integer> load = new HashMap<>();
        for (int i = 0; i < keys; i++) {
            String k = "user:" + i;
            String s = ring.serverFor(k);
            assertEquals(s, ring.serverForTailMap(k));
            before.put(k, s);
            load.merge(s, 1, Integer::sum);
        }
        // with 150 virtual nodes every server gets a fair share (ideal 25%)
        for (String s : servers) assertTrue(load.get(s) > keys * 0.15 && load.get(s) < keys * 0.35, s + "=" + load.get(s));

        // adding a 5th server moves only keys that now belong to it — roughly 1/5 of them
        ring.addServer("cache-e");
        int moved = 0;
        for (Map.Entry<String, String> e : before.entrySet()) {
            String now = ring.serverFor(e.getKey());
            if (!now.equals(e.getValue())) {
                moved++;
                assertEquals("cache-e", now, "a key may only move TO the new server");
            }
        }
        assertTrue(moved > keys * 0.10 && moved < keys * 0.30, "moved=" + moved);

        // modulo hashing moves most keys when going from 4 to 5 buckets
        int movedModulo = 0;
        for (int i = 0; i < keys; i++) {
            String k = "user:" + i;
            if (ConsistentHashing.moduloBucket(k, 4) != ConsistentHashing.moduloBucket(k, 5)) movedModulo++;
        }
        assertTrue(movedModulo > keys * 0.7, "movedModulo=" + movedModulo);

        // removing the server restores the original assignment exactly
        ring.removeServer("cache-e");
        for (Map.Entry<String, String> e : before.entrySet()) assertEquals(e.getValue(), ring.serverFor(e.getKey()));
        assertThrows(IllegalStateException.class, () -> new ConsistentHashing(10).serverFor("x"));
    }
}
