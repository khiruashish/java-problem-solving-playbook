package com.playbook.recursion;

/**
 * Solve a 9×9 Sudoku in place ('.' = empty). Returns false if the puzzle has no solution.
 * Backtracking with O(1) validity checks (bitmasks per row/column/box) and the "most constrained cell first" heuristic.
 */
public final class SudokuSolver {
    private SudokuSolver() {}

    public static boolean solve(char[][] board) {
        int[] rows = new int[9], cols = new int[9], boxes = new int[9];   // bit d set = digit d+1 already used
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') continue;
                int bit = 1 << (board[r][c] - '1'), b = box(r, c);
                if ((rows[r] & bit) != 0 || (cols[c] & bit) != 0 || (boxes[b] & bit) != 0) return false;   // invalid givens
                rows[r] |= bit;
                cols[c] |= bit;
                boxes[b] |= bit;
            }
        return backtrack(board, rows, cols, boxes);
    }

    private static boolean backtrack(char[][] board, int[] rows, int[] cols, int[] boxes) {
        int bestR = -1, bestC = -1, bestOptions = 10, bestMask = 0;
        for (int r = 0; r < 9; r++)                          // pick the empty cell with the FEWEST candidates
            for (int c = 0; c < 9; c++) {
                if (board[r][c] != '.') continue;
                int free = ~(rows[r] | cols[c] | boxes[box(r, c)]) & 0x1FF;
                int options = Integer.bitCount(free);
                if (options < bestOptions) {
                    bestR = r;
                    bestC = c;
                    bestOptions = options;
                    bestMask = free;
                    if (options == 0) return false;          // dead end: prune immediately
                }
            }
        if (bestR == -1) return true;                        // no empty cell left: solved
        int b = box(bestR, bestC);
        for (int free = bestMask; free != 0; free &= free - 1) {
            int bit = free & -free;
            board[bestR][bestC] = (char) ('1' + Integer.numberOfTrailingZeros(bit));
            rows[bestR] |= bit;
            cols[bestC] |= bit;
            boxes[b] |= bit;
            if (backtrack(board, rows, cols, boxes)) return true;
            rows[bestR] &= ~bit;                             // undo
            cols[bestC] &= ~bit;
            boxes[b] &= ~bit;
        }
        board[bestR][bestC] = '.';
        return false;
    }

    private static int box(int r, int c) {
        return (r / 3) * 3 + c / 3;
    }

    /** Is a completely filled board a valid Sudoku? (used by tests) */
    public static boolean isValidSolution(char[][] board) {
        for (int i = 0; i < 9; i++) {
            int row = 0, col = 0, bx = 0;
            for (int j = 0; j < 9; j++) {
                row |= 1 << (board[i][j] - '1');
                col |= 1 << (board[j][i] - '1');
                bx |= 1 << (board[(i / 3) * 3 + j / 3][(i % 3) * 3 + j % 3] - '1');
            }
            if (row != 0x1FF || col != 0x1FF || bx != 0x1FF) return false;
        }
        return true;
    }
}
