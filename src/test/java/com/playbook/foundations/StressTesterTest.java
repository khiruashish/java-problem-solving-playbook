package com.playbook.foundations;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StressTesterTest {

    static int bruteMaxSubarray(int[] a) {
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            int sum = 0;
            for (int j = i; j < a.length; j++) best = Math.max(best, sum += a[j]);
        }
        return best;
    }

    static int kadane(int[] a) {
        int cur = a[0], best = a[0];
        for (int i = 1; i < a.length; i++) best = Math.max(best, cur = Math.max(a[i], cur + a[i]));
        return best;
    }

    /** Planted bug: starts at 0, so it is wrong when every value is negative. */
    static int buggyKadane(int[] a) {
        int cur = 0, best = 0;
        for (int x : a) best = Math.max(best, cur = Math.max(0, cur + x));
        return best;
    }

    @Test void agreesWhenBothAreCorrect() {
        assertDoesNotThrow(() -> StressTester.check(500, 42,
                rnd -> StressTester.randomArray(rnd, 1, 8, -5, 5),
                StressTesterTest::bruteMaxSubarray, StressTesterTest::kadane));
    }

    @Test void catchesThePlantedBugWithASmallInput() {
        AssertionError e = assertThrows(AssertionError.class, () -> StressTester.check(500, 42,
                rnd -> StressTester.randomArray(rnd, 1, 8, -5, 5),
                StressTesterTest::bruteMaxSubarray, StressTesterTest::buggyKadane));
        assertTrue(e.getMessage().contains("input"), e.getMessage());
    }

    @Test void inPlaceAlgorithmsGetTheirOwnCopy() {
        // "fast" sorts its input in place; brute must still see the original order.
        assertDoesNotThrow(() -> StressTester.check(200, 7,
                rnd -> StressTester.randomArray(rnd, 0, 6, 0, 9),
                a -> a.length == 0 ? -1 : a[0],
                a -> { int first = a.length == 0 ? -1 : a[0]; Arrays.sort(a); return first; }));
    }

    @Test void randomArrayRespectsBounds() {
        Random rnd = new Random(1);
        for (int i = 0; i < 300; i++) {
            int[] a = StressTester.randomArray(rnd, 2, 5, -3, 3);
            assertTrue(a.length >= 2 && a.length <= 5);
            for (int x : a) assertTrue(x >= -3 && x <= 3);
        }
    }
}
