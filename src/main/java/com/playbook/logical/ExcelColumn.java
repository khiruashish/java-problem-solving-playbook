package com.playbook.logical;

/**
 * Excel column titles: A..Z = 1..26, AA = 27, AZ = 52, BA = 53, ZZ = 702, AAA = 703.
 * This is "bijective base 26": digits run 1..26 and there is no zero, which is why naive base conversion fails.
 */
public final class ExcelColumn {
    private ExcelColumn() {}

    /** Title → number with Horner's rule: n = n * 26 + digit, digit in 1..26. O(L). */
    public static long toNumber(String title) {
        if (title == null || title.isEmpty()) throw new IllegalArgumentException("empty title");
        long n = 0;
        for (int i = 0; i < title.length(); i++) {
            char c = title.charAt(i);
            if (c < 'A' || c > 'Z') throw new IllegalArgumentException("not A..Z: " + c);
            n = n * 26 + (c - 'A' + 1);
        }
        return n;
    }

    /**
     * Number → title. Shift to 0-based before each division: (n - 1) % 26 picks the letter, (n - 1) / 26 is the rest.
     * Without the -1, 26 would become "A@" instead of "Z". O(log₂₆ n).
     */
    public static String toTitle(long n) {
        if (n < 1) throw new IllegalArgumentException("n must be >= 1");
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            n--;                                        // make this digit 0-based
            sb.append((char) ('A' + n % 26));
            n /= 26;
        }
        return sb.reverse().toString();
    }

    /** Recursive form of toTitle: title(n) = title((n-1)/26) + letter((n-1) % 26). */
    public static String toTitleRecursive(long n) {
        if (n < 1) throw new IllegalArgumentException("n must be >= 1");
        long m = n - 1;
        String last = String.valueOf((char) ('A' + m % 26));
        return m / 26 == 0 ? last : toTitleRecursive(m / 26) + last;
    }

    /** Brute force for tests: count up from "A" with an odometer increment. O(n · L). */
    public static String toTitleBrute(int n) {
        char[] t = {'A'};
        for (int k = 1; k < n; k++) t = increment(t);
        return new String(t);
    }

    private static char[] increment(char[] t) {
        for (int i = t.length - 1; i >= 0; i--) {
            if (t[i] != 'Z') {
                t[i]++;
                return t;
            }
            t[i] = 'A';                                  // carry
        }
        char[] grown = new char[t.length + 1];          // all Z → one longer, all A
        java.util.Arrays.fill(grown, 'A');
        return grown;
    }
}
