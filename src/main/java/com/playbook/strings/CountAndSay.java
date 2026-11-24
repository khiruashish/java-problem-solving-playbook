package com.playbook.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Count-and-say: term 1 is "1"; each next term reads the previous one aloud as (count, digit) runs.
 * 1 → 11 → 21 → 1211 → 111221 → 312211.
 */
public final class CountAndSay {
    private CountAndSay() {}

    /** Iterative run-length scan. Builds n terms; each term is O(length of the previous term). */
    public static String iterative(int n) {
        if (n < 1) throw new IllegalArgumentException("n must be >= 1");
        String term = "1";
        for (int k = 2; k <= n; k++) term = say(term);
        return term;
    }

    /** Recursive: term(n) = say(term(n-1)), base case term(1) = "1". */
    public static String recursive(int n) {
        if (n < 1) throw new IllegalArgumentException("n must be >= 1");
        return n == 1 ? "1" : say(recursive(n - 1));
    }

    /** Regex: "(.)\\1*" matches one maximal run of the same digit. */
    private static final Pattern RUN = Pattern.compile("(.)\\1*");

    public static String regex(int n) {
        if (n < 1) throw new IllegalArgumentException("n must be >= 1");
        String term = "1";
        for (int k = 2; k <= n; k++) {
            StringBuilder sb = new StringBuilder();
            Matcher m = RUN.matcher(term);
            while (m.find()) sb.append(m.group().length()).append(m.group(1));
            term = sb.toString();
        }
        return term;
    }

    /** One step: read s aloud. */
    static String say(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ) {
            int j = i;
            while (j < s.length() && s.charAt(j) == s.charAt(i)) j++;   // extend the run of s[i]
            sb.append(j - i).append(s.charAt(i));
            i = j;                                                     // jump to the next run
        }
        return sb.toString();
    }
}
