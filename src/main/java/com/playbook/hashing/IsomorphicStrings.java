package com.playbook.hashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * s and t are isomorphic if the characters of s can be replaced (consistently, one-to-one) to get t.
 * "egg"/"add" → true; "foo"/"bar" → false; "badc"/"baba" → false (two letters can't map to the same one).
 */
public final class IsomorphicStrings {
    private IsomorphicStrings() {}

    /** Two maps: s→t and t→s must both stay consistent (a bijection). O(n) time, O(alphabet) space. */
    public static boolean twoMaps(String s, String t) {
        if (s.length() != t.length()) return false;
        Map<Character, Character> st = new HashMap<>(), ts = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i), b = t.charAt(i);
            Character mappedA = st.putIfAbsent(a, b), mappedB = ts.putIfAbsent(b, a);
            if ((mappedA != null && mappedA != b) || (mappedB != null && mappedB != a)) return false;
        }
        return true;
    }

    /**
     * Last-seen positions: two strings are isomorphic iff every position has the same "pattern" — each char was last
     * seen at the same index in both strings. One int[] per string, no maps. O(n).
     */
    public static boolean lastSeen(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] ls = new int[65536], lt = new int[65536];          // full char range; 0 = never seen
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i), b = t.charAt(i);
            if (ls[a] != lt[b]) return false;                     // different history → different structure
            ls[a] = lt[b] = i + 1;                                 // +1 so that 0 keeps meaning "unseen"
        }
        return true;
    }

    /** Canonical form: replace each char by the index of its first occurrence. "paper" → [0,1,0,3,4]. */
    public static boolean canonical(String s, String t) {
        return s.length() == t.length() && Arrays.equals(pattern(s), pattern(t));
    }

    static int[] pattern(String s) {
        Map<Character, Integer> first = new HashMap<>();
        int[] p = new int[s.length()];
        for (int i = 0; i < s.length(); i++) p[i] = first.computeIfAbsent(s.charAt(i), c -> first.size());
        return p;
    }
}
