package com.playbook.greedy;

import java.util.Arrays;

/** Trains arrive/depart (same day); minimum platforms so no train waits. A train arriving when another departs needs a new platform (GfG rule: dep inclusive). */
public final class MinimumPlatforms {
    private MinimumPlatforms() {}

    /** Brute force: for each train, count trains present at its arrival time. O(n^2). */
    public static int bruteForce(int[] arr, int[] dep) {
        int best = 0;
        for (int i = 0; i < arr.length; i++) {
            int present = 0;
            for (int j = 0; j < arr.length; j++) if (arr[j] <= arr[i] && arr[i] <= dep[j]) present++;
            best = Math.max(best, present);
        }
        return best;
    }

    /**
     * Sweep: sort arrivals and departures independently (we only need COUNTS, not which train). Walk both with two
     * pointers: an arrival before (or at) the next departure adds a platform; otherwise a departure frees one. O(n log n).
     */
    public static int sweep(int[] arr, int[] dep) {
        int[] a = arr.clone(), d = dep.clone();
        Arrays.sort(a);
        Arrays.sort(d);
        int i = 0, j = 0, current = 0, best = 0;
        while (i < a.length) {
            if (a[i] <= d[j]) {                              // inclusive: arriving at a departure minute still overlaps
                current++;
                i++;
                best = Math.max(best, current);
            } else {
                current--;
                j++;
            }
        }
        return best;
    }
}
