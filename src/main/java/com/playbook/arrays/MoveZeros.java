package com.playbook.arrays;

import java.util.Arrays;
import java.util.stream.IntStream;

/** Move all zeros to the end, keeping the relative order of non-zero elements. In place. */
public final class MoveZeros {
    private MoveZeros() {}

    /** Extra array: copy non-zeros, fill rest with zeros. O(n) time, O(n) space. */
    public static int[] extraSpace(int[] a) {
        int[] out = new int[a.length];
        int k = 0;
        for (int x : a) if (x != 0) out[k++] = x;           // remaining slots are already 0
        return out;
    }

    /** Optimal: write pointer + swap. O(n) time, O(1) space, minimal writes. */
    public static void optimal(int[] a) {
        int write = 0;                                       // next position for a non-zero
        for (int read = 0; read < a.length; read++) {
            if (a[read] != 0) {
                int t = a[write];
                a[write++] = a[read];
                a[read] = t;
            }
        }
    }

    /** Java 8: concatenate non-zeros and zeros. */
    public static int[] streams(int[] a) {
        return IntStream.concat(Arrays.stream(a).filter(x -> x != 0), Arrays.stream(a).filter(x -> x == 0)).toArray();
    }
}
