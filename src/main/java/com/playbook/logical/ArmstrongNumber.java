package com.playbook.logical;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Armstrong (narcissistic) number: sum of each digit^(number of digits) equals the number. 153 = 1³+5³+3³. */
public final class ArmstrongNumber {
    private ArmstrongNumber() {}

    /** Iterative: count digits, then sum powers. O(d). */
    public static boolean isArmstrong(int n) {
        if (n < 0) return false;
        int digits = String.valueOf(n).length();
        long sum = 0;
        for (int x = n; x > 0; x /= 10) sum += pow(x % 10, digits);
        return sum == n;
    }

    /** Recursive sum of digit powers. */
    public static boolean recursive(int n) {
        return n >= 0 && sumPowers(n, String.valueOf(n).length()) == n;
    }

    private static long sumPowers(int n, int p) {
        return n == 0 ? 0 : pow(n % 10, p) + sumPowers(n / 10, p);
    }

    /** Java 8: digits as chars → power sum. */
    public static boolean streams(int n) {
        if (n < 0) return false;
        String s = String.valueOf(n);
        return s.chars().mapToLong(c -> pow(c - '0', s.length())).sum() == n;
    }

    /** All Armstrong numbers in [1, limit]. */
    public static List<Integer> upTo(int limit) {
        return IntStream.rangeClosed(1, limit).filter(ArmstrongNumber::isArmstrong).boxed().collect(Collectors.toCollection(ArrayList::new));
    }

    private static long pow(int base, int exp) {             // integer power — avoid Math.pow double rounding
        long r = 1;
        for (int i = 0; i < exp; i++) r *= base;
        return r;
    }
}
