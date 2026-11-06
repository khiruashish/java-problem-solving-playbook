package com.playbook.recursion;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecursionTest {

    @Test void factorial() {
        long[] expected = {1, 1, 2, 6, 24, 120, 3628800};
        int[] n = {0, 1, 2, 3, 4, 5, 10};
        for (int i = 0; i < n.length; i++) {
            assertEquals(expected[i], Factorial.recursive(n[i]));
            assertEquals(expected[i], Factorial.tailRecursive(n[i]));
            assertEquals(expected[i], Factorial.iterative(n[i]));
            assertEquals(expected[i], Factorial.streams(n[i]));
        }
        assertEquals(2432902008176640000L, Factorial.iterative(20));
        assertEquals(new BigInteger("51090942171709440000"), Factorial.big(21));
        assertThrows(IllegalArgumentException.class, () -> Factorial.recursive(-1));
    }

    @Test void fibonacci() {
        long[] expected = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55};
        for (int n = 0; n < expected.length; n++) {
            assertEquals(expected[n], Fibonacci.naive(n));
            assertEquals(expected[n], Fibonacci.memo(n));
            assertEquals(expected[n], Fibonacci.iterative(n));
            assertEquals(expected[n], Fibonacci.matrix(n));
        }
        assertEquals(12586269025L, Fibonacci.memo(50));
        assertEquals(12586269025L, Fibonacci.matrix(50));
        assertEquals(List.of(0L, 1L, 1L, 2L, 3L, 5L), Fibonacci.series(6));
    }

    @Test void power() {
        double[][] cases = {{2, 10, 1024}, {2.1, 3, 9.261}, {2, -2, 0.25}, {5, 0, 1}, {1, Integer.MIN_VALUE, 1}};
        for (double[] c : cases) {
            assertEquals(c[2], Power.fastRecursive(c[0], (int) c[1]), 1e-9);
            assertEquals(c[2], Power.fastIterative(c[0], (int) c[1]), 1e-9);
            if (Math.abs(c[1]) < 1000) assertEquals(c[2], Power.naive(c[0], (int) c[1]), 1e-9);
        }
        assertEquals(24, Power.modPow(2, 10, 1000));
    }

    @Test void sumOfDigits() {
        assertEquals(15, SumOfDigits.recursive(12345));
        assertEquals(15, SumOfDigits.iterative(12345));
        assertEquals(15, SumOfDigits.streams(12345));
        assertEquals(0, SumOfDigits.recursive(0));
        assertEquals(6, SumOfDigits.digitalRoot(12345));
        for (long n = 0; n < 2000; n++) assertEquals(SumOfDigits.digitalRoot(n), SumOfDigits.digitalRootFormula(n));
    }

    @Test void hanoi() {
        assertEquals(List.of("disk 1: A->B", "disk 2: A->C", "disk 1: B->C"), TowerOfHanoi.recursive(2));
        for (int n = 1; n <= 8; n++) {
            List<String> rec = TowerOfHanoi.recursive(n);
            assertEquals((1 << n) - 1, rec.size());
            assertEquals(rec, TowerOfHanoi.iterative(n), "n=" + n);
        }
    }

    @Test void subsets() {
        int[] a = {1, 2, 3};
        for (List<List<Integer>> r : List.of(Subsets.pickOrSkip(a), Subsets.backtracking(a), Subsets.iterative(a), Subsets.bitmask(a))) {
            assertEquals(8, r.size());
            assertEquals(Set.of(List.of(), List.of(1), List.of(2), List.of(3), List.of(1, 2), List.of(1, 3), List.of(2, 3), List.of(1, 2, 3)),
                    new HashSet<>(r));
        }
        assertEquals(List.of(List.of()), Subsets.backtracking(new int[0]));
    }

    @Test void nQueens() {
        assertEquals(List.of(List.of(".Q..", "...Q", "Q...", "..Q."), List.of("..Q.", "Q...", "...Q", ".Q..")), NQueens.solve(4));
        int[] counts = {1, 0, 0, 2, 10, 4, 40, 92};
        for (int n = 1; n <= 8; n++) {
            assertEquals(counts[n - 1], NQueens.solve(n).size());
            assertEquals(counts[n - 1], NQueens.count(n));
        }
    }
}
