package com.playbook.recursion;

/** Does `word` exist in the grid as a path of adjacent (4-dir) cells, each used at most once? (LeetCode 79) */
public final class WordSearch {
    private WordSearch() {}

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /**
     * Backtracking from every cell: match word[k] here, mark the cell, try the 4 neighbours for word[k+1],
     * unmark on the way back. The grid is restored before returning. O(R·C·3^L) — after the first step there are
     * only 3 directions (we can't go back).
     */
    public static boolean exists(char[][] board, String word) {
        if (word.isEmpty()) return true;
        if ((long) board.length * board[0].length < word.length()) return false;   // cheap prune
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++)
                if (dfs(board, word, 0, r, c)) return true;
        return false;
    }

    private static boolean dfs(char[][] b, String w, int k, int r, int c) {
        if (r < 0 || c < 0 || r >= b.length || c >= b[0].length || b[r][c] != w.charAt(k)) return false;
        if (k == w.length() - 1) return true;
        char saved = b[r][c];
        b[r][c] = '#';                                       // visited on the current path
        boolean found = false;
        for (int[] d : DIRS) {
            if (dfs(b, w, k + 1, r + d[0], c + d[1])) {
                found = true;
                break;
            }
        }
        b[r][c] = saved;                                     // un-choose, even on success: leave the grid intact
        return found;
    }
}
