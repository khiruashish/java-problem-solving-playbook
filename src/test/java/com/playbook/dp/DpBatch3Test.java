package com.playbook.dp;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DpBatch3Test {

    @Test void stockWithCooldown() {
        int[] p = {1, 2, 3, 0, 2};
        assertEquals(3, StockWithCooldown.stateMachine(p));      // buy 1, sell 2, cooldown, buy 0, sell 2
        assertEquals(3, StockWithCooldown.memo(p));
        assertEquals(3, StockWithCooldown.recursive(p));
        assertEquals(0, StockWithCooldown.stateMachine(new int[]{1}));
        assertEquals(0, StockWithCooldown.stateMachine(new int[0]));
        assertEquals(0, StockWithCooldown.stateMachine(new int[]{5, 4, 3}));
        Random rnd = new Random(101);
        for (int t = 0; t < 1500; t++) {
            int[] x = new int[rnd.nextInt(12)];
            for (int i = 0; i < x.length; i++) x[i] = rnd.nextInt(10);
            int want = StockWithCooldown.recursive(x);
            assertEquals(want, StockWithCooldown.memo(x));
            assertEquals(want, StockWithCooldown.stateMachine(x), java.util.Arrays.toString(x));
        }
    }

    @Test void longestPalindromicSubsequence() {
        assertEquals(4, LongestPalindromicSubsequence.intervalDp("bbbab"));
        assertEquals(2, LongestPalindromicSubsequence.intervalDp("cbbd"));
        assertEquals(0, LongestPalindromicSubsequence.intervalDp(""));
        assertEquals(7, LongestPalindromicSubsequence.twoRows("racecar"));
        assertEquals(2, LongestPalindromicSubsequence.minInsertionsToPalindrome("mbadm"));   // "mbdadbm"
        Random rnd = new Random(102);
        for (int t = 0; t < 1500; t++) {
            int n = rnd.nextInt(13);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) sb.append((char) ('a' + rnd.nextInt(3)));
            String s = sb.toString();
            int want = LongestPalindromicSubsequence.recursive(s);
            assertEquals(want, LongestPalindromicSubsequence.intervalDp(s), s);
            assertEquals(want, LongestPalindromicSubsequence.twoRows(s), s);
            assertEquals(want, LongestPalindromicSubsequence.viaLcs(s), s);
        }
    }

    @Test void eggDrop() {
        assertEquals(2, EggDrop.movesDp(1, 2));
        assertEquals(3, EggDrop.movesDp(2, 6));
        assertEquals(4, EggDrop.movesDp(3, 14));
        assertEquals(14, EggDrop.movesDp(2, 100));                 // the famous "2 eggs, 100 floors" answer
        assertEquals(14, EggDrop.classic(2, 100));
        assertEquals(14, EggDrop.binarySearch(2, 100));
        assertEquals(0, EggDrop.movesDp(5, 0));
        for (int k = 1; k <= 4; k++)
            for (int n = 0; n <= 60; n++) {
                int want = EggDrop.classic(k, n);
                assertEquals(want, EggDrop.binarySearch(k, n), k + " eggs, " + n + " floors");
                assertEquals(want, EggDrop.movesDp(k, n), k + " eggs, " + n + " floors");
            }
        assertEquals(30, EggDrop.movesDp(64, 1_000_000_000));          // ≥ log2(n+1) eggs → binary search depth
        assertEquals(EggDrop.binarySearch(3, 2000), EggDrop.movesDp(3, 2000));
    }
}
