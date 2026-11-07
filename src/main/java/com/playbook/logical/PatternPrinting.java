package com.playbook.logical;

import java.util.ArrayList;
import java.util.List;

/**
 * Classic pattern programs. They return lines (no System.out) so they can be tested; print with lines.forEach(System.out::println).
 * The trick to every pattern: for row i, work out (1) leading spaces and (2) what to print, as formulas of i and n.
 */
public final class PatternPrinting {
    private PatternPrinting() {}

    /** Right triangle of stars: row i has i stars. */
    public static List<String> rightTriangle(int n) {
        List<String> out = new ArrayList<>();
        for (int i = 1; i <= n; i++) out.add("*".repeat(i));
        return out;
    }

    /** Centered pyramid: row i has (n - i) spaces and (2i - 1) stars. */
    public static List<String> pyramid(int n) {
        List<String> out = new ArrayList<>();
        for (int i = 1; i <= n; i++) out.add(" ".repeat(n - i) + "*".repeat(2 * i - 1));
        return out;
    }

    /** Diamond: pyramid of n rows + inverted pyramid of n-1 rows. */
    public static List<String> diamond(int n) {
        List<String> out = new ArrayList<>(pyramid(n));
        for (int i = n - 1; i >= 1; i--) out.add(" ".repeat(n - i) + "*".repeat(2 * i - 1));
        return out;
    }

    /** Floyd's triangle: consecutive numbers, row i has i numbers. */
    public static List<String> floydsTriangle(int n) {
        List<String> out = new ArrayList<>();
        int next = 1;
        for (int i = 1; i <= n; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < i; j++) row.append(j == 0 ? "" : " ").append(next++);
            out.add(row.toString());
        }
        return out;
    }

    /** Pascal's triangle values: each entry is the sum of the two above; edges are 1. */
    public static List<List<Long>> pascal(int n) {
        List<List<Long>> rows = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Long> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                row.add(j == 0 || j == i ? 1L : rows.get(i - 1).get(j - 1) + rows.get(i - 1).get(j));
            }
            rows.add(row);
        }
        return rows;
    }

    /** Pascal row k directly with C(k, j+1) = C(k, j) * (k - j) / (j + 1) — O(k), no previous rows. */
    public static List<Long> pascalRow(int k) {
        List<Long> row = new ArrayList<>();
        long c = 1;
        for (int j = 0; j <= k; j++) {
            row.add(c);
            c = c * (k - j) / (j + 1);
        }
        return row;
    }

    /** Hollow square of side n. */
    public static List<String> hollowSquare(int n) {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i == 0 || i == n - 1) out.add("*".repeat(n));
            else out.add(n == 1 ? "*" : "*" + " ".repeat(n - 2) + "*");
        }
        return out;
    }
}
