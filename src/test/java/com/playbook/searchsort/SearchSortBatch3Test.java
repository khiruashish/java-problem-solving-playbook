package com.playbook.searchsort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SearchSortBatch3Test {

    @Test void search2DMatrix() {
        int[][] full = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        assertTrue(Search2DMatrix.fullySorted(full, 3));
        assertFalse(Search2DMatrix.fullySorted(full, 13));
        int[][] grid = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};
        assertTrue(Search2DMatrix.staircase(grid, 5));
        assertFalse(Search2DMatrix.staircase(grid, 20));
        assertFalse(Search2DMatrix.staircase(new int[0][0], 1));
        assertFalse(Search2DMatrix.fullySorted(new int[][]{{}}, 1));
        Random rnd = new Random(61);
        for (int t = 0; t < 500; t++) {
            int rows = 1 + rnd.nextInt(5), cols = 1 + rnd.nextInt(5);
            // fully sorted: a sorted array cut into rows
            int[] flat = new int[rows * cols];
            for (int i = 0, v = rnd.nextInt(3); i < flat.length; i++, v += 1 + rnd.nextInt(3)) flat[i] = v;
            int[][] f = new int[rows][cols];
            for (int i = 0; i < flat.length; i++) f[i / cols][i % cols] = flat[i];
            // row & column sorted: g[r][c] = r + c + noise that keeps monotonicity
            int[][] g = new int[rows][cols];
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < cols; c++)
                    g[r][c] = Math.max(r > 0 ? g[r - 1][c] : 0, c > 0 ? g[r][c - 1] : 0) + rnd.nextInt(3);
            for (int target = -1; target <= flat[flat.length - 1] + 1; target++) {
                assertEquals(Search2DMatrix.scan(f, target), Search2DMatrix.fullySorted(f, target));
            }
            for (int target = -1; target <= g[rows - 1][cols - 1] + 1; target++) {
                boolean want = Search2DMatrix.scan(g, target);
                assertEquals(want, Search2DMatrix.staircase(g, target));
                assertEquals(want, Search2DMatrix.binarySearchEachRow(g, target));
            }
        }
    }

    @Test void countingAndRadixSort() {
        assertArrayEquals(new int[]{1, 2, 2, 3, 3, 4, 8}, CountingRadixSort.countingSort(new int[]{4, 2, 2, 8, 3, 3, 1}));
        assertArrayEquals(new int[]{-5, -1, 0, 3}, CountingRadixSort.radixSort(new int[]{3, -1, 0, -5}));
        assertArrayEquals(new int[]{Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE},
                CountingRadixSort.radixSort(new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE, -1}));
        assertArrayEquals(new int[0], CountingRadixSort.countingSort(new int[0]));
        assertEquals("aelpp", CountingRadixSort.sortLetters("apple"));
        assertThrows(IllegalArgumentException.class, () -> CountingRadixSort.countingSort(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE}));
        Random rnd = new Random(62);
        for (int t = 0; t < 500; t++) {
            int[] small = new int[rnd.nextInt(30)];
            for (int i = 0; i < small.length; i++) small[i] = rnd.nextInt(41) - 20;
            int[] want = small.clone();
            Arrays.sort(want);
            assertArrayEquals(want, CountingRadixSort.countingSort(small));
            assertArrayEquals(want, CountingRadixSort.radixSort(small));
            int[] wide = rnd.ints(rnd.nextInt(50)).toArray();         // full int range, negatives included
            int[] w2 = wide.clone();
            Arrays.sort(w2);
            assertArrayEquals(w2, CountingRadixSort.radixSort(wide));
        }
    }

    @Test void kokoEatingBananas() {
        assertEquals(4, KokoEatingBananas.binarySearch(new int[]{3, 6, 7, 11}, 8));
        assertEquals(30, KokoEatingBananas.binarySearch(new int[]{30, 11, 23, 4, 20}, 5));
        assertEquals(23, KokoEatingBananas.binarySearch(new int[]{30, 11, 23, 4, 20}, 6));
        assertEquals(3, KokoEatingBananas.binarySearch(new int[]{1_000_000_000}, 333_333_334) );
        assertEquals(1, KokoEatingBananas.binarySearch(new int[]{1_000_000_000, 1_000_000_000}, Integer.MAX_VALUE));
        assertThrows(IllegalArgumentException.class, () -> KokoEatingBananas.binarySearch(new int[]{1, 2}, 1));
        Random rnd = new Random(63);
        for (int t = 0; t < 600; t++) {
            int[] piles = new int[1 + rnd.nextInt(6)];
            for (int i = 0; i < piles.length; i++) piles[i] = 1 + rnd.nextInt(30);
            int h = piles.length + rnd.nextInt(20);
            int want = KokoEatingBananas.linear(piles, h);
            assertEquals(want, KokoEatingBananas.binarySearch(piles, h));
            assertEquals(want, KokoEatingBananas.binarySearchTightBounds(piles, h));
        }
    }
}
