package com.playbook.searchsort;

/** The O(n^2) sorts every interview expects you to write and compare. All sort in place, ascending. */
public final class ElementarySorts {
    private ElementarySorts() {}

    /** Bubble sort: repeatedly swap adjacent out-of-order pairs; the largest "bubbles" to the end each pass. Stable. */
    public static void bubble(int[] a) {
        for (int end = a.length - 1; end > 0; end--) {
            boolean swapped = false;
            for (int i = 0; i < end; i++) {
                if (a[i] > a[i + 1]) {                       // strict > keeps equal elements in order → stable
                    swap(a, i, i + 1);
                    swapped = true;
                }
            }
            if (!swapped) return;                            // already sorted: best case O(n)
        }
    }

    /** Selection sort: pick the minimum of the unsorted part, put it at the front. Exactly n-1 swaps. NOT stable. */
    public static void selection(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) if (a[j] < a[min]) min = j;
            swap(a, i, min);
        }
    }

    /** Insertion sort: grow a sorted prefix, shifting bigger elements right. Stable, adaptive, great for small/nearly sorted input. */
    public static void insertion(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i], j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];                             // shift, don't swap: fewer writes
                j--;
            }
            a[j + 1] = key;
        }
    }

    /** Recursive insertion sort: sort the first n-1, then insert the last. */
    public static void insertionRecursive(int[] a) {
        insertionRecursive(a, a.length);
    }

    private static void insertionRecursive(int[] a, int n) {
        if (n <= 1) return;
        insertionRecursive(a, n - 1);
        int key = a[n - 1], j = n - 2;
        while (j >= 0 && a[j] > key) {
            a[j + 1] = a[j];
            j--;
        }
        a[j + 1] = key;
    }

    /** Counting sort for values in [0, maxValue]: O(n + k), not comparison-based. Stable when done via prefix sums. */
    public static int[] counting(int[] a, int maxValue) {
        int[] count = new int[maxValue + 1];
        for (int x : a) count[x]++;
        for (int v = 1; v <= maxValue; v++) count[v] += count[v - 1];   // count[v] = number of elements <= v
        int[] out = new int[a.length];
        for (int i = a.length - 1; i >= 0; i--) out[--count[a[i]]] = a[i];   // right-to-left keeps stability
        return out;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
