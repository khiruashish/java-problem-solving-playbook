package com.playbook.greedy;

import java.util.Arrays;

/** Child i is content with a cookie of size ≥ greed[i]; each child gets at most one cookie. Max content children. */
public final class AssignCookies {
    private AssignCookies() {}

    /** Brute force: try giving each child each unused cookie (or nothing). Exponential — reference only. */
    public static int bruteForce(int[] greed, int[] cookies) {
        return brute(greed, 0, cookies, new boolean[cookies.length]);
    }

    private static int brute(int[] g, int i, int[] s, boolean[] used) {
        if (i == g.length) return 0;
        int best = brute(g, i + 1, s, used);                 // child i gets nothing
        for (int j = 0; j < s.length; j++) {
            if (!used[j] && s[j] >= g[i]) {
                used[j] = true;
                best = Math.max(best, 1 + brute(g, i + 1, s, used));
                used[j] = false;
            }
        }
        return best;
    }

    /**
     * Greedy: sort both; give the SMALLEST cookie that satisfies the LEAST greedy remaining child.
     * Exchange argument: any optimal assignment can be rearranged into this one without losing a child. O(n log n).
     */
    public static int greedy(int[] greed, int[] cookies) {
        int[] g = greed.clone(), s = cookies.clone();
        Arrays.sort(g);
        Arrays.sort(s);
        int child = 0;
        for (int cookie = 0; cookie < s.length && child < g.length; cookie++) {
            if (s[cookie] >= g[child]) child++;              // satisfied; otherwise this cookie is too small for everyone left
        }
        return child;
    }
}
