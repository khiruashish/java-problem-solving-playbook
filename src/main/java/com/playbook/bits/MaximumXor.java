package com.playbook.bits;

import java.util.HashSet;
import java.util.Set;

/** Maximum a[i] XOR a[j] over all pairs (i may equal j) of NON-NEGATIVE ints. [3,10,5,25,2,8] → 28 (5 ^ 25). */
public final class MaximumXor {
    private MaximumXor() {}

    /** Brute force: every pair. O(n^2). */
    public static int bruteForce(int[] a) {
        int best = 0;
        for (int x : a) for (int y : a) best = Math.max(best, x ^ y);
        return best;
    }

    /**
     * Bitwise trie: insert every number's 31 bits from the top. For each number, walk the trie preferring the
     * OPPOSITE bit at every level (a 1 in a higher bit beats anything below). O(31·n).
     */
    public static int trie(int[] a) {
        int[][] child = new int[a.length * 31 + 1][2];       // node 0 = root; 0 = "no child"
        int nodes = 1, best = 0;
        for (int x : a) {
            int node = 0;
            for (int b = 30; b >= 0; b--) {                  // insert
                int bit = (x >> b) & 1;
                if (child[node][bit] == 0) child[node][bit] = nodes++;
                node = child[node][bit];
            }
            node = 0;
            int cur = 0;
            for (int b = 30; b >= 0; b--) {                  // query: greedily take the opposite bit
                int want = ((x >> b) & 1) ^ 1;
                if (child[node][want] != 0) {
                    cur |= 1 << b;
                    node = child[node][want];
                } else node = child[node][want ^ 1];
            }
            best = Math.max(best, cur);
        }
        return best;
    }

    /**
     * Prefix greedy with a HashSet: decide the answer bit by bit from the top. Guess the next bit is 1 (candidate);
     * it's achievable iff two prefixes p, q exist with p ^ q == candidate ⇔ (candidate ^ p) is in the set. O(31·n).
     */
    public static int prefixSet(int[] a) {
        int best = 0, mask = 0;
        for (int b = 30; b >= 0; b--) {
            mask |= 1 << b;
            Set<Integer> prefixes = new HashSet<>();
            for (int x : a) prefixes.add(x & mask);
            int candidate = best | (1 << b);
            for (int p : prefixes) {
                if (prefixes.contains(candidate ^ p)) {
                    best = candidate;
                    break;
                }
            }
        }
        return best;
    }
}
