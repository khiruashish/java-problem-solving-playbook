package com.playbook.greedy;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GreedyBatch3Test {

    @Test void lemonadeChange() {
        assertTrue(LemonadeChange.greedy(new int[]{5, 5, 5, 10, 20}));
        assertFalse(LemonadeChange.greedy(new int[]{5, 5, 10, 10, 20}));
        assertFalse(LemonadeChange.greedy(new int[]{10}));
        assertTrue(LemonadeChange.greedy(new int[0]));
        // the greedy choice matters: changing the 20 with 5+5+5 would leave no five for the last 10
        assertTrue(LemonadeChange.greedy(new int[]{5, 5, 5, 5, 10, 20, 10}));
        Random rnd = new Random(111);
        int[] bills = {5, 10, 20};
        for (int t = 0; t < 3000; t++) {
            int[] b = new int[rnd.nextInt(12)];
            for (int i = 0; i < b.length; i++) b[i] = bills[rnd.nextInt(10) < 5 ? 0 : 1 + rnd.nextInt(2)];
            assertEquals(LemonadeChange.exhaustive(b), LemonadeChange.greedy(b), java.util.Arrays.toString(b));
        }
    }

    @Test void nonOverlappingIntervals() {
        int[][] a = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        assertEquals(1, NonOverlappingIntervals.greedyByEnd(a));
        assertEquals(1, NonOverlappingIntervals.greedyByStart(a));
        assertEquals(2, NonOverlappingIntervals.greedyByEnd(new int[][]{{1, 2}, {1, 2}, {1, 2}}));
        assertEquals(0, NonOverlappingIntervals.greedyByEnd(new int[][]{{1, 2}, {2, 3}}));
        assertEquals(0, NonOverlappingIntervals.greedyByEnd(new int[0][]));
        Random rnd = new Random(112);
        for (int t = 0; t < 800; t++) {
            int[][] iv = new int[rnd.nextInt(10)][];
            for (int i = 0; i < iv.length; i++) {
                int s = rnd.nextInt(10);
                iv[i] = new int[]{s, s + 1 + rnd.nextInt(4)};
            }
            int want = NonOverlappingIntervals.bruteForce(iv);
            assertEquals(want, NonOverlappingIntervals.greedyByEnd(iv));
            assertEquals(want, NonOverlappingIntervals.greedyByStart(iv));
            assertEquals(want, NonOverlappingIntervals.dp(iv));
        }
    }

    @Test void candy() {
        assertEquals(5, Candy.twoPass(new int[]{1, 0, 2}));
        assertEquals(4, Candy.twoPass(new int[]{1, 2, 2}));
        assertEquals(5, Candy.onePass(new int[]{1, 0, 2}));
        assertEquals(4, Candy.onePass(new int[]{1, 2, 2}));
        assertEquals(11, Candy.twoPass(new int[]{1, 3, 4, 5, 2}));         // 1+2+3+4+1
        assertEquals(11, Candy.onePass(new int[]{1, 3, 4, 5, 2}));
        assertEquals(0, Candy.onePass(new int[0]));
        Random rnd = new Random(113);
        for (int t = 0; t < 3000; t++) {
            int[] r = new int[rnd.nextInt(12)];
            for (int i = 0; i < r.length; i++) r[i] = rnd.nextInt(4);
            int want = Candy.relaxation(r);
            assertEquals(want, Candy.twoPass(r), java.util.Arrays.toString(r));
            assertEquals(want, Candy.onePass(r), java.util.Arrays.toString(r));
        }
    }
}
