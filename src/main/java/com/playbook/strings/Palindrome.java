package com.playbook.strings;

import java.util.stream.IntStream;

/**
 * Check whether a string is a palindrome.
 * {@link #isPalindrome} is a strict check; {@link #isValidPalindrome} ignores case and non-alphanumerics
 * ("A man, a plan, a canal: Panama" → true). {@code null} is treated as not a palindrome.
 */
public final class Palindrome {
    private Palindrome() {}

    /** Brute force: reverse and compare. O(n) time, O(n) extra space. */
    public static boolean bruteForce(String s) {
        if (s == null) return false;
        return new StringBuilder(s).reverse().toString().equals(s);
    }

    /** Optimal: two pointers moving inwards, stop at first mismatch. O(n) time, O(1) space. */
    public static boolean isPalindrome(String s) {
        if (s == null) return false;
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }

    /** Recursive: first == last && middle is a palindrome. O(n) time, O(n) stack. */
    public static boolean recursive(String s) {
        if (s == null) return false;
        return recursive(s, 0, s.length() - 1);
    }

    private static boolean recursive(String s, int left, int right) {
        if (left >= right) return true;                       // base case: 0 or 1 char left
        if (s.charAt(left) != s.charAt(right)) return false;
        return recursive(s, left + 1, right - 1);
    }

    /** Java 8 streams: every i in the first half matches its mirror. */
    public static boolean streams(String s) {
        if (s == null) return false;
        return IntStream.range(0, s.length() / 2)
                .allMatch(i -> s.charAt(i) == s.charAt(s.length() - 1 - i));
    }

    /** Variation: ignore case and anything that is not a letter or digit. */
    public static boolean isValidPalindrome(String s) {
        if (s == null) return false;
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;   // skip junk
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) return false;
            left++;
            right--;
        }
        return true;
    }
}
