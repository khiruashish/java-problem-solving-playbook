package com.playbook.bits;

/** Small XOR / bit tricks interviewers like to ask as warm-ups. */
public final class XorTricks {
    private XorTricks() {}

    /** Swap two array cells without a temp variable. Guard i == j: x ^= x would zero it! */
    public static void xorSwap(int[] a, int i, int j) {
        if (i == j) return;
        a[i] ^= a[j];                                        // a = a^b
        a[j] ^= a[i];                                        // b = b^(a^b) = a
        a[i] ^= a[j];                                        // a = (a^b)^a = b
    }

    /** Odd check via the lowest bit — works for negatives too (n % 2 == 1 does NOT: -3 % 2 == -1). */
    public static boolean isOdd(int n) {
        return (n & 1) == 1;
    }

    /** Do x and y have opposite signs? The sign bit of x ^ y is 1 exactly when they differ. */
    public static boolean oppositeSigns(int x, int y) {
        return (x ^ y) < 0;
    }

    /** XOR of 1..n in O(1): the prefix XOR repeats with period 4 → n, 1, n+1, 0. */
    public static int xorUpTo(int n) {
        return switch (n & 3) {
            case 0 -> n;
            case 1 -> 1;
            case 2 -> n + 1;
            default -> 0;
        };
    }

    /** Reverse the 32 bits of n (LeetCode 190): take the lowest bit of n, push it into the result. */
    public static int reverseBits(int n) {
        int r = 0;
        for (int i = 0; i < 32; i++) {
            r = (r << 1) | (n & 1);
            n >>>= 1;
        }
        return r;
    }

    /** Add without + (LeetCode 371): XOR adds without carry, AND<<1 is the carry; repeat until no carry. */
    public static int addWithoutPlus(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a ^= b;
            b = carry;
        }
        return a;
    }
}
