package com.playbook.recursion;

import java.util.ArrayList;
import java.util.List;

/** Move n disks from A to C using B. Returns the moves as "disk k: X->Y". Always 2^n − 1 moves. */
public final class TowerOfHanoi {
    private TowerOfHanoi() {}

    /**
     * Recursive: (1) move n-1 from source to helper, (2) move disk n to target, (3) move n-1 helper → target.
     * T(n) = 2T(n-1) + 1 → 2^n − 1.
     */
    public static List<String> recursive(int n) {
        List<String> moves = new ArrayList<>();
        solve(n, 'A', 'C', 'B', moves);
        return moves;
    }

    private static void solve(int n, char from, char to, char via, List<String> moves) {
        if (n == 0) return;                                  // base case: nothing to move
        solve(n - 1, from, via, to, moves);
        moves.add("disk " + n + ": " + from + "->" + to);
        solve(n - 1, via, to, from, moves);
    }

    /**
     * Iterative: move number m (1-based) moves disk = trailing zeros of m + 1,
     * from peg (m & m-1) % 3 to peg ((m | m-1) + 1) % 3. Peg order (A,B,C) for odd n, (A,C,B) for even n.
     */
    public static List<String> iterative(int n) {
        List<String> moves = new ArrayList<>();
        char[] pegs = n % 2 == 1 ? new char[]{'A', 'B', 'C'} : new char[]{'A', 'C', 'B'};
        for (int m = 1; m < (1 << n); m++) {
            int disk = Integer.numberOfTrailingZeros(m) + 1;
            int from = (m & (m - 1)) % 3, to = ((m | (m - 1)) + 1) % 3;
            moves.add("disk " + disk + ": " + pegs[from] + "->" + pegs[to]);
        }
        return moves;
    }
}
