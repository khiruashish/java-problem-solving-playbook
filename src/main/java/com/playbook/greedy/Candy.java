package com.playbook.greedy;

import java.util.Arrays;

/**
 * Children stand in a line with ratings. Each gets ≥ 1 candy; a child with a HIGHER rating than a neighbour must get
 * MORE candy than that neighbour. Minimum total candies?
 */
public final class Candy {
    private Candy() {}

    /** Brute force: start everyone at 1 and keep fixing violations until stable. O(n²) worst case. */
    public static int relaxation(int[] ratings) {
        int n = ratings.length;
        int[] c = new int[n];
        Arrays.fill(c, 1);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i < n; i++) {
                if (i > 0 && ratings[i] > ratings[i - 1] && c[i] <= c[i - 1]) { c[i] = c[i - 1] + 1; changed = true; }
                if (i + 1 < n && ratings[i] > ratings[i + 1] && c[i] <= c[i + 1]) { c[i] = c[i + 1] + 1; changed = true; }
            }
        }
        return Arrays.stream(c).sum();
    }

    /**
     * Two passes: left→right satisfies every "higher than LEFT neighbour" rule; right→left satisfies every
     * "higher than RIGHT neighbour" rule while keeping the first (take the max). Each child gets exactly the larger
     * of its two run lengths, which is the minimum possible. O(n) time, O(n) space.
     */
    public static int twoPass(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;
        int[] c = new int[n];
        Arrays.fill(c, 1);
        for (int i = 1; i < n; i++) if (ratings[i] > ratings[i - 1]) c[i] = c[i - 1] + 1;
        for (int i = n - 2; i >= 0; i--) if (ratings[i] > ratings[i + 1]) c[i] = Math.max(c[i], c[i + 1] + 1);
        int total = 0;
        for (int x : c) total += x;
        return total;
    }

    /**
     * One pass, O(1) space: walk the "mountains". Track the current up-run length, down-run length, and the peak
     * height. A down-run of length d costs 1+2+…+d; when it grows past the peak, the peak must grow too (+1).
     */
    public static int onePass(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;
        int total = 1, up = 0, down = 0, peak = 0;
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                up++;
                down = 0;
                peak = up;
                total += 1 + up;                 // this child gets one more than the previous
            } else if (ratings[i] == ratings[i - 1]) {
                up = down = peak = 0;
                total += 1;                      // equal neighbours: no constraint, reset to 1
            } else {
                up = 0;
                down++;
                // the new child gets 1 and the earlier children of this down-run each rise by 1: that is +down.
                // Once the down-run is longer than the climb to the peak, the peak must rise as well: +1.
                total += down + (down > peak ? 1 : 0);
            }
        }
        return total;
    }
}
