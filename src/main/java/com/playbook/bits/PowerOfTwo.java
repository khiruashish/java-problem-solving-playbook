package com.playbook.bits;

/** Is n a power of two? (and of four). Negative numbers and 0 are not. */
public final class PowerOfTwo {
    private PowerOfTwo() {}

    /** Divide by 2 while even. O(log n). */
    public static boolean loop(int n) {
        if (n <= 0) return false;
        while (n % 2 == 0) n /= 2;
        return n == 1;
    }

    /** A power of two has exactly ONE set bit; n & (n-1) clears it → 0. O(1). */
    public static boolean bitTrick(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    /** Library: exactly one set bit. */
    public static boolean bitCount(int n) {
        return n > 0 && Integer.bitCount(n) == 1;
    }

    /** Power of four: a power of two whose single bit is at an EVEN position (mask 0101…0101 = 0x55555555). */
    public static boolean powerOfFour(int n) {
        return bitTrick(n) && (n & 0x55555555) != 0;
    }
}
