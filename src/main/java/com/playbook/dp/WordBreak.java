package com.playbook.dp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/** Can s be segmented into dictionary words (reusable)? "leetcode", [leet, code] → true. */
public final class WordBreak {
    private WordBreak() {}

    /** Recursion: try every dictionary word as a prefix. Exponential on inputs like "aaaa…ab". */
    public static boolean recursive(String s, List<String> dict) {
        if (s.isEmpty()) return true;
        for (String w : dict) if (s.startsWith(w) && recursive(s.substring(w.length()), dict)) return true;
        return false;
    }

    /** Memoized on the start index. O(n · |dict| · L). */
    public static boolean memo(String s, List<String> dict) {
        return memo(s, 0, dict, new HashMap<>());
    }

    private static boolean memo(String s, int start, List<String> dict, Map<Integer, Boolean> cache) {
        if (start == s.length()) return true;
        Boolean hit = cache.get(start);
        if (hit != null) return hit;
        boolean ok = false;
        for (String w : dict) {
            if (s.startsWith(w, start) && memo(s, start + w.length(), dict, cache)) {
                ok = true;
                break;
            }
        }
        cache.put(start, ok);
        return ok;
    }

    /**
     * Bottom-up: dp[i] = prefix s[0..i) can be segmented. dp[i] is true if some j < i has dp[j] and s[j..i) is a word.
     * Limit j to the longest word length. O(n · maxLen) substring checks.
     */
    public static boolean dp(String s, List<String> dict) {
        Set<String> words = new HashSet<>(dict);
        int maxLen = 0;
        for (String w : dict) maxLen = Math.max(maxLen, w.length());
        boolean[] can = new boolean[s.length() + 1];
        can[0] = true;
        for (int i = 1; i <= s.length(); i++)
            for (int j = Math.max(0, i - maxLen); j < i && !can[i]; j++)
                can[i] = can[j] && words.contains(s.substring(j, i));
        return can[s.length()];
    }
}
