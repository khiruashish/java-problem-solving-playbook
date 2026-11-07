package com.playbook.hashing;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HashingTest {
    private final Random rnd = new Random(2026);

    private int[] randomArray(int maxLen, int lo, int hi) {
        return rnd.ints(rnd.nextInt(maxLen + 1), lo, hi).toArray();
    }

    @Test void containsDuplicate() {
        assertTrue(ContainsDuplicate.hashSet(new int[]{1, 2, 3, 1}));
        assertFalse(ContainsDuplicate.hashSet(new int[]{1, 2, 3, 4}));
        assertFalse(ContainsDuplicate.hashSet(new int[0]));
        for (int t = 0; t < 300; t++) {
            int[] a = randomArray(12, 0, 15);
            boolean expected = ContainsDuplicate.bruteForce(a);
            assertEquals(expected, ContainsDuplicate.sorting(a));
            assertEquals(expected, ContainsDuplicate.hashSet(a));
            assertEquals(expected, ContainsDuplicate.streams(a));
        }
        assertTrue(ContainsDuplicate.withinDistance(new int[]{1, 2, 3, 1}, 3));
        assertFalse(ContainsDuplicate.withinDistance(new int[]{1, 2, 3, 1, 2, 3}, 2));
        assertTrue(ContainsDuplicate.withinDistance(new int[]{1, 0, 1, 1}, 1));
    }

    @Test void intersection() {
        assertArrayEquals(new int[]{2}, ArrayIntersection.unique(new int[]{1, 2, 2, 1}, new int[]{2, 2}));
        assertArrayEquals(new int[]{4, 9}, ArrayIntersection.unique(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}));
        assertArrayEquals(new int[]{4, 9}, ArrayIntersection.retainAll(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}));
        assertArrayEquals(new int[]{4, 9}, ArrayIntersection.bruteForceUnique(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}));
        assertArrayEquals(new int[]{2, 2}, ArrayIntersection.withCounts(new int[]{1, 2, 2, 1}, new int[]{2, 2}));
        assertArrayEquals(new int[]{4, 9}, ArrayIntersection.withCounts(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}));
        assertArrayEquals(new int[]{2, 2, 5}, ArrayIntersection.sortedTwoPointers(new int[]{1, 2, 2, 3, 5}, new int[]{2, 2, 2, 5, 6}));
        assertArrayEquals(new int[0], ArrayIntersection.unique(new int[0], new int[]{1}));
    }

    @Test void subarraySumK() {
        assertEquals(2, SubarraySumEqualsK.prefixSumMap(new int[]{1, 1, 1}, 2));
        assertEquals(2, SubarraySumEqualsK.prefixSumMap(new int[]{1, 2, 3}, 3));
        assertEquals(4, SubarraySumEqualsK.prefixSumMap(new int[]{3, 4, 7, 2, -3, 1, 4, 2}, 7));
        assertEquals(0, SubarraySumEqualsK.slidingWindowNonNegative(new int[]{1}, 0));   // no empty-window miscount
        for (int t = 0; t < 400; t++) {
            int[] a = randomArray(15, -4, 5);
            int k = rnd.nextInt(9) - 4;
            assertEquals(SubarraySumEqualsK.bruteForce(a, k), SubarraySumEqualsK.prefixSumMap(a, k));
            int[] nonNeg = randomArray(15, 0, 4);
            int kk = rnd.nextInt(7);
            assertEquals(SubarraySumEqualsK.bruteForce(nonNeg, kk), SubarraySumEqualsK.slidingWindowNonNegative(nonNeg, kk),
                    java.util.Arrays.toString(nonNeg) + " k=" + kk);
        }
    }

    @Test void longestConsecutive() {
        assertEquals(4, LongestConsecutiveSequence.hashSet(new int[]{100, 4, 200, 1, 3, 2}));
        assertEquals(9, LongestConsecutiveSequence.hashSet(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
        assertEquals(0, LongestConsecutiveSequence.hashSet(new int[0]));
        assertEquals(2, LongestConsecutiveSequence.hashSet(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE - 1}));
        assertEquals(2, LongestConsecutiveSequence.sorting(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE + 1}));
        for (int t = 0; t < 300; t++) {
            int[] a = randomArray(15, -10, 10);
            int expected = LongestConsecutiveSequence.bruteForce(a);
            assertEquals(expected, LongestConsecutiveSequence.sorting(a));
            assertEquals(expected, LongestConsecutiveSequence.hashSet(a));
        }
    }

    @Test void topKFrequent() {
        int[] a = {1, 1, 1, 2, 2, 3};
        assertEquals(List.of(1, 2), TopKFrequent.sorting(a, 2));
        assertEquals(List.of(1, 2), TopKFrequent.heap(a, 2));
        assertEquals(List.of(1, 2), TopKFrequent.bucketSort(a, 2));
        assertEquals(List.of(1), TopKFrequent.bucketSort(new int[]{1}, 1));
        for (int t = 0; t < 300; t++) {
            int[] r = randomArray(20, 0, 8);
            if (r.length == 0) continue;
            int distinct = (int) java.util.Arrays.stream(r).distinct().count();
            int k = 1 + rnd.nextInt(distinct);
            List<Integer> expected = TopKFrequent.sorting(r, k);
            assertEquals(expected, TopKFrequent.heap(r, k));
            assertEquals(expected, TopKFrequent.bucketSort(r, k));
        }
    }

    @Test void longestZeroSum() {
        assertEquals(5, LongestZeroSumSubarray.longestZeroSum(new int[]{15, -2, 2, -8, 1, 7, 10, 23}));
        assertEquals(0, LongestZeroSumSubarray.longestZeroSum(new int[]{1, 2, 3}));
        assertEquals(2, LongestZeroSumSubarray.longestEqualZerosOnes(new int[]{0, 1}));
        assertEquals(6, LongestZeroSumSubarray.longestEqualZerosOnes(new int[]{0, 1, 1, 1, 1, 1, 0, 0, 0}));
        for (int t = 0; t < 400; t++) {
            int[] a = randomArray(15, -4, 5);
            int k = rnd.nextInt(7) - 3;
            assertEquals(LongestZeroSumSubarray.bruteForce(a, k), LongestZeroSumSubarray.longestWithSum(a, k));
        }
    }

    @Test void fourSum() {
        assertEquals(2, FourSumCount.meetInTheMiddle(new int[]{1, 2}, new int[]{-2, -1}, new int[]{-1, 2}, new int[]{0, 2}));
        assertEquals(1, FourSumCount.meetInTheMiddle(new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0}));
        for (int t = 0; t < 100; t++) {
            int n = rnd.nextInt(6);
            int[] a = rnd.ints(n, -3, 4).toArray(), b = rnd.ints(n, -3, 4).toArray(),
                    c = rnd.ints(n, -3, 4).toArray(), d = rnd.ints(n, -3, 4).toArray();
            int expected = FourSumCount.bruteForce(a, b, c, d);
            assertEquals(expected, FourSumCount.threeLoops(a, b, c, d));
            assertEquals(expected, FourSumCount.meetInTheMiddle(a, b, c, d));
        }
    }

    /** Key whose hashCode always collides — forces long chains. */
    private record BadKey(int id) {
        @Override public int hashCode() { return 7; }
    }

    @Test void myHashMap() {
        MyHashMap<String, Integer> m = new MyHashMap<>();
        assertNull(m.put("a", 1));
        assertEquals(1, m.put("a", 2));
        assertEquals(2, m.get("a"));
        assertNull(m.get("missing"));
        m.put(null, 0);                                          // null key supported
        assertEquals(0, m.get(null));
        m.put("n", null);
        assertTrue(m.containsKey("n"));                          // present but mapped to null
        assertFalse(m.containsKey("zzz"));
        assertEquals(3, m.size());
        assertEquals(2, m.remove("a"));
        assertNull(m.remove("a"));
        assertEquals(2, m.size());

        // differential test against java.util.HashMap, through several resizes
        MyHashMap<Integer, Integer> mine = new MyHashMap<>(2);
        Map<Integer, Integer> ref = new HashMap<>();
        for (int i = 0; i < 20_000; i++) {
            int key = rnd.nextInt(3000) - 1500, op = rnd.nextInt(10);
            if (op < 6) assertEquals(ref.put(key, i), mine.put(key, i));
            else if (op < 8) assertEquals(ref.remove(key), mine.remove(key));
            else assertEquals(ref.get(key), mine.get(key));
            assertEquals(ref.size(), mine.size());
        }
        assertTrue(mine.capacity() >= mine.size() / 0.75);

        MyHashMap<BadKey, Integer> colliding = new MyHashMap<>();
        for (int i = 0; i < 500; i++) colliding.put(new BadKey(i), i);
        for (int i = 0; i < 500; i++) assertEquals(i, colliding.get(new BadKey(i)));
        assertEquals(499, colliding.remove(new BadKey(499)));
        assertEquals(499, colliding.size());
    }
}
