package com.playbook.recursion;

import java.util.ArrayList;
import java.util.List;

/** All well-formed strings of n pairs of parentheses, in lexicographic order ('(' < ')'). n=3 → 5 strings (Catalan). */
public final class GenerateParentheses {
    private GenerateParentheses() {}

    /** Brute force: generate all 2^(2n) strings, keep the valid ones. O(2^(2n) · n). */
    public static List<String> bruteForce(int n) {
        List<String> out = new ArrayList<>();
        all(new char[2 * n], 0, out);
        return out;
    }

    private static void all(char[] cur, int pos, List<String> out) {
        if (pos == cur.length) {
            if (valid(cur)) out.add(new String(cur));
            return;
        }
        cur[pos] = '(';
        all(cur, pos + 1, out);
        cur[pos] = ')';
        all(cur, pos + 1, out);
    }

    private static boolean valid(char[] s) {
        int bal = 0;
        for (char c : s) if ((bal += c == '(' ? 1 : -1) < 0) return false;
        return bal == 0;
    }

    /**
     * Backtracking with pruning: only add '(' while open < n, only add ')' while close < open.
     * Every leaf is valid — no wasted work. Generates exactly Catalan(n) strings.
     */
    public static List<String> backtracking(int n) {
        List<String> out = new ArrayList<>();
        build(new StringBuilder(), 0, 0, n, out);
        return out;
    }

    private static void build(StringBuilder sb, int open, int close, int n, List<String> out) {
        if (sb.length() == 2 * n) {
            out.add(sb.toString());
            return;
        }
        if (open < n) {
            sb.append('(');
            build(sb, open + 1, close, n, out);
            sb.deleteCharAt(sb.length() - 1);                // backtrack
        }
        if (close < open) {
            sb.append(')');
            build(sb, open, close + 1, n, out);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
