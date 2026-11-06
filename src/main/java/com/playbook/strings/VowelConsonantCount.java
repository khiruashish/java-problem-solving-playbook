package com.playbook.strings;

/**
 * Count vowels and consonants (English letters only; digits, spaces and symbols are ignored).
 * Result is {@code int[]{vowels, consonants}}. {@code null}/empty → {0, 0}.
 */
public final class VowelConsonantCount {
    private VowelConsonantCount() {}

    /** Loop + {@code indexOf} lookup in a vowel string. O(n) time, O(1) space. */
    public static int[] optimal(String s) {
        int vowels = 0, consonants = 0;
        if (s == null) return new int[]{0, 0};
        for (char c : s.toLowerCase().toCharArray()) {
            if (c < 'a' || c > 'z') continue;                // not an English letter
            if ("aeiou".indexOf(c) >= 0) vowels++;
            else consonants++;
        }
        return new int[]{vowels, consonants};
    }

    /** Recursive: count first char, recurse on the rest by index (no substring copies). */
    public static int[] recursive(String s) {
        int[] acc = new int[2];
        if (s != null) recursive(s.toLowerCase(), 0, acc);
        return acc;
    }

    private static void recursive(String s, int i, int[] acc) {
        if (i == s.length()) return;                         // base case
        char c = s.charAt(i);
        if (c >= 'a' && c <= 'z') acc["aeiou".indexOf(c) >= 0 ? 0 : 1]++;
        recursive(s, i + 1, acc);
    }

    /** Java 8 streams: filter letters, then count twice. */
    public static int[] streams(String s) {
        if (s == null) return new int[]{0, 0};
        String lower = s.toLowerCase();
        long vowels = lower.chars().filter(c -> "aeiou".indexOf(c) >= 0).count();
        long letters = lower.chars().filter(c -> c >= 'a' && c <= 'z').count();
        return new int[]{(int) vowels, (int) (letters - vowels)};
    }
}
