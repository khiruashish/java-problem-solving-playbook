package com.playbook.searchsort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SearchSortTest {
    private final Random rnd = new Random(909);

    private int[] random(int maxLen, int lo, int hi) {
        return rnd.ints(rnd.nextInt(maxLen + 1), lo, hi).toArray();
    }

    @Test void binarySearch() {
        int[] a = {-5, -1, 0, 3, 8, 13, 21};
        for (int i = 0; i < a.length; i++) {
            assertEquals(i, BinarySearch.iterative(a, a[i]));
            assertEquals(i, BinarySearch.recursive(a, a[i]));
            assertEquals(i, BinarySearch.linear(a, a[i]));
        }
        for (int miss : new int[]{-6, 2, 22}) {
            assertEquals(-1, BinarySearch.iterative(a, miss));
            assertEquals(-1, BinarySearch.recursive(a, miss));
        }
        assertEquals(-1, BinarySearch.iterative(new int[0], 1));
        for (int x : new int[]{0, 1, 2, 3, 4, 8, 15, 16, 17, Integer.MAX_VALUE})
            assertEquals((int) Math.floor(Math.sqrt(x)), BinarySearch.sqrt(x), "x=" + x);
    }

    @Test void elementarySorts() {
        List<Consumer<int[]>> sorts = List.of(ElementarySorts::bubble, ElementarySorts::selection,
                ElementarySorts::insertion, ElementarySorts::insertionRecursive);
        for (int t = 0; t < 300; t++) {
            int[] a = random(30, -20, 20), expected = a.clone();
            Arrays.sort(expected);
            for (Consumer<int[]> sort : sorts) {
                int[] c = a.clone();
                sort.accept(c);
                assertArrayEquals(expected, c);
            }
            int[] nonNeg = random(30, 0, 10), sortedNonNeg = nonNeg.clone();
            Arrays.sort(sortedNonNeg);
            assertArrayEquals(sortedNonNeg, ElementarySorts.counting(nonNeg, 9));
        }
    }

    @Test void firstLast() {
        int[] a = {5, 7, 7, 8, 8, 10};
        assertArrayEquals(new int[]{3, 4}, FirstLastOccurrence.binary(a, 8));
        assertArrayEquals(new int[]{-1, -1}, FirstLastOccurrence.binary(a, 6));
        assertArrayEquals(new int[]{-1, -1}, FirstLastOccurrence.binary(new int[0], 0));
        assertEquals(2, FirstLastOccurrence.count(a, 7));
        for (int t = 0; t < 300; t++) {
            int[] r = random(20, 0, 6);
            Arrays.sort(r);
            int target = rnd.nextInt(7);
            assertArrayEquals(FirstLastOccurrence.linear(r, target), FirstLastOccurrence.binary(r, target));
        }
    }

    @Test void rotated() {
        int[] a = {4, 5, 6, 7, 0, 1, 2};
        assertEquals(4, RotatedSortedArray.search(a, 0));
        assertEquals(-1, RotatedSortedArray.search(a, 3));
        assertEquals(4, RotatedSortedArray.minIndex(a));
        for (int n = 1; n <= 9; n++) {
            int[] sorted = IntStream.range(0, n).map(i -> i * 3).toArray();
            for (int rot = 0; rot < n; rot++) {
                int[] r = new int[n];
                for (int i = 0; i < n; i++) r[i] = sorted[(i + rot) % n];
                assertEquals((n - rot) % n, RotatedSortedArray.minIndex(r));
                for (int target = -1; target <= 3 * n; target++) {
                    int expected = BinarySearch.linear(r, target);
                    assertEquals(expected, RotatedSortedArray.search(r, target));
                    assertEquals(expected, RotatedSortedArray.searchViaPivot(r, target));
                }
            }
        }
    }

    @Test void mergeQuick() {
        List<Consumer<int[]>> sorts = List.of(MergeQuickSort::mergeSort, MergeQuickSort::mergeSortBottomUp,
                MergeQuickSort::quickSortLomuto, MergeQuickSort::quickSortHoare);
        for (int t = 0; t < 300; t++) {
            int[] a = random(60, -30, 30), expected = a.clone();
            Arrays.sort(expected);
            for (Consumer<int[]> sort : sorts) {
                int[] c = a.clone();
                sort.accept(c);
                assertArrayEquals(expected, c);
            }
        }
        int[] big = IntStream.range(0, 50_000).toArray();       // sorted input: random pivot must avoid O(n^2) / stack overflow
        MergeQuickSort.quickSortLomuto(big);
        int[] dupes = new int[50_000];                           // all equal: Hoare handles it in n log n
        MergeQuickSort.quickSortHoare(dupes);
    }

    @Test void sortColors() {
        for (int t = 0; t < 300; t++) {
            int[] a = random(30, 0, 3), expected = a.clone(), b = a.clone();
            Arrays.sort(expected);
            SortColors.dutchFlag(a);
            SortColors.counting(b);
            assertArrayEquals(expected, a);
            assertArrayEquals(expected, b);
        }
    }

    @Test void peak() {
        int[] a = {1, 2, 1, 3, 5, 6, 4};
        assertTrue(FindPeakElement.isPeak(a, FindPeakElement.binary(a)));
        for (int t = 0; t < 300; t++) {
            int n = 1 + rnd.nextInt(20);
            int[] r = new int[n];
            r[0] = rnd.nextInt(10);
            for (int i = 1; i < n; i++) r[i] = r[i - 1] + (rnd.nextBoolean() ? 1 + rnd.nextInt(3) : -1 - rnd.nextInt(3));
            assertTrue(FindPeakElement.isPeak(r, FindPeakElement.linear(r)));
            assertTrue(FindPeakElement.isPeak(r, FindPeakElement.binary(r)));
            assertTrue(FindPeakElement.isPeak(r, FindPeakElement.recursive(r)));
        }
    }

    @Test void kthSmallest() {
        int[] a = {7, 10, 4, 3, 20, 15};
        assertEquals(7, KthSmallest.quickselect(a, 3));
        assertEquals(15, KthSmallest.kthLargest(a, 2));
        assertArrayEquals(new int[]{7, 10, 4, 3, 20, 15}, a, "input untouched");
        for (int t = 0; t < 300; t++) {
            int[] r = random(25, -10, 10);
            if (r.length == 0) continue;
            int k = 1 + rnd.nextInt(r.length);
            int expected = KthSmallest.sorting(r, k);
            assertEquals(expected, KthSmallest.maxHeap(r, k));
            assertEquals(expected, KthSmallest.quickselect(r, k));
        }
    }

    @Test void inversions() {
        assertEquals(6, CountInversions.mergeSort(new int[]{8, 4, 2, 1}));
        assertEquals(5, CountInversions.mergeSort(new int[]{1, 20, 6, 4, 5}));   // (20,6) (20,4) (20,5) (6,4) (6,5)
        assertEquals(0, CountInversions.mergeSort(new int[]{1, 1, 2}));
        for (int t = 0; t < 300; t++) {
            int[] r = random(40, 0, 10);
            assertEquals(CountInversions.bruteForce(r), CountInversions.mergeSort(r));
        }
        int n = 100_000;
        assertEquals((long) n * (n - 1) / 2, CountInversions.mergeSort(IntStream.range(0, n).map(i -> n - i).toArray()));
    }

    @Test void median() {
        assertEquals(2.0, MedianTwoSortedArrays.partition(new int[]{1, 3}, new int[]{2}));
        assertEquals(2.5, MedianTwoSortedArrays.partition(new int[]{1, 2}, new int[]{3, 4}));
        assertEquals(3.0, MedianTwoSortedArrays.partition(new int[0], new int[]{3}));
        for (int t = 0; t < 500; t++) {
            int[] a = random(10, -20, 20), b = random(10, -20, 20);
            if (a.length + b.length == 0) continue;
            Arrays.sort(a);
            Arrays.sort(b);
            int[] all = IntStream.concat(Arrays.stream(a), Arrays.stream(b)).sorted().toArray();
            int m = all.length;
            double expected = m % 2 == 1 ? all[m / 2] : (all[m / 2 - 1] + all[m / 2]) / 2.0;
            assertEquals(expected, MedianTwoSortedArrays.merge(a, b), 1e-9);
            assertEquals(expected, MedianTwoSortedArrays.partition(a, b), 1e-9);
        }
    }

    @Test void splitArray() {
        assertEquals(18, SplitArrayLargestSum.binarySearchOnAnswer(new int[]{7, 2, 5, 10, 8}, 2));
        assertEquals(9, SplitArrayLargestSum.binarySearchOnAnswer(new int[]{1, 2, 3, 4, 5}, 2));
        assertEquals(113, SplitArrayLargestSum.binarySearchOnAnswer(new int[]{12, 34, 67, 90}, 2));   // GfG allocate pages
        for (int t = 0; t < 200; t++) {
            int[] r = rnd.ints(1 + rnd.nextInt(9), 0, 20).toArray();
            int k = 1 + rnd.nextInt(r.length);
            assertEquals(SplitArrayLargestSum.bruteForce(r, k), SplitArrayLargestSum.binarySearchOnAnswer(r, k));
        }
    }
}
