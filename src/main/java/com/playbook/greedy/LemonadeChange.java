package com.playbook.greedy;

/**
 * Lemonade costs 5. Customers pay with 5, 10 or 20, in order; you start with no change.
 * Can you give every customer correct change?
 */
public final class LemonadeChange {
    private LemonadeChange() {}

    /**
     * Greedy: for a 20, prefer giving 10 + 5 over 5 + 5 + 5 — fives are strictly more useful (they serve both 10s and
     * 20s), tens only serve 20s. O(n), O(1).
     */
    public static boolean greedy(int[] bills) {
        int fives = 0, tens = 0;
        for (int b : bills) {
            switch (b) {
                case 5 -> fives++;
                case 10 -> {
                    if (fives == 0) return false;
                    fives--;
                    tens++;
                }
                case 20 -> {
                    if (tens > 0 && fives > 0) { tens--; fives--; }      // keep fives for later
                    else if (fives >= 3) fives -= 3;
                    else return false;
                }
                default -> throw new IllegalArgumentException("bill must be 5, 10 or 20: " + b);
            }
        }
        return true;
    }

    /**
     * Brute force for tests: explore BOTH ways to change a 20 (when both are possible) with DFS over
     * (customer, fives, tens). Proves the greedy choice never loses.
     */
    public static boolean exhaustive(int[] bills) {
        return dfs(bills, 0, 0, 0);
    }

    private static boolean dfs(int[] b, int i, int fives, int tens) {
        if (i == b.length) return true;
        return switch (b[i]) {
            case 5 -> dfs(b, i + 1, fives + 1, tens);
            case 10 -> fives > 0 && dfs(b, i + 1, fives - 1, tens + 1);
            case 20 -> (tens > 0 && fives > 0 && dfs(b, i + 1, fives - 1, tens - 1))
                    || (fives >= 3 && dfs(b, i + 1, fives - 3, tens));
            default -> throw new IllegalArgumentException("bill must be 5, 10 or 20");
        };
    }
}
