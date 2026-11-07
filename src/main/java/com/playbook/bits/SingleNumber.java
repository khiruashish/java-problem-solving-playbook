package com.playbook.bits;

import java.util.HashMap;
import java.util.Map;

/**
 * I:   every element appears twice except one → find it.
 * II:  every element appears three times except one → find it.
 * III: every element appears twice except TWO → find both (returned sorted).
 */
public final class SingleNumber {
    private SingleNumber() {}

    /** Hash map count — works for all variants, O(n) space. */
    public static int countingI(int[] a) {
        Map<Integer, Integer> c = new HashMap<>();
        for (int x : a) c.merge(x, 1, Integer::sum);
        for (Map.Entry<Integer, Integer> e : c.entrySet()) if (e.getValue() == 1) return e.getKey();
        throw new IllegalArgumentException("no single element");
    }

    /** I: XOR everything. x ^ x = 0 and x ^ 0 = x, XOR is commutative → pairs cancel. O(n), O(1). */
    public static int xorI(int[] a) {
        int r = 0;
        for (int x : a) r ^= x;
        return r;
    }

    /** II: for each of the 32 bit positions, count ones; mod 3 leaves the single number's bit. O(32n), O(1). */
    public static int bitCountII(int[] a) {
        int result = 0;
        for (int bit = 0; bit < 32; bit++) {
            int ones = 0;
            for (int x : a) ones += (x >>> bit) & 1;
            if (ones % 3 != 0) result |= 1 << bit;
        }
        return result;
    }

    /**
     * II, expert: two-variable state machine. `ones` holds bits seen 1 (mod 3) times, `twos` bits seen 2 times;
     * a third occurrence clears both. O(n), O(1), no per-bit loop.
     */
    public static int stateMachineII(int[] a) {
        int ones = 0, twos = 0;
        for (int x : a) {
            ones = (ones ^ x) & ~twos;
            twos = (twos ^ x) & ~ones;
        }
        return ones;
    }

    /**
     * III: XOR of all = p ^ q (non-zero since p != q). Any set bit of it is a bit where p and q differ;
     * split the numbers by that bit → each group contains one of them plus cancelling pairs.
     */
    public static int[] xorIII(int[] a) {
        int both = 0;
        for (int x : a) both ^= x;
        int diffBit = both & -both;                          // lowest bit where p and q differ
        int p = 0;
        for (int x : a) if ((x & diffBit) != 0) p ^= x;
        int q = both ^ p;
        return p < q ? new int[]{p, q} : new int[]{q, p};
    }
}
