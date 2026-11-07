package com.playbook.searchsort;

import java.util.concurrent.ThreadLocalRandom;

/** The O(n log n) divide-and-conquer sorts: merge sort (recursive + bottom-up) and quicksort (Lomuto + Hoare). */
public final class MergeQuickSort {
    private MergeQuickSort() {}

    /** Merge sort, top-down recursive. O(n log n) always, O(n) extra, STABLE. */
    public static void mergeSort(int[] a) {
        if (a.length < 2) return;
        mergeSort(a, new int[a.length], 0, a.length - 1);
    }

    private static void mergeSort(int[] a, int[] tmp, int lo, int hi) {
        if (lo >= hi) return;                                // base case: 0 or 1 element is sorted
        int mid = (lo + hi) >>> 1;
        mergeSort(a, tmp, lo, mid);
        mergeSort(a, tmp, mid + 1, hi);
        if (a[mid] <= a[mid + 1]) return;                    // halves already in order: skip the merge
        merge(a, tmp, lo, mid, hi);
    }

    static void merge(int[] a, int[] tmp, int lo, int mid, int hi) {
        System.arraycopy(a, lo, tmp, lo, hi - lo + 1);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = tmp[j++];
            else if (j > hi) a[k] = tmp[i++];
            else if (tmp[j] < tmp[i]) a[k] = tmp[j++];       // strict < : take from the LEFT on ties → stable
            else a[k] = tmp[i++];
        }
    }

    /** Merge sort, bottom-up iterative: merge runs of width 1, 2, 4, … No recursion. */
    public static void mergeSortBottomUp(int[] a) {
        int n = a.length;
        int[] tmp = new int[n];
        for (int width = 1; width < n; width *= 2) {
            for (int lo = 0; lo < n - width; lo += 2 * width) {
                merge(a, tmp, lo, lo + width - 1, Math.min(lo + 2 * width - 1, n - 1));
            }
        }
    }

    /** Quicksort with Lomuto partition and a RANDOM pivot. O(n log n) expected, O(n^2) worst, in place, NOT stable. */
    public static void quickSortLomuto(int[] a) {
        quickLomuto(a, 0, a.length - 1);
    }

    private static void quickLomuto(int[] a, int lo, int hi) {
        while (lo < hi) {
            int p = lomuto(a, lo, hi);
            if (p - lo < hi - p) {                           // recurse on the SMALLER side, loop on the larger:
                quickLomuto(a, lo, p - 1);                   // keeps the stack O(log n) even in bad cases
                lo = p + 1;
            } else {
                quickLomuto(a, p + 1, hi);
                hi = p - 1;
            }
        }
    }

    /** Lomuto: pivot at hi; everything < pivot is moved left of index i. Returns the pivot's final index. */
    static int lomuto(int[] a, int lo, int hi) {
        swap(a, lo + ThreadLocalRandom.current().nextInt(hi - lo + 1), hi);   // random pivot defeats sorted input
        int pivot = a[hi], i = lo;
        for (int j = lo; j < hi; j++) if (a[j] < pivot) swap(a, i++, j);
        swap(a, i, hi);
        return i;
    }

    /** Quicksort with Hoare partition: two pointers moving inward, ~3x fewer swaps than Lomuto, handles duplicates well. */
    public static void quickSortHoare(int[] a) {
        quickHoare(a, 0, a.length - 1);
    }

    private static void quickHoare(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int pivot = a[lo + ThreadLocalRandom.current().nextInt(hi - lo + 1)];
        int i = lo - 1, j = hi + 1;
        while (true) {
            do i++; while (a[i] < pivot);
            do j--; while (a[j] > pivot);
            if (i >= j) break;
            swap(a, i, j);
        }
        quickHoare(a, lo, j);                                // Hoare returns a split point, not the pivot's index
        quickHoare(a, j + 1, hi);
    }

    static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
