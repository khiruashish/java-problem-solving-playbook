package com.playbook.logical;

/** Convert integers between bases 2..36 (digits 0-9 then a-z), without Integer.toString(n, radix) as the main answer. */
public final class BaseConversion {
    private BaseConversion() {}

    private static final String DIGITS = "0123456789abcdefghijklmnopqrstuvwxyz";

    /** Repeated division: remainders are the digits from LEAST significant upward → reverse at the end. */
    public static String toBase(long n, int base) {
        check(base);
        if (n == 0) return "0";
        boolean negative = n < 0;
        StringBuilder sb = new StringBuilder();
        long x = Math.abs(n);                                // fine for |n| < 2^63
        while (x > 0) {
            sb.append(DIGITS.charAt((int) (x % base)));
            x /= base;
        }
        if (negative) sb.append('-');
        return sb.reverse().toString();
    }

    /** Recursive: convert n / base first (the higher digits), then append the last digit — no reversal needed. */
    public static String toBaseRecursive(long n, int base) {
        check(base);
        if (n < 0) return "-" + toBaseRecursive(-n, base);
        if (n < base) return String.valueOf(DIGITS.charAt((int) n));
        return toBaseRecursive(n / base, base) + DIGITS.charAt((int) (n % base));
    }

    /** Parse: Horner's rule — value = value * base + digit, left to right. */
    public static long fromBase(String s, int base) {
        check(base);
        boolean negative = s.startsWith("-");
        long value = 0;
        for (char c : (negative ? s.substring(1) : s).toLowerCase().toCharArray()) {
            int d = DIGITS.indexOf(c);
            if (d < 0 || d >= base) throw new IllegalArgumentException("bad digit '" + c + "' for base " + base);
            value = value * base + d;
        }
        return negative ? -value : value;
    }

    /** Any base to any base via a long in the middle. */
    public static String convert(String s, int from, int to) {
        return toBase(fromBase(s, from), to);
    }

    private static void check(int base) {
        if (base < 2 || base > 36) throw new IllegalArgumentException("base must be 2..36");
    }
}
