package com.playbook.searchsort;

/** Sort an array containing only 0, 1, 2 in place (Dutch National Flag). [2,0,2,1,1,0] → [0,0,1,1,2,2]. */
public final class SortColors {
    private SortColors() {}

    /** Two passes: count each colour, then overwrite. O(n), O(1). Simple but not one-pass. */
    public static void counting(int[] a) {
        int[] c = new int[3];
        for (int x : a) c[x]++;
        int k = 0;
        for (int colour = 0; colour < 3; colour++) for (int i = 0; i < c[colour]; i++) a[k++] = colour;
    }

    /**
     * Dijkstra's Dutch National Flag, one pass, three pointers:
     *   [0, low) = 0s · [low, mid) = 1s · [mid, high] = unknown · (high, n) = 2s.
     */
    public static void dutchFlag(int[] a) {
        int low = 0, mid = 0, high = a.length - 1;
        while (mid <= high) {
            switch (a[mid]) {
                case 0 -> swap(a, low++, mid++);             // swapped-in value from low is a known 1 → mid can advance
                case 1 -> mid++;
                default -> swap(a, mid, high--);             // swapped-in value from high is UNKNOWN → don't advance mid
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
