package com.playbook.heap;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Min-heap from scratch on an int array. Node i has children 2i+1, 2i+2 and parent (i-1)/2.
 * Heap property: every parent ≤ its children → the minimum is at index 0. Also: O(n) heapify and in-place heapsort.
 */
public final class BinaryHeap {
    private int[] heap = new int[16];
    private int size;

    /** Append at the end, then sift UP while smaller than the parent. O(log n). */
    public void push(int x) {
        if (size == heap.length) heap = Arrays.copyOf(heap, size * 2);
        heap[size] = x;
        siftUp(heap, size++);
    }

    /** Take the root, move the LAST element to the root, sift it DOWN. O(log n). */
    public int pop() {
        if (size == 0) throw new NoSuchElementException("heap is empty");
        int min = heap[0];
        heap[0] = heap[--size];
        siftDown(heap, 0, size);
        return min;
    }

    public int peek() {
        if (size == 0) throw new NoSuchElementException("heap is empty");
        return heap[0];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Build a heap from an array in O(n): sift down every non-leaf, bottom-up (NOT n pushes, which is O(n log n)). */
    public static BinaryHeap heapify(int[] values) {
        BinaryHeap h = new BinaryHeap();
        h.heap = Arrays.copyOf(values, Math.max(16, values.length));
        h.size = values.length;
        for (int i = h.size / 2 - 1; i >= 0; i--) siftDown(h.heap, i, h.size);
        return h;
    }

    private static void siftUp(int[] h, int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (h[parent] <= h[i]) return;                   // heap property restored
            swap(h, parent, i);
            i = parent;
        }
    }

    /** Min-heap sift-down within h[0..n). */
    private static void siftDown(int[] h, int i, int n) {
        while (true) {
            int smallest = i, l = 2 * i + 1, r = l + 1;
            if (l < n && h[l] < h[smallest]) smallest = l;
            if (r < n && h[r] < h[smallest]) smallest = r;
            if (smallest == i) return;
            swap(h, i, smallest);
            i = smallest;
        }
    }

    /**
     * Heapsort ascending, in place, O(n log n), O(1) extra: build a MAX-heap, then repeatedly swap the max to the end
     * and shrink the heap. Not stable.
     */
    public static void heapSort(int[] a) {
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--) maxSiftDown(a, i, n);
        for (int end = n - 1; end > 0; end--) {
            swap(a, 0, end);                                 // largest goes to its final position
            maxSiftDown(a, 0, end);
        }
    }

    private static void maxSiftDown(int[] h, int i, int n) {
        while (true) {
            int largest = i, l = 2 * i + 1, r = l + 1;
            if (l < n && h[l] > h[largest]) largest = l;
            if (r < n && h[r] > h[largest]) largest = r;
            if (largest == i) return;
            swap(h, i, largest);
            i = largest;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
