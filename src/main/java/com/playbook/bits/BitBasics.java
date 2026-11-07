package com.playbook.bits;

/** The four single-bit operations every bit problem is built from (bit positions are 0-based from the right). */
public final class BitBasics {
    private BitBasics() {}

    /** Is bit k set? Shift a 1 to position k and AND. */
    public static boolean isSet(int n, int k) {
        return (n & (1 << k)) != 0;
    }

    /** Turn bit k on: OR with the mask. */
    public static int set(int n, int k) {
        return n | (1 << k);
    }

    /** Turn bit k off: AND with the inverted mask. */
    public static int clear(int n, int k) {
        return n & ~(1 << k);
    }

    /** Flip bit k: XOR with the mask. */
    public static int toggle(int n, int k) {
        return n ^ (1 << k);
    }

    /** Lowest set bit as a value (12 = 1100 → 4). Two's complement: -n = ~n + 1 keeps only the lowest 1. */
    public static int lowestSetBit(int n) {
        return n & -n;
    }

    /** Clear the lowest set bit (12 → 8). The engine behind Kernighan's bit count and power-of-two test. */
    public static int clearLowestSetBit(int n) {
        return n & (n - 1);
    }

    /** Binary string padded to `width` bits (for teaching / debugging). */
    public static String toBinary(int n, int width) {
        String s = Integer.toBinaryString(n);
        if (s.length() >= width) return s.substring(s.length() - width);
        return "0".repeat(width - s.length()) + s;
    }
}
