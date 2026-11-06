package com.playbook.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Place n queens on an n×n board so none attack each other. Return all boards ("Q" / "."). */
public final class NQueens {
    private NQueens() {}

    /**
     * Backtracking row by row. Column / diagonal occupancy in boolean arrays gives O(1) safety checks:
     * diagonal id = row - col + n - 1, anti-diagonal id = row + col.
     */
    public static List<List<String>> solve(int n) {
        List<List<String>> out = new ArrayList<>();
        place(0, n, new int[n], new boolean[n], new boolean[2 * n - 1], new boolean[2 * n - 1], out);
        return out;
    }

    private static void place(int row, int n, int[] queenCol, boolean[] cols, boolean[] diag, boolean[] anti,
                              List<List<String>> out) {
        if (row == n) {                                      // all rows filled → record board
            out.add(render(queenCol));
            return;
        }
        for (int c = 0; c < n; c++) {
            int d = row - c + n - 1, ad = row + c;
            if (cols[c] || diag[d] || anti[ad]) continue;    // attacked → prune
            queenCol[row] = c;
            cols[c] = diag[d] = anti[ad] = true;
            place(row + 1, n, queenCol, cols, diag, anti, out);
            cols[c] = diag[d] = anti[ad] = false;            // backtrack
        }
    }

    /** Count only, using bitmasks (fastest form). */
    public static int count(int n) {
        return count(n, 0, 0, 0, 0);
    }

    private static int count(int n, int row, int cols, int diag, int anti) {
        if (row == n) return 1;
        int total = 0;
        int free = ~(cols | diag | anti) & ((1 << n) - 1);  // bits where a queen may go
        while (free != 0) {
            int bit = free & -free;                          // lowest free position
            free -= bit;
            total += count(n, row + 1, cols | bit, (diag | bit) << 1, (anti | bit) >> 1);
        }
        return total;
    }

    private static List<String> render(int[] queenCol) {
        List<String> board = new ArrayList<>();
        for (int c : queenCol) {
            char[] row = new char[queenCol.length];
            Arrays.fill(row, '.');
            row[c] = 'Q';
            board.add(new String(row));
        }
        return board;
    }
}
