package com.playbook.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

/** The k points closest to the origin (any order). Compare SQUARED distances — no sqrt, no floating point. */
public final class KClosestPoints {
    private KClosestPoints() {}

    static long dist2(int[] p) {
        return (long) p[0] * p[0] + (long) p[1] * p[1];     // long: 2 * (4.6e4)^2 can overflow int for large coords
    }

    /** Sort all by distance. O(n log n). */
    public static int[][] sorting(int[][] points, int k) {
        int[][] c = points.clone();
        Arrays.sort(c, Comparator.comparingLong(KClosestPoints::dist2));
        return Arrays.copyOf(c, k);
    }

    /** MAX-heap of size k: root = farthest of the current k, evicted when a closer point arrives. O(n log k). */
    public static int[][] maxHeap(int[][] points, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingLong(KClosestPoints::dist2).reversed());
        for (int[] p : points) {
            pq.offer(p);
            if (pq.size() > k) pq.poll();
        }
        return pq.toArray(new int[0][]);
    }

    /** Quickselect on distance: partition so the first k are the closest. O(n) expected. */
    public static int[][] quickselect(int[][] points, int k) {
        int[][] c = points.clone();
        int lo = 0, hi = c.length - 1;
        while (lo < hi) {
            int p = partition(c, lo, hi);
            if (p == k - 1) break;
            if (p < k - 1) lo = p + 1;
            else hi = p - 1;
        }
        return Arrays.copyOf(c, k);
    }

    private static int partition(int[][] a, int lo, int hi) {
        swap(a, lo + ThreadLocalRandom.current().nextInt(hi - lo + 1), hi);
        long pivot = dist2(a[hi]);
        int i = lo;
        for (int j = lo; j < hi; j++) if (dist2(a[j]) < pivot) swap(a, i++, j);
        swap(a, i, hi);
        return i;
    }

    private static void swap(int[][] a, int i, int j) {
        int[] t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
