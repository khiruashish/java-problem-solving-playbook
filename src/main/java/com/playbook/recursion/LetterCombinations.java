package com.playbook.recursion;

import java.util.ArrayList;
import java.util.List;

/** Phone keypad: all letter strings a digit string 2–9 could represent. "23" → [ad, ae, af, bd, …, cf]. */
public final class LetterCombinations {
    private LetterCombinations() {}

    private static final String[] KEYS = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    /** Recursive: pick a letter for the current digit, recurse on the next digit. O(4^n · n). */
    public static List<String> recursive(String digits) {
        List<String> out = new ArrayList<>();
        if (digits.isEmpty()) return out;
        go(digits, 0, new StringBuilder(), out);
        return out;
    }

    private static void go(String d, int i, StringBuilder sb, List<String> out) {
        if (i == d.length()) {
            out.add(sb.toString());
            return;
        }
        for (char c : KEYS[d.charAt(i) - '0'].toCharArray()) {
            sb.append(c);
            go(d, i + 1, sb, out);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /** Iterative (BFS-like): extend every partial string by every letter of the next digit. */
    public static List<String> iterative(String digits) {
        List<String> out = new ArrayList<>();
        if (digits.isEmpty()) return out;
        out.add("");
        for (char digit : digits.toCharArray()) {
            List<String> next = new ArrayList<>();
            for (String prefix : out) for (char c : KEYS[digit - '0'].toCharArray()) next.add(prefix + c);
            out = next;
        }
        return out;
    }
}
