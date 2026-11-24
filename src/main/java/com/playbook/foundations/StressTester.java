package com.playbook.foundations;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

/**
 * Differential ("stress") testing: run a trusted brute force and a fast implementation on many small random inputs
 * and fail on the first input where they disagree.
 */
public final class StressTester {
    private StressTester() {}

    /** A generator of random inputs. */
    @FunctionalInterface
    public interface Generator<I> {
        I next(Random rnd);
    }

    /**
     * Runs {@code cases} random inputs through both functions. Each function gets its own deep copy of int[] inputs,
     * so an in-place algorithm can't change what the other one sees.
     *
     * @throws AssertionError with the failing input, expected and actual output
     */
    public static <I, O> void check(int cases, long seed, Generator<I> gen, Function<I, O> brute, Function<I, O> fast) {
        Random rnd = new Random(seed);                        // fixed seed: every failure is reproducible
        for (int c = 0; c < cases; c++) {
            I input = gen.next(rnd);
            O expected = brute.apply(copy(input));
            O actual = fast.apply(copy(input));
            if (!deepEquals(expected, actual)) {
                throw new AssertionError("mismatch on case #" + c
                        + "\n  input    = " + show(input)
                        + "\n  expected = " + show(expected)
                        + "\n  actual   = " + show(actual));
            }
        }
    }

    /** Random int array with a length in [minLen, maxLen] and values in [lo, hi]. Small ranges force duplicates. */
    public static int[] randomArray(Random rnd, int minLen, int maxLen, int lo, int hi) {
        int n = minLen + rnd.nextInt(maxLen - minLen + 1);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = lo + rnd.nextInt(hi - lo + 1);
        return a;
    }

    @SuppressWarnings("unchecked")
    private static <I> I copy(I input) {
        return input instanceof int[] a ? (I) a.clone() : input;
    }

    private static boolean deepEquals(Object a, Object b) {
        return Objects.deepEquals(a, b);                     // handles int[] and nested arrays too
    }

    private static String show(Object o) {
        if (o instanceof int[] a) return Arrays.toString(a);
        if (o instanceof Object[] a) return Arrays.deepToString(a);
        return String.valueOf(o);
    }
}
