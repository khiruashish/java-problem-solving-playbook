package com.playbook.arrays;

/** Maximum sum of a non-empty contiguous subarray (Kadane). [-2,1,-3,4,-1,2,1,-5,4] → 6. */
public final class MaxSubarraySum {
    private MaxSubarraySum() {}

    /** Brute force: all subarrays with running sum. O(n^2). */
    public static int bruteForce(int[] a) {
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            int sum = 0;
            for (int j = i; j < a.length; j++) {
                sum += a[j];
                best = Math.max(best, sum);
            }
        }
        return best;
    }

    /** Kadane: best subarray ending here = max(a[i], a[i] + best ending at i-1). O(n), O(1). */
    public static int kadane(int[] a) {
        int current = a[0], best = a[0];
        for (int i = 1; i < a.length; i++) {
            current = Math.max(a[i], current + a[i]);        // extend or restart
            best = Math.max(best, current);
        }
        return best;
    }

    /** Kadane returning the subarray bounds {sum, start, end}. */
    public static int[] kadaneWithIndices(int[] a) {
        int current = a[0], best = a[0], start = 0, bestStart = 0, bestEnd = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] > current + a[i]) {
                current = a[i];
                start = i;                                   // restart here
            } else {
                current += a[i];
            }
            if (current > best) {
                best = current;
                bestStart = start;
                bestEnd = i;
            }
        }
        return new int[]{best, bestStart, bestEnd};
    }

    /** Divide & conquer (recursive): max of left, right, crossing. O(n log n). */
    public static int divideAndConquer(int[] a) {
        return dc(a, 0, a.length - 1);
    }

    private static int dc(int[] a, int lo, int hi) {
        if (lo == hi) return a[lo];
        int mid = (lo + hi) >>> 1;
        int leftBest = Integer.MIN_VALUE, sum = 0;
        for (int i = mid; i >= lo; i--) leftBest = Math.max(leftBest, sum += a[i]);
        int rightBest = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= hi; i++) rightBest = Math.max(rightBest, sum += a[i]);
        return Math.max(Math.max(dc(a, lo, mid), dc(a, mid + 1, hi)), leftBest + rightBest);
    }
}
