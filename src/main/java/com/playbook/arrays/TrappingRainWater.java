package com.playbook.arrays;

import java.util.ArrayDeque;
import java.util.Deque;

/** Water trapped between bars. [0,1,0,2,1,0,1,3,2,1,2,1] → 6. */
public final class TrappingRainWater {
    private TrappingRainWater() {}

    /** Brute force: for each bar, scan left max and right max. O(n^2). */
    public static int bruteForce(int[] h) {
        int water = 0;
        for (int i = 0; i < h.length; i++) {
            int leftMax = 0, rightMax = 0;
            for (int l = 0; l <= i; l++) leftMax = Math.max(leftMax, h[l]);
            for (int r = i; r < h.length; r++) rightMax = Math.max(rightMax, h[r]);
            water += Math.min(leftMax, rightMax) - h[i];
        }
        return water;
    }

    /** Precompute prefix-max and suffix-max arrays. O(n) time, O(n) space. */
    public static int prefixMax(int[] h) {
        int n = h.length;
        if (n == 0) return 0;
        int[] left = new int[n], right = new int[n];
        left[0] = h[0];
        for (int i = 1; i < n; i++) left[i] = Math.max(left[i - 1], h[i]);
        right[n - 1] = h[n - 1];
        for (int i = n - 2; i >= 0; i--) right[i] = Math.max(right[i + 1], h[i]);
        int water = 0;
        for (int i = 0; i < n; i++) water += Math.min(left[i], right[i]) - h[i];
        return water;
    }

    /**
     * Optimal: two pointers. The side with the smaller max is the bottleneck — its water level is
     * already known, so process it and move inward. O(n) time, O(1) space.
     */
    public static int twoPointers(int[] h) {
        int l = 0, r = h.length - 1, leftMax = 0, rightMax = 0, water = 0;
        while (l < r) {
            if (h[l] < h[r]) {
                leftMax = Math.max(leftMax, h[l]);
                water += leftMax - h[l++];
            } else {
                rightMax = Math.max(rightMax, h[r]);
                water += rightMax - h[r--];
            }
        }
        return water;
    }

    /** Monotonic decreasing stack: fill "layers" when a taller bar closes a basin. O(n). */
    public static int monotonicStack(int[] h) {
        Deque<Integer> stack = new ArrayDeque<>();
        int water = 0;
        for (int i = 0; i < h.length; i++) {
            while (!stack.isEmpty() && h[i] > h[stack.peek()]) {
                int bottom = stack.pop();
                if (stack.isEmpty()) break;                  // no left wall
                int left = stack.peek();
                int width = i - left - 1;
                int bounded = Math.min(h[left], h[i]) - h[bottom];
                water += width * bounded;
            }
            stack.push(i);
        }
        return water;
    }
}
