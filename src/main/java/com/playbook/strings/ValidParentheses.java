package com.playbook.strings;

import java.util.ArrayDeque;
import java.util.Deque;

/** Brackets "()[]{}" are balanced if every opener is closed by the same type in the correct order. */
public final class ValidParentheses {
    private ValidParentheses() {}

    /** Brute force: repeatedly delete "()", "[]", "{}" until nothing changes. O(n^2). */
    public static boolean bruteForce(String s) {
        if (s == null) return false;
        String prev;
        do {
            prev = s;
            s = s.replace("()", "").replace("[]", "").replace("{}", "");
        } while (!s.equals(prev));
        return s.isEmpty();
    }

    /** Optimal: stack of expected closers. O(n) time, O(n) space. */
    public static boolean optimal(String s) {
        if (s == null) return false;
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '(' -> stack.push(')');                 // push what we EXPECT to see later
                case '[' -> stack.push(']');
                case '{' -> stack.push('}');
                default -> {
                    if (stack.isEmpty() || stack.pop() != c) return false;
                }
            }
        }
        return stack.isEmpty();                              // leftover openers → unbalanced
    }

    /** Only one bracket type? A counter is enough. O(1) space. */
    public static boolean singleTypeCounter(String s) {
        if (s == null) return false;
        int open = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') open++;
            else if (c == ')' && --open < 0) return false;   // closed more than opened
        }
        return open == 0;
    }
}
