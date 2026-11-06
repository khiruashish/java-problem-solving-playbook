package com.playbook.logical;

/** Reverse the digits of an int (sign kept). Returns 0 on overflow (LeetCode 7). Plus palindrome number. */
public final class ReverseNumber {
    private ReverseNumber() {}

    /** Optimal: pop last digit (x % 10), push onto result (r * 10 + d). Overflow-checked. O(log10 n). */
    public static int reverse(int x) {
        int r = 0;
        while (x != 0) {
            int d = x % 10;                                  // in Java, -123 % 10 == -3, so sign is preserved
            if (r > Integer.MAX_VALUE / 10 || r < Integer.MIN_VALUE / 10) return 0;   // r*10 would overflow
            r = r * 10 + d;
            x /= 10;
        }
        return r;
    }

    /** Recursive: carry the partially built reversed value. (No overflow check — teaching version, uses long.) */
    public static long recursive(long x) {
        return x < 0 ? -recursive(-x, 0) : recursive(x, 0);
    }

    private static long recursive(long x, long acc) {
        if (x == 0) return acc;                              // base case: no digits left
        return recursive(x / 10, acc * 10 + x % 10);
    }

    /** String version — easy to write, but allocates and must handle sign + overflow separately. */
    public static int viaString(int x) {
        try {
            String s = new StringBuilder(String.valueOf(Math.abs((long) x))).reverse().toString();
            return (int) Math.copySign(Integer.parseInt(s), x);
        } catch (NumberFormatException overflow) {
            return 0;
        }
    }

    /** Palindrome number: reverse only HALF the digits (no overflow possible). Negative → false. */
    public static boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;
        int half = 0;
        while (x > half) {
            half = half * 10 + x % 10;
            x /= 10;
        }
        return x == half || x == half / 10;                  // /10 drops the middle digit for odd length
    }
}
