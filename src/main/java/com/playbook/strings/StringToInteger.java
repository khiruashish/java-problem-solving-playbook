package com.playbook.strings;

/**
 * atoi: parse a 32-bit signed integer like C's atoi / LeetCode 8.
 * Rules: skip leading spaces, optional '+' or '-', then digits until the first non-digit.
 * No digits → 0. Out of range → clamp to Integer.MIN_VALUE / MAX_VALUE.
 */
public final class StringToInteger {
    private StringToInteger() {}

    /** Brute force: collect the digit run, parse with BigInteger-free long, clamp. Uses long as a wider buffer. */
    public static int withLong(String s) {
        if (s == null) return 0;
        int i = 0, n = s.length();
        while (i < n && s.charAt(i) == ' ') i++;
        int sign = 1;
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) sign = s.charAt(i++) == '-' ? -1 : 1;
        long value = 0;
        while (i < n && Character.isDigit(s.charAt(i)) && s.charAt(i) <= '9') {
            value = value * 10 + (s.charAt(i++) - '0');
            if (value > (long) Integer.MAX_VALUE + 1) break;          // already out of range; stop growing
        }
        value *= sign;
        if (value > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if (value < Integer.MIN_VALUE) return Integer.MIN_VALUE;
        return (int) value;
    }

    /** Optimal: int only, overflow checked BEFORE each multiply-add. O(n) time, O(1) space. */
    public static int optimal(String s) {
        if (s == null) return 0;
        int i = 0, n = s.length();
        while (i < n && s.charAt(i) == ' ') i++;                     // 1. leading spaces
        int sign = 1;
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {  // 2. one optional sign
            sign = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }
        int result = 0;
        while (i < n) {                                              // 3. digits
            char c = s.charAt(i);
            if (c < '0' || c > '9') break;
            int d = c - '0';
            // result * 10 + d > MAX  ⇔  result > (MAX - d) / 10 ; checked before it can overflow
            if (result > (Integer.MAX_VALUE - d) / 10) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            result = result * 10 + d;
            i++;
        }
        return sign * result;
    }

    /** Deterministic finite automaton: states start → signed → number, any other char ends the parse. */
    public static int automaton(String s) {
        if (s == null) return 0;
        enum State { START, SIGNED, NUMBER }
        State state = State.START;
        int sign = 1;
        long value = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean digit = c >= '0' && c <= '9';
            if (state == State.START && c == ' ') continue;
            if (state == State.START && (c == '+' || c == '-')) {
                sign = c == '-' ? -1 : 1;
                state = State.SIGNED;
            } else if (digit) {
                state = State.NUMBER;
                value = Math.min(value * 10 + (c - '0'), (long) Integer.MAX_VALUE + 1);  // saturate
            } else {
                break;                                                // END state
            }
        }
        long signed = sign * value;
        return (int) Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, signed));
    }
}
