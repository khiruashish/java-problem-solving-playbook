package com.playbook.strings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reverse the order of words; collapse multiple spaces and trim ("  the sky  is blue " → "blue is sky the").
 * Also: reverse each word in place keeping word order ("I love Java" → "I evol avaJ").
 */
public final class ReverseWords {
    private ReverseWords() {}

    /** Library: split on whitespace, reverse list, join. O(n) time, O(n) space. */
    public static String library(String s) {
        if (s == null) return null;
        String trimmed = s.trim();
        if (trimmed.isEmpty()) return "";
        List<String> words = Arrays.asList(trimmed.split("\\s+"));
        Collections.reverse(words);
        return String.join(" ", words);
    }

    /** Optimal (manual parse): scan from the end, copy each word once. O(n) time, O(n) output. */
    public static String optimal(String s) {
        if (s == null) return null;
        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1;
        while (i >= 0) {
            while (i >= 0 && s.charAt(i) == ' ') i--;        // skip spaces
            if (i < 0) break;
            int end = i;
            while (i >= 0 && s.charAt(i) != ' ') i--;        // find word start
            if (!sb.isEmpty()) sb.append(' ');
            sb.append(s, i + 1, end + 1);
        }
        return sb.toString();
    }

    /** Recursive: reverse(rest) + " " + firstWord. */
    public static String recursive(String s) {
        if (s == null) return null;
        String t = s.trim();
        int space = t.indexOf(' ');
        if (space < 0) return t;                             // base case: single word (or empty)
        return recursive(t.substring(space + 1)) + " " + t.substring(0, space);
    }

    /** Java 8 streams: words reversed via a reducing join. */
    public static String streams(String s) {
        if (s == null) return null;
        return Arrays.stream(s.trim().split("\\s+"))
                .filter(w -> !w.isEmpty())
                .reduce((acc, w) -> w + " " + acc)
                .orElse("");
    }

    /** Variation: reverse characters of each word, keep word order. */
    public static String reverseEachWord(String s) {
        if (s == null) return null;
        return Arrays.stream(s.split(" "))
                .map(w -> new StringBuilder(w).reverse().toString())
                .collect(Collectors.joining(" "));
    }
}
