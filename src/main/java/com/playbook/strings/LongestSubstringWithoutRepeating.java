package com.playbook.strings;

import java.util.HashSet;
import java.util.Set;

/** Length of the longest substring with all distinct characters ("abcabcbb" → 3, "abc"). */
public final class LongestSubstringWithoutRepeating {
    private LongestSubstringWithoutRepeating() {}

    /** Brute force: try every start, extend while unique. O(n^2) time. */
    public static int bruteForce(String s) {
        if (s == null) return 0;
        int best = 0;
        for (int i = 0; i < s.length(); i++) {
            Set<Character> seen = new HashSet<>();
            for (int j = i; j < s.length() && seen.add(s.charAt(j)); j++) {
                best = Math.max(best, j - i + 1);
            }
        }
        return best;
    }

    /** Sliding window with a set: grow right, shrink left while duplicate. O(n) — each char in/out once. */
    public static int slidingWindowSet(String s) {
        if (s == null) return 0;
        Set<Character> window = new HashSet<>();
        int best = 0, left = 0;
        for (int right = 0; right < s.length(); right++) {
            while (!window.add(s.charAt(right))) window.remove(s.charAt(left++));
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    /** Optimal: remember last index of each char and JUMP left past it. O(n), O(1) fixed alphabet. */
    public static int optimal(String s) {
        if (s == null) return 0;
        int[] lastSeen = new int[Character.MAX_VALUE + 1];   // stores index + 1; 0 = never seen
        int best = 0, left = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            left = Math.max(left, lastSeen[c]);              // never move left backwards
            best = Math.max(best, right - left + 1);
            lastSeen[c] = right + 1;
        }
        return best;
    }
}
