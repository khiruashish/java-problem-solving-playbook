package com.playbook.recursion;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Rat in a maze: n×n grid, 1 = open, 0 = wall. Start (0,0), target (n-1,n-1). Moves D, L, R, U, no cell visited twice
 * in one path. Return every path as a move string, in lexicographic order.
 */
public final class RatInMaze {
    private RatInMaze() {}

    // Alphabetical move order (D < L < R < U) makes the DFS emit paths already sorted.
    private static final char[] MOVE = {'D', 'L', 'R', 'U'};
    private static final int[] DR = {1, 0, 0, -1};
    private static final int[] DC = {0, -1, 1, 0};

    /** Backtracking: mark the cell, try 4 moves, unmark. Exponential in the worst case (open grid). */
    public static List<String> allPaths(int[][] maze) {
        List<String> out = new ArrayList<>();
        int n = maze.length;
        if (n == 0 || maze[0][0] == 0 || maze[n - 1][n - 1] == 0) return out;
        boolean[][] onPath = new boolean[n][n];
        dfs(maze, 0, 0, onPath, new StringBuilder(), out);
        return out;
    }

    private static void dfs(int[][] m, int r, int c, boolean[][] onPath, StringBuilder path, List<String> out) {
        int n = m.length;
        if (r == n - 1 && c == n - 1) {                  // base case: reached the target
            out.add(path.toString());
            return;
        }
        onPath[r][c] = true;                             // choose this cell
        for (int d = 0; d < 4; d++) {
            int nr = r + DR[d], nc = c + DC[d];
            if (nr < 0 || nc < 0 || nr >= n || nc >= n || m[nr][nc] == 0 || onPath[nr][nc]) continue;
            path.append(MOVE[d]);
            dfs(m, nr, nc, onPath, path, out);
            path.deleteCharAt(path.length() - 1);
        }
        onPath[r][c] = false;                            // un-choose: other paths may use this cell
    }

    /** Count simple paths without building strings (same search, cheaper output). */
    public static long countPaths(int[][] maze) {
        int n = maze.length;
        if (n == 0 || maze[0][0] == 0 || maze[n - 1][n - 1] == 0) return 0;
        return count(maze, 0, 0, new boolean[n][n]);
    }

    private static long count(int[][] m, int r, int c, boolean[][] onPath) {
        int n = m.length;
        if (r == n - 1 && c == n - 1) return 1;
        onPath[r][c] = true;
        long total = 0;
        for (int d = 0; d < 4; d++) {
            int nr = r + DR[d], nc = c + DC[d];
            if (nr >= 0 && nc >= 0 && nr < n && nc < n && m[nr][nc] == 1 && !onPath[nr][nc]) {
                total += count(m, nr, nc, onPath);
            }
        }
        onPath[r][c] = false;
        return total;
    }

    /**
     * Only "is there ANY path?" — no backtracking needed: a cell that failed once fails forever, so mark it visited
     * permanently. Iterative DFS with an explicit stack: O(n²) time and space.
     */
    public static boolean pathExists(int[][] maze) {
        int n = maze.length;
        if (n == 0 || maze[0][0] == 0 || maze[n - 1][n - 1] == 0) return false;
        boolean[][] seen = new boolean[n][n];
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{0, 0});
        seen[0][0] = true;
        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            if (cell[0] == n - 1 && cell[1] == n - 1) return true;
            for (int d = 0; d < 4; d++) {
                int nr = cell[0] + DR[d], nc = cell[1] + DC[d];
                if (nr >= 0 && nc >= 0 && nr < n && nc < n && maze[nr][nc] == 1 && !seen[nr][nc]) {
                    seen[nr][nc] = true;
                    stack.push(new int[]{nr, nc});
                }
            }
        }
        return false;
    }
}
