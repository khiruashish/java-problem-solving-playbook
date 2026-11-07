package com.playbook.greedy;

import java.util.Arrays;

/** Items can be taken FRACTIONALLY. Max value within capacity. Greedy is optimal here (unlike 0/1 knapsack). */
public final class FractionalKnapsack {
    private FractionalKnapsack() {}

    /** Sort by value/weight descending, take whole items while they fit, then a fraction of the next. O(n log n). */
    public static double maxValue(int[] weight, int[] value, int capacity) {
        Integer[] idx = new Integer[weight.length];
        for (int i = 0; i < idx.length; i++) idx[i] = i;
        // compare ratios without division: v1/w1 > v2/w2  ⇔  v1·w2 > v2·w1
        Arrays.sort(idx, (x, y) -> Long.compare((long) value[y] * weight[x], (long) value[x] * weight[y]));
        double total = 0;
        int left = capacity;
        for (int i : idx) {
            if (left == 0) break;
            if (weight[i] <= left) {
                total += value[i];
                left -= weight[i];
            } else {
                total += (double) value[i] * left / weight[i];   // the fraction that still fits
                left = 0;
            }
        }
        return total;
    }
}
