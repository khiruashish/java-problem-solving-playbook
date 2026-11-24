package com.playbook.searchsort;

/**
 * Koko eats from one pile per hour, at most `speed` bananas from it (a smaller pile is finished and the rest of the
 * hour is wasted). Find the minimum integer speed that finishes all piles within h hours (h ≥ number of piles).
 */
public final class KokoEatingBananas {
    private KokoEatingBananas() {}

    /** Hours needed at a given speed: Σ ceil(pile / speed), computed without floating point. */
    static long hoursAt(int[] piles, int speed) {
        long hours = 0;
        for (int p : piles) hours += (p + (long) speed - 1) / speed;   // ceil division in long (no overflow)
        return hours;
    }

    /** Brute force: try speeds 1, 2, 3, ... until one works. O(max · n). Fine only for tiny piles. */
    public static int linear(int[] piles, int h) {
        validate(piles, h);
        for (int speed = 1; ; speed++) if (hoursAt(piles, speed) <= h) return speed;
    }

    /**
     * Binary search on the ANSWER: feasibility is monotonic (a faster speed never needs more hours), so search
     * [1, max pile] for the first feasible speed. O(n log max).
     */
    public static int binarySearch(int[] piles, int h) {
        validate(piles, h);
        int lo = 1, hi = 0;
        for (int p : piles) hi = Math.max(hi, p);                  // speed = max pile always works (1 hour each)
        while (lo < hi) {                                          // invariant: answer in [lo, hi]
            int mid = lo + (hi - lo) / 2;
            if (hoursAt(piles, mid) <= h) hi = mid;               // mid works: answer is mid or smaller
            else lo = mid + 1;                                     // mid too slow: answer is bigger
        }
        return lo;
    }

    /** Tighter starting bounds: speed ≥ ceil(total / h), and speed ≥ 1. Same algorithm, fewer iterations. */
    public static int binarySearchTightBounds(int[] piles, int h) {
        validate(piles, h);
        long total = 0;
        int max = 0;
        for (int p : piles) { total += p; max = Math.max(max, p); }
        int lo = (int) Math.max(1, (total + h - 1) / h), hi = max;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (hoursAt(piles, mid) <= h) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    private static void validate(int[] piles, int h) {
        if (piles.length == 0 || h < piles.length) throw new IllegalArgumentException("need h >= number of piles");
        for (int p : piles) if (p < 1) throw new IllegalArgumentException("piles must be positive");
    }
}
