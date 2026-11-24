package com.playbook.logical;

import java.util.HashSet;
import java.util.Set;

/**
 * Happy number: repeatedly replace n by the sum of the squares of its digits. Happy if this reaches 1;
 * otherwise the sequence falls into a cycle that never contains 1 (for base 10 it is the cycle through 4).
 */
public final class HappyNumber {
    private HappyNumber() {}

    /** Sum of squares of the decimal digits of n (n ≥ 0). */
    static int next(int n) {
        int sum = 0;
        while (n > 0) {
            int d = n % 10;
            sum += d * d;
            n /= 10;
        }
        return sum;
    }

    /** Hash set of seen values: stop at 1 (happy) or at a repeat (cycle). O(log n) per step, O(k) space. */
    public static boolean hashSet(int n) {
        if (n <= 0) return false;
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && seen.add(n)) n = next(n);
        return n == 1;
    }

    /**
     * Floyd's tortoise and hare on the implicit linked list n → next(n). Every sequence ends in a cycle
     * (1 → 1 is a cycle too), so the pointers meet; the number is happy iff they meet at 1. O(1) space.
     */
    public static boolean floyd(int n) {
        if (n <= 0) return false;
        int slow = n, fast = next(n);
        while (fast != 1 && slow != fast) {
            slow = next(slow);
            fast = next(next(fast));
        }
        return fast == 1;
    }

    /** Math shortcut: every unhappy number in base 10 eventually hits 4. */
    public static boolean cycleThroughFour(int n) {
        if (n <= 0) return false;
        while (n != 1 && n != 4) n = next(n);
        return n == 1;
    }

    /** Recursive form of the hash-set approach. */
    public static boolean recursive(int n) {
        return n > 0 && recursive(n, new HashSet<>());
    }

    private static boolean recursive(int n, Set<Integer> seen) {
        if (n == 1) return true;                       // base case: happy
        if (!seen.add(n)) return false;                // base case: entered a cycle
        return recursive(next(n), seen);
    }
}
