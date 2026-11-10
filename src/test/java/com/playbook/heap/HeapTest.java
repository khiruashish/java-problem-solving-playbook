package com.playbook.heap;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HeapTest {
    private final Random rnd = new Random(1111);

    @Test void kthLargest() {
        KthLargest s = new KthLargest(3, new int[]{4, 5, 8, 2});
        assertEquals(4, s.add(3));
        assertEquals(5, s.add(5));
        assertEquals(5, s.add(10));
        assertEquals(8, s.add(9));
        assertEquals(8, s.add(4));
        for (int t = 0; t < 300; t++) {
            int[] a = rnd.ints(1 + rnd.nextInt(30), -50, 50).toArray();
            int k = 1 + rnd.nextInt(a.length);
            assertEquals(KthLargest.sorting(a, k), KthLargest.minHeap(a, k));
        }
    }

    @Test void topKWords() {
        String[] w = {"i", "love", "leetcode", "i", "love", "coding"};
        assertEquals(List.of("i", "love"), TopKFrequentWords.heap(w, 2));
        String[] w2 = {"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"};
        assertEquals(List.of("the", "is", "sunny", "day"), TopKFrequentWords.heap(w2, 4));
        String[] pool = {"a", "b", "c", "d", "e", "f"};
        for (int t = 0; t < 300; t++) {
            String[] r = rnd.ints(1 + rnd.nextInt(25), 0, pool.length).mapToObj(i -> pool[i]).toArray(String[]::new);
            int distinct = (int) Arrays.stream(r).distinct().count();
            int k = 1 + rnd.nextInt(distinct);
            assertEquals(TopKFrequentWords.sorting(r, k), TopKFrequentWords.heap(r, k));
        }
    }

    @Test void kClosest() {
        int[][] pts = {{3, 3}, {5, -1}, {-2, 4}};
        assertEquals(List.of(List.of(-2, 4), List.of(3, 3)), sortedPoints(KClosestPoints.maxHeap(pts, 2)));
        for (int t = 0; t < 300; t++) {
            int n = 1 + rnd.nextInt(20);
            int[][] p = new int[n][];
            for (int i = 0; i < n; i++) p[i] = new int[]{rnd.nextInt(41) - 20, rnd.nextInt(41) - 20};
            int k = 1 + rnd.nextInt(n);
            long[] expected = distances(KClosestPoints.sorting(p, k));      // compare distance multisets (ties may differ)
            assertArrayEquals(expected, distances(KClosestPoints.maxHeap(p, k)));
            assertArrayEquals(expected, distances(KClosestPoints.quickselect(p, k)));
        }
    }

    private static long[] distances(int[][] pts) {
        return Arrays.stream(pts).mapToLong(KClosestPoints::dist2).sorted().toArray();
    }

    private static List<List<Integer>> sortedPoints(int[][] pts) {
        return Arrays.stream(pts).map(p -> List.of(p[0], p[1]))
                .sorted((x, y) -> x.get(0) != y.get(0).intValue() ? x.get(0) - y.get(0) : x.get(1) - y.get(1)).toList();
    }

    @Test void mergeKArrays() {
        int[][] a = {{1, 4, 7}, {2, 5, 8}, {}, {0, 9}};
        assertArrayEquals(new int[]{0, 1, 2, 4, 5, 7, 8, 9}, MergeKSortedArrays.heap(a));
        assertArrayEquals(new int[0], MergeKSortedArrays.heap(new int[0][]));
        for (int t = 0; t < 200; t++) {
            int k = 1 + rnd.nextInt(6);
            int[][] arrs = new int[k][];
            for (int i = 0; i < k; i++) {
                arrs[i] = rnd.ints(rnd.nextInt(8), -20, 20).toArray();
                Arrays.sort(arrs[i]);
            }
            int[] expected = MergeKSortedArrays.concatAndSort(arrs);
            assertArrayEquals(expected, MergeKSortedArrays.heap(arrs));
            assertArrayEquals(expected, MergeKSortedArrays.divideAndConquer(arrs));
        }
    }

    @Test void taskScheduler() {
        assertEquals(8, TaskScheduler.formula("AAABBB".toCharArray(), 2));
        assertEquals(6, TaskScheduler.formula("AAABBB".toCharArray(), 0));
        assertEquals(16, TaskScheduler.formula("AAAAAABCDEFG".toCharArray(), 2));
        assertEquals(8, TaskScheduler.simulate("AAABBB".toCharArray(), 2));
        for (int t = 0; t < 300; t++) {
            char[] tasks = new char[1 + rnd.nextInt(20)];
            for (int i = 0; i < tasks.length; i++) tasks[i] = (char) ('A' + rnd.nextInt(5));
            int n = rnd.nextInt(5);
            assertEquals(TaskScheduler.formula(tasks, n), TaskScheduler.simulate(tasks, n), new String(tasks) + " n=" + n);
        }
    }

    @Test void medianOfStream() {
        MedianOfStream m = new MedianOfStream();
        m.addNum(1);
        m.addNum(2);
        assertEquals(1.5, m.findMedian());
        m.addNum(3);
        assertEquals(2.0, m.findMedian());
        assertThrows(IllegalStateException.class, () -> new MedianOfStream().findMedian());
        MedianOfStream fast = new MedianOfStream();
        MedianOfStream.SortedList slow = new MedianOfStream.SortedList();
        for (int i = 0; i < 2000; i++) {
            int x = rnd.nextInt(201) - 100;
            fast.addNum(x);
            slow.addNum(x);
            assertEquals(slow.findMedian(), fast.findMedian(), 1e-9);
        }
    }

    @Test void binaryHeap() {
        BinaryHeap h = new BinaryHeap();
        PriorityQueue<Integer> ref = new PriorityQueue<>();
        for (int i = 0; i < 5000; i++) {
            if (ref.isEmpty() || rnd.nextInt(3) > 0) {
                int x = rnd.nextInt(1000) - 500;
                h.push(x);
                ref.offer(x);
            } else {
                assertEquals(ref.poll(), h.pop());
            }
            assertEquals(ref.size(), h.size());
            if (!ref.isEmpty()) assertEquals(ref.peek(), h.peek());
        }
        assertThrows(NoSuchElementException.class, () -> new BinaryHeap().pop());
        for (int t = 0; t < 200; t++) {
            int[] a = rnd.ints(rnd.nextInt(40), -30, 30).toArray(), expected = a.clone(), b = a.clone();
            Arrays.sort(expected);
            BinaryHeap built = BinaryHeap.heapify(a);
            int[] drained = new int[a.length];
            for (int i = 0; i < a.length; i++) drained[i] = built.pop();
            assertArrayEquals(expected, drained);
            BinaryHeap.heapSort(b);
            assertArrayEquals(expected, b);
        }
    }

    @Test void binaryHeapEmptyState() {
        BinaryHeap h = new BinaryHeap();
        assertTrue(h.isEmpty());
        assertThrows(NoSuchElementException.class, h::peek);
        h.push(4);
        h.push(-2);
        assertFalse(h.isEmpty());
        assertEquals(-2, h.peek());
        h.pop();
        h.pop();
        assertTrue(h.isEmpty());
        assertTrue(BinaryHeap.heapify(new int[0]).isEmpty());
    }
}
