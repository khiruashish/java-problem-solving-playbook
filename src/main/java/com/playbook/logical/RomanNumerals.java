package com.playbook.logical;

import java.util.Map;

/** Integer ↔ Roman numeral for 1..3999 (standard subtractive form: IV, IX, XL, XC, CD, CM). */
public final class RomanNumerals {
    private RomanNumerals() {}

    // Greedy table, largest first, including the six subtractive pairs.
    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    /** Greedy: take the largest value that fits, repeatedly. At most 15 symbols, so O(1). */
    public static String toRoman(int num) {
        if (num < 1 || num > 3999) throw new IllegalArgumentException("supported range is 1..3999: " + num);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < VALUES.length; i++) {
            while (num >= VALUES[i]) {
                num -= VALUES[i];
                sb.append(SYMBOLS[i]);
            }
        }
        return sb.toString();
    }

    /** Digit table: one lookup per decimal place. No loops over values at all. */
    public static String toRomanByDigits(int num) {
        if (num < 1 || num > 3999) throw new IllegalArgumentException("supported range is 1..3999: " + num);
        String[] th = {"", "M", "MM", "MMM"};
        String[] h = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] t = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] o = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return th[num / 1000] + h[num / 100 % 10] + t[num / 10 % 10] + o[num % 10];
    }

    private static final Map<Character, Integer> VALUE =
            Map.of('I', 1, 'V', 5, 'X', 10, 'L', 50, 'C', 100, 'D', 500, 'M', 1000);

    /** Roman → int: add each symbol, but subtract it when a larger symbol follows (the "IV" rule). O(n). */
    public static int fromRoman(String s) {
        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int v = value(s.charAt(i));
            boolean smallerBeforeLarger = i + 1 < s.length() && v < value(s.charAt(i + 1));
            total += smallerBeforeLarger ? -v : v;
        }
        return total;
    }

    /** Right-to-left variant: subtract when the symbol is smaller than the running maximum seen to its right. */
    public static int fromRomanRightToLeft(String s) {
        int total = 0, maxRight = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int v = value(s.charAt(i));
            if (v < maxRight) total -= v;
            else {
                total += v;
                maxRight = v;
            }
        }
        return total;
    }

    private static int value(char c) {
        Integer v = VALUE.get(c);
        if (v == null) throw new IllegalArgumentException("not a Roman symbol: " + c);
        return v;
    }
}
