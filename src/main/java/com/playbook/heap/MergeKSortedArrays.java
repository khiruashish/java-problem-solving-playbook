package com.playbook.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/** Merge k sorted int arrays into one sorted array. N = total elements. */
public final class MergeKSortedArrays {
    private MergeKSortedArrays() {}

    /** Concatenate and sort. O(N log N). */
    public static int[] concatAndSort(int[][] arrays) {
        int[] all = Arrays.stream(arrays).flatMapToInt(Arrays::stream).toArray();
        Arrays.sort(all);
        return all;
    }

    /**
     * Min-heap of entries {value, arrayIndex, elementIndex}: always output the smallest current head, then push that
     * array's next element. Heap never exceeds k. O(N log k) time, O(k) extra.
     */
    public static int[] heap(int[][] arrays) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> Integer.compare(x[0], y[0]));
        int total = 0;
        for (int i = 0; i < arrays.length; i++) {
            total += arrays[i].length;
            if (arrays[i].length > 0) pq.offer(new int[]{arrays[i][0], i, 0});
        }
        int[] out = new int[total];
        int w = 0;
        while (!pq.isEmpty()) {
            int[] e = pq.poll();
            out[w++] = e[0];
            int next = e[2] + 1;
            if (next < arrays[e[1]].length) pq.offer(new int[]{arrays[e[1]][next], e[1], next});
        }
        return out;
    }

    /** Divide & conquer pairwise merging. O(N log k), no heap. */
    public static int[] divideAndConquer(int[][] arrays) {
        if (arrays.length == 0) return new int[0];
        return dc(arrays, 0, arrays.length - 1);
    }

    private static int[] dc(int[][] a, int lo, int hi) {
        if (lo == hi) return a[lo];
        int mid = (lo + hi) >>> 1;
        return mergeTwo(dc(a, lo, mid), dc(a, mid + 1, hi));
    }

    static int[] mergeTwo(int[] x, int[] y) {
        int[] out = new int[x.length + y.length];
        int i = 0, j = 0, k = 0;
        while (i < x.length && j < y.length) out[k++] = x[i] <= y[j] ? x[i++] : y[j++];
        while (i < x.length) out[k++] = x[i++];
        while (j < y.length) out[k++] = y[j++];
        return out;
    }
}
