package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;

/** For each day, how many days until a warmer temperature (0 if never). [73,74,75,71,69,72,76,73] → [1,1,4,2,1,1,0,0]. */
public final class DailyTemperatures {
    private DailyTemperatures() {}

    /** Brute force: scan forward for each day. O(n^2). */
    public static int[] bruteForce(int[] t) {
        int[] out = new int[t.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = i + 1; j < t.length; j++) {
                if (t[j] > t[i]) {
                    out[i] = j - i;
                    break;
                }
            }
        }
        return out;
    }

    /** Monotonic stack of INDEXES (we need distances, not values). Each index pushed/popped once → O(n). */
    public static int[] monotonicStack(int[] t) {
        int[] out = new int[t.length];
        Deque<Integer> waiting = new ArrayDeque<>();         // indexes with decreasing temperatures
        for (int i = 0; i < t.length; i++) {
            while (!waiting.isEmpty() && t[waiting.peek()] < t[i]) {
                int day = waiting.pop();
                out[day] = i - day;                          // today is the first warmer day for `day`
            }
            waiting.push(i);
        }
        return out;
    }

    /**
     * O(1) extra space: walk right to left and jump using answers already computed
     * (if day j is not warmer, the next candidate is j + out[j]).
     */
    public static int[] backwardJumps(int[] t) {
        int n = t.length;
        int[] out = new int[n];
        for (int i = n - 2; i >= 0; i--) {
            int j = i + 1;
            while (t[j] <= t[i] && out[j] != 0) j += out[j];
            out[i] = t[j] > t[i] ? j - i : 0;
        }
        return out;
    }
}
