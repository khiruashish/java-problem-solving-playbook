package com.playbook.bits;

/** Reverse the 32 bits of an int (treated as unsigned): bit 0 ↔ bit 31, bit 1 ↔ bit 30, ... */
public final class ReverseBits {
    private ReverseBits() {}

    /** Bit by bit: shift the result left, append n's lowest bit, shift n right (unsigned). 32 iterations. */
    public static int loop(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1);
            n >>>= 1;                                     // >>> so the sign bit doesn't smear 1s
        }
        return result;
    }

    /**
     * Divide and conquer with masks: swap the two 16-bit halves, then the bytes inside each half, then nibbles,
     * pairs, and single bits. 5 steps, no loop — this is what Integer.reverse does.
     */
    public static int masks(int n) {
        n = (n >>> 16) | (n << 16);
        n = ((n & 0xFF00FF00) >>> 8) | ((n & 0x00FF00FF) << 8);
        n = ((n & 0xF0F0F0F0) >>> 4) | ((n & 0x0F0F0F0F) << 4);
        n = ((n & 0xCCCCCCCC) >>> 2) | ((n & 0x33333333) << 2);
        n = ((n & 0xAAAAAAAA) >>> 1) | ((n & 0x55555555) << 1);
        return n;
    }

    private static final int[] BYTE_REV = new int[256];

    static {
        for (int b = 0; b < 256; b++) {
            int r = 0;
            for (int i = 0; i < 8; i++) if ((b & (1 << i)) != 0) r |= 1 << (7 - i);
            BYTE_REV[b] = r;
        }
    }

    /** Lookup table: reverse each byte via a 256-entry table and place it in the mirrored byte slot. 4 lookups. */
    public static int byteTable(int n) {
        return (BYTE_REV[n & 0xFF] << 24)
                | (BYTE_REV[(n >>> 8) & 0xFF] << 16)
                | (BYTE_REV[(n >>> 16) & 0xFF] << 8)
                | BYTE_REV[(n >>> 24) & 0xFF];
    }

    /** Library: what you'd use in production. */
    public static int library(int n) {
        return Integer.reverse(n);
    }

    /** Helper for docs/tests: 32-char binary string with leading zeros. */
    public static String bin(int n) {
        return String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
    }
}
