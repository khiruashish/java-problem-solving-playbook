package com.playbook.strings;

/**
 * Run-length encoding: "aaabbc" → "a3b2c1". {@link #compressIfShorter} returns the original
 * when compression does not help (CTCI 1.6). {@link #decode} reverses it, supporting multi-digit counts.
 */
public final class StringCompression {
    private StringCompression() {}

    /** Optimal: single pass counting runs. O(n) time, O(n) output. */
    public static String compress(String s) {
        if (s == null || s.isEmpty()) return s;
        StringBuilder sb = new StringBuilder();
        int run = 1;
        for (int i = 1; i <= s.length(); i++) {
            if (i < s.length() && s.charAt(i) == s.charAt(i - 1)) {
                run++;
            } else {                                         // run ended (or string ended)
                sb.append(s.charAt(i - 1)).append(run);
                run = 1;
            }
        }
        return sb.toString();
    }

    /** Recursive: encode the first run, recurse on the remainder. */
    public static String recursive(String s) {
        if (s == null || s.isEmpty()) return s;
        int run = 1;
        while (run < s.length() && s.charAt(run) == s.charAt(0)) run++;
        return "" + s.charAt(0) + run + recursive(s.substring(run));
    }

    public static String compressIfShorter(String s) {
        String c = compress(s);
        return c == null || c.length() >= s.length() ? s : c;
    }

    /** "a12b3" → 12 a's then 3 b's. */
    public static String decode(String s) {
        if (s == null || s.isEmpty()) return s;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i++);
            int count = 0;
            while (i < s.length() && Character.isDigit(s.charAt(i))) count = count * 10 + (s.charAt(i++) - '0');
            sb.append(String.valueOf(c).repeat(count));
        }
        return sb.toString();
    }
}
