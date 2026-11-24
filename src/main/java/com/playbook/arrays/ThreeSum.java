package com.playbook.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 3Sum: all UNIQUE triplets [a, b, c] (as values, each sorted ascending) with a + b + c == 0.
 * Output order: triplets sorted lexicographically, so every approach returns the same list.
 */
public final class ThreeSum {
    private ThreeSum() {}

    /** Brute force: every i < j < k, dedupe with a sorted set. O(n³) time. */
    public static List<List<Integer>> bruteForce(int[] nums) {
        Set<List<Integer>> seen = new TreeSet<>(ThreeSum::compare);
        int n = nums.length;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                for (int k = j + 1; k < n; k++)
                    if ((long) nums[i] + nums[j] + nums[k] == 0) {
                        int[] t = {nums[i], nums[j], nums[k]};
                        Arrays.sort(t);
                        seen.add(List.of(t[0], t[1], t[2]));
                    }
        return new ArrayList<>(seen);
    }

    /** Fix i, then two-sum with a hash set on the rest. O(n²) time, O(n) space. */
    public static List<List<Integer>> hashing(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        Set<List<Integer>> out = new TreeSet<>(ThreeSum::compare);
        for (int i = 0; i < a.length; i++) {
            if (i > 0 && a[i] == a[i - 1]) continue;
            Set<Integer> seen = new HashSet<>();
            for (int j = i + 1; j < a.length; j++) {
                int need = -a[i] - a[j];
                if (seen.contains(need)) out.add(List.of(a[i], need, a[j]));   // need ≤ a[j]: it came earlier
                seen.add(a[j]);
            }
        }
        return new ArrayList<>(out);
    }

    /**
     * Optimal: sort, fix the smallest element, two pointers on the rest. Skip equal values at every level so each
     * triplet is produced exactly once — no Set needed. O(n²) time, O(1) extra (besides output and the sort).
     */
    public static List<List<Integer>> twoPointers(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 0; i < a.length - 2; i++) {
            if (a[i] > 0) break;                              // smallest is positive: no sum can reach 0
            if (i > 0 && a[i] == a[i - 1]) continue;          // same first value → same triplets
            int lo = i + 1, hi = a.length - 1;
            while (lo < hi) {
                long sum = (long) a[i] + a[lo] + a[hi];
                if (sum < 0) lo++;                            // need bigger: move the small end up
                else if (sum > 0) hi--;                       // need smaller: move the big end down
                else {
                    out.add(List.of(a[i], a[lo], a[hi]));
                    while (lo < hi && a[lo] == a[lo + 1]) lo++;   // skip duplicate second values
                    while (lo < hi && a[hi] == a[hi - 1]) hi--;   // skip duplicate third values
                    lo++;
                    hi--;
                }
            }
        }
        return out;
    }

    private static int compare(List<Integer> x, List<Integer> y) {
        for (int i = 0; i < 3; i++) {
            int c = Integer.compare(x.get(i), y.get(i));
            if (c != 0) return c;
        }
        return 0;
    }
}
