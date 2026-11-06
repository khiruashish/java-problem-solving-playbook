package com.playbook.strings;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Reverse a string — four ways. All methods return {@code null} for {@code null} input. */
public final class ReverseString {
    private ReverseString() {}

    /** Brute force: build a new string by prepending each char. O(n^2) because strings are immutable. */
    public static String bruteForce(String s) {
        if (s == null) return null;
        String result = "";
        for (char c : s.toCharArray()) {
            result = c + result;               // creates a new String every iteration
        }
        return result;
    }

    /** Optimal: two pointers swapping on a char[]. O(n) time, O(n) space for the array. */
    public static String optimal(String s) {
        if (s == null) return null;
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            char tmp = chars[left];
            chars[left++] = chars[right];
            chars[right--] = tmp;
        }
        return new String(chars);
    }

    /** Library: StringBuilder.reverse() — what you use in production (handles surrogate pairs). */
    public static String library(String s) {
        return s == null ? null : new StringBuilder(s).reverse().toString();
    }

    /** Recursive: reverse(rest) + first char. Base case: length <= 1. O(n^2) time, O(n) stack. */
    public static String recursive(String s) {
        if (s == null || s.length() <= 1) return s;
        return recursive(s.substring(1)) + s.charAt(0);
    }

    /** Java 8 streams: walk indexes backwards and join. */
    public static String streams(String s) {
        if (s == null) return null;
        return IntStream.range(0, s.length())
                .map(i -> s.charAt(s.length() - 1 - i))
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }
}
