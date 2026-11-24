package com.playbook.arrays;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.OptionalInt;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ArraysBatch3Test {

    @Test void majorityElement() {
        int[] a = {2, 2, 1, 1, 1, 2, 2};
        assertEquals(2, MajorityElement.boyerMoore(a));
        assertEquals(2, MajorityElement.sorting(a));
        assertEquals(OptionalInt.of(2), MajorityElement.verified(a));
        assertEquals(OptionalInt.of(3), MajorityElement.hashing(new int[]{3, 2, 3}));
        assertEquals(OptionalInt.empty(), MajorityElement.verified(new int[]{1, 2, 3}));
        assertEquals(OptionalInt.empty(), MajorityElement.verified(new int[]{1, 1, 2, 2}));  // exactly n/2 is not a majority
        assertEquals(OptionalInt.empty(), MajorityElement.verified(new int[0]));
        Random rnd = new Random(11);
        for (int t = 0; t < 800; t++) {
            int[] x = randomArray(rnd, 1 + rnd.nextInt(9), 0, 2);
            OptionalInt want = MajorityElement.bruteForce(x);
            assertEquals(want, MajorityElement.hashing(x));
            assertEquals(want, MajorityElement.verified(x));
            assertEquals(want, MajorityElement.streams(x));
            if (want.isPresent()) {
                assertEquals(want.getAsInt(), MajorityElement.boyerMoore(x));
                assertEquals(want.getAsInt(), MajorityElement.sorting(x));
            }
        }
    }

    @Test void threeSum() {
        List<List<Integer>> expected = List.of(List.of(-1, -1, 2), List.of(-1, 0, 1));
        int[] nums = {-1, 0, 1, 2, -1, -4};
        assertEquals(expected, ThreeSum.twoPointers(nums));
        assertEquals(expected, ThreeSum.hashing(nums));
        assertEquals(expected, ThreeSum.bruteForce(nums));
        assertEquals(List.of(List.of(0, 0, 0)), ThreeSum.twoPointers(new int[]{0, 0, 0, 0}));
        assertEquals(List.of(), ThreeSum.twoPointers(new int[]{0, 1, 1}));
        assertEquals(List.of(), ThreeSum.twoPointers(new int[]{1}));
        assertArrayEquals(new int[]{-1, 0, 1, 2, -1, -4}, nums);  // input untouched
        Random rnd = new Random(12);
        for (int t = 0; t < 600; t++) {
            int[] x = randomArray(rnd, rnd.nextInt(10), -4, 4);
            List<List<Integer>> want = ThreeSum.bruteForce(x);
            assertEquals(want, ThreeSum.twoPointers(x));
            assertEquals(want, ThreeSum.hashing(x));
        }
    }

    @Test void firstMissingPositive() {
        int[][] cases = {{1, 2, 0}, {3, 4, -1, 1}, {7, 8, 9, 11, 12}, {}, {1}, {2}, {1, 1}, {2, 2, 2}, {1, 2, 3}};
        int[] want = {3, 2, 1, 1, 2, 1, 2, 1, 4};
        for (int i = 0; i < cases.length; i++) {
            assertEquals(want[i], FirstMissingPositive.bruteForce(cases[i]));
            assertEquals(want[i], FirstMissingPositive.hashing(cases[i]));
            assertEquals(want[i], FirstMissingPositive.sorting(cases[i]));
            assertEquals(want[i], FirstMissingPositive.cyclicSort(cases[i].clone()));
            assertEquals(want[i], FirstMissingPositive.signMarking(cases[i].clone()));
        }
        Random rnd = new Random(13);
        for (int t = 0; t < 800; t++) {
            int[] x = randomArray(rnd, rnd.nextInt(10), -3, 10);
            int w = FirstMissingPositive.bruteForce(x);
            assertEquals(w, FirstMissingPositive.hashing(x));
            assertEquals(w, FirstMissingPositive.sorting(x));
            assertEquals(w, FirstMissingPositive.cyclicSort(x.clone()));
            assertEquals(w, FirstMissingPositive.signMarking(x.clone()));
        }
    }

    static int[] randomArray(Random rnd, int n, int lo, int hi) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = lo + rnd.nextInt(hi - lo + 1);
        return a;
    }
}
