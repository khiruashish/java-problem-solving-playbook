package com.playbook.arrays;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

class ArraysTest {

    @Test void maxMin() {
        int[][] cases = {{3, 1, 4, 1, 5, 9, 2, 6}, {7}, {-5, -1, -9}, {2, 2}, {Integer.MIN_VALUE, Integer.MAX_VALUE, 0}};
        for (int[] a : cases) {
            int[] expected = {Arrays.stream(a).min().getAsInt(), Arrays.stream(a).max().getAsInt()};
            assertArrayEquals(expected, MaxMin.sorting(a));
            assertArrayEquals(expected, MaxMin.linear(a));
            assertArrayEquals(expected, MaxMin.pairwise(a));
            assertArrayEquals(expected, MaxMin.recursive(a));
            assertArrayEquals(expected, MaxMin.streams(a));
        }
        assertThrows(IllegalArgumentException.class, () -> MaxMin.linear(new int[0]));
    }

    @Test void reverseArray() {
        int[] a = {1, 2, 3, 4, 5}, b = a.clone();
        ReverseArray.inPlace(a);
        ReverseArray.recursive(b);
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, a);
        assertArrayEquals(a, b);
        assertArrayEquals(new int[]{2, 1}, ReverseArray.streams(new int[]{1, 2}));
        int[] empty = {};
        ReverseArray.inPlace(empty);
        assertEquals(0, empty.length);
    }

    @Test void secondLargest() {
        assertEquals(OptionalInt.of(34), SecondLargest.optimal(new int[]{12, 35, 1, 10, 34, 1}));
        assertEquals(OptionalInt.of(5), SecondLargest.optimal(new int[]{10, 5, 10}));
        assertEquals(OptionalInt.of(5), SecondLargest.sorting(new int[]{10, 5, 10}));
        assertEquals(OptionalInt.of(5), SecondLargest.streams(new int[]{10, 5, 10}));
        assertEquals(OptionalInt.empty(), SecondLargest.optimal(new int[]{10, 10, 10}));
        assertEquals(OptionalInt.empty(), SecondLargest.sorting(new int[]{10, 10}));
        assertEquals(OptionalInt.empty(), SecondLargest.streams(new int[]{4}));
        assertEquals(OptionalInt.of(Integer.MIN_VALUE),
                SecondLargest.optimal(new int[]{Integer.MIN_VALUE, 0}));
    }

    @Test void moveZeros() {
        int[] in = {0, 1, 0, 3, 12};
        int[] expected = {1, 3, 12, 0, 0};
        assertArrayEquals(expected, MoveZeros.extraSpace(in));
        assertArrayEquals(expected, MoveZeros.streams(in));
        MoveZeros.optimal(in);
        assertArrayEquals(expected, in);
    }

    @Test void removeDuplicates() {
        int[] a = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int[] b = a.clone(), c = a.clone();
        assertEquals(5, RemoveDuplicatesSorted.optimal(a));
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, Arrays.copyOf(a, 5));
        assertEquals(5, RemoveDuplicatesSorted.withSet(b));
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, RemoveDuplicatesSorted.streams(c));
        int[] d = {1, 1, 1, 2, 2, 3};
        assertEquals(5, RemoveDuplicatesSorted.atMostTwice(d));
        assertArrayEquals(new int[]{1, 1, 2, 2, 3}, Arrays.copyOf(d, 5));
        assertEquals(0, RemoveDuplicatesSorted.optimal(new int[0]));
    }

    @Test void missingNumber() {
        int[][] cases = {{3, 0, 1}, {0, 1}, {9, 6, 4, 2, 3, 5, 7, 0, 1}, {0}, {1}};
        int[] expected = {2, 2, 8, 1, 0};
        for (int i = 0; i < cases.length; i++) {
            assertEquals(expected[i], MissingNumber.sorting(cases[i]));
            assertEquals(expected[i], MissingNumber.sumFormula(cases[i]));
            assertEquals(expected[i], MissingNumber.xor(cases[i]));
            assertEquals(expected[i], MissingNumber.streams(cases[i]));
        }
    }

    @Test void twoSum() {
        int[] a = {2, 7, 11, 15};
        assertArrayEquals(new int[]{0, 1}, TwoSum.bruteForce(a, 9));
        assertArrayEquals(new int[]{0, 1}, TwoSum.optimal(a, 9));
        assertArrayEquals(new int[]{0, 1}, TwoSum.sortedTwoPointers(a, 9));
        assertArrayEquals(new int[]{1, 2}, TwoSum.optimal(new int[]{3, 2, 4}, 6));
        assertArrayEquals(new int[]{1, 2}, TwoSum.sortThenTwoPointers(new int[]{3, 2, 4}, 6));
        assertArrayEquals(new int[]{0, 1}, TwoSum.optimal(new int[]{3, 3}, 6));
        assertEquals(0, TwoSum.optimal(new int[]{1, 2}, 10).length);
    }

    @Test void rotate() {
        int[] expected = {5, 6, 7, 1, 2, 3, 4};
        for (int k : new int[]{3, 10}) {
            int[] a = {1, 2, 3, 4, 5, 6, 7}, b = a.clone(), c = a.clone();
            RotateArray.bruteForce(a, k);
            RotateArray.extraArray(b, k);
            RotateArray.reversal(c, k);
            assertArrayEquals(expected, a);
            assertArrayEquals(expected, b);
            assertArrayEquals(expected, c);
        }
        int[] d = {1, 2, 3, 4, 5};
        RotateArray.rotateLeft(d, 2);
        assertArrayEquals(new int[]{3, 4, 5, 1, 2}, d);
    }

    @Test void kadane() {
        int[][] cases = {{-2, 1, -3, 4, -1, 2, 1, -5, 4}, {1}, {5, 4, -1, 7, 8}, {-3, -1, -2}};
        int[] expected = {6, 1, 23, -1};
        for (int i = 0; i < cases.length; i++) {
            assertEquals(expected[i], MaxSubarraySum.bruteForce(cases[i]));
            assertEquals(expected[i], MaxSubarraySum.kadane(cases[i]));
            assertEquals(expected[i], MaxSubarraySum.divideAndConquer(cases[i]));
        }
        assertArrayEquals(new int[]{6, 3, 6}, MaxSubarraySum.kadaneWithIndices(cases[0]));
    }

    @Test void stocks() {
        assertEquals(5, BuySellStock.optimal(new int[]{7, 1, 5, 3, 6, 4}));
        assertEquals(5, BuySellStock.bruteForce(new int[]{7, 1, 5, 3, 6, 4}));
        assertEquals(0, BuySellStock.optimal(new int[]{7, 6, 4, 3, 1}));
        assertEquals(7, BuySellStock.unlimitedTransactions(new int[]{7, 1, 5, 3, 6, 4}));
        assertEquals(0, BuySellStock.optimal(new int[0]));
    }

    @Test void productExceptSelf() {
        int[][] cases = {{1, 2, 3, 4}, {-1, 1, 0, -3, 3}, {2, 3}};
        int[][] expected = {{24, 12, 8, 6}, {0, 0, 9, 0, 0}, {3, 2}};
        for (int i = 0; i < cases.length; i++) {
            assertArrayEquals(expected[i], ProductExceptSelf.bruteForce(cases[i]));
            assertArrayEquals(expected[i], ProductExceptSelf.prefixSuffix(cases[i]));
            assertArrayEquals(expected[i], ProductExceptSelf.optimal(cases[i]));
        }
    }

    @Test void mergeIntervals() {
        assertArrayEquals(new int[][]{{1, 6}, {8, 10}, {15, 18}},
                MergeIntervals.merge(new int[][]{{8, 10}, {1, 3}, {15, 18}, {2, 6}}));
        assertArrayEquals(new int[][]{{1, 5}}, MergeIntervals.merge(new int[][]{{1, 4}, {4, 5}}));
        assertArrayEquals(new int[][]{{1, 10}}, MergeIntervals.merge(new int[][]{{1, 10}, {2, 3}}));
        assertEquals(0, MergeIntervals.merge(new int[0][]).length);
        assertArrayEquals(new int[][]{{1, 2}, {3, 10}, {12, 16}},
                MergeIntervals.insert(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8}));
    }

    @Test void nextPermutation() {
        int[][] in = {{1, 2, 3}, {3, 2, 1}, {1, 1, 5}, {1, 3, 2}, {2, 3, 1}, {1}};
        int[][] out = {{1, 3, 2}, {1, 2, 3}, {1, 5, 1}, {2, 1, 3}, {3, 1, 2}, {1}};
        for (int i = 0; i < in.length; i++) {
            NextPermutation.next(in[i]);
            assertArrayEquals(out[i], in[i]);
        }
    }

    @Test void trappingRainWater() {
        int[][] cases = {{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, {4, 2, 0, 3, 2, 5}, {}, {3}, {1, 2, 3}, {3, 0, 3}};
        int[] expected = {6, 9, 0, 0, 0, 3};
        for (int i = 0; i < cases.length; i++) {
            assertEquals(expected[i], TrappingRainWater.bruteForce(cases[i]));
            assertEquals(expected[i], TrappingRainWater.prefixMax(cases[i]));
            assertEquals(expected[i], TrappingRainWater.twoPointers(cases[i]));
            assertEquals(expected[i], TrappingRainWater.monotonicStack(cases[i]));
        }
    }
}
