package com.playbook.greedy;

/** Circular route: gas[i] fuel at station i, cost[i] to drive to i+1. Start index to complete the loop, or -1 (unique if exists). */
public final class GasStation {
    private GasStation() {}

    /** Brute force: simulate from every start. O(n^2). */
    public static int bruteForce(int[] gas, int[] cost) {
        int n = gas.length;
        for (int s = 0; s < n; s++) {
            long tank = 0;
            int k;
            for (k = 0; k < n; k++) {
                int i = (s + k) % n;
                tank += gas[i] - cost[i];
                if (tank < 0) break;
            }
            if (k == n) return s;
        }
        return -1;
    }

    /**
     * Greedy O(n): (1) if total gas < total cost, impossible. (2) If the tank goes negative arriving after station i,
     * then NO start between the current start and i works either (they'd arrive with even less) → restart at i+1.
     */
    public static int greedy(int[] gas, int[] cost) {
        long total = 0, tank = 0;
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            total += gas[i] - cost[i];
            tank += gas[i] - cost[i];
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        return total < 0 ? -1 : start;
    }
}
