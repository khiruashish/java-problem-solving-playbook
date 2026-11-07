package com.playbook.graphs;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Grid: 0 empty, 1 fresh, 2 rotten. Each minute rotten oranges rot their 4 neighbours.
 * Minutes until no fresh orange remains, or -1 if some never rot (LeetCode 994).
 */
public final class RottingOranges {
    private RottingOranges() {}

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /** Brute force: simulate minute by minute, scanning the whole grid each time. O((R·C)^2). */
    public static int simulate(int[][] grid) {
        int[][] g = copy(grid);
        int minutes = 0;
        while (true) {
            int[][] next = copy(g);
            boolean changed = false;
            for (int r = 0; r < g.length; r++)
                for (int c = 0; c < g[0].length; c++)
                    if (g[r][c] == 2)
                        for (int[] d : DIRS) {
                            int nr = r + d[0], nc = c + d[1];
                            if (nr >= 0 && nc >= 0 && nr < g.length && nc < g[0].length && g[nr][nc] == 1) {
                                next[nr][nc] = 2;
                                changed = true;
                            }
                        }
            if (!changed) break;
            g = next;
            minutes++;
        }
        for (int[] row : g) for (int v : row) if (v == 1) return -1;
        return minutes;
    }

    /**
     * Multi-source BFS: put ALL rotten oranges in the queue at time 0; each BFS level is one minute.
     * Every cell is processed once → O(R·C).
     */
    public static int multiSourceBfs(int[][] grid) {
        int[][] g = copy(grid);
        int rows = g.length, cols = rows == 0 ? 0 : g[0].length, fresh = 0;
        Deque<int[]> q = new ArrayDeque<>();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                if (g[r][c] == 2) q.offer(new int[]{r, c});
                else if (g[r][c] == 1) fresh++;
            }
        int minutes = 0;
        while (!q.isEmpty() && fresh > 0) {
            for (int size = q.size(); size > 0; size--) {    // one level = one minute
                int[] cell = q.poll();
                for (int[] d : DIRS) {
                    int nr = cell[0] + d[0], nc = cell[1] + d[1];
                    if (nr >= 0 && nc >= 0 && nr < rows && nc < cols && g[nr][nc] == 1) {
                        g[nr][nc] = 2;
                        fresh--;
                        q.offer(new int[]{nr, nc});
                    }
                }
            }
            minutes++;
        }
        return fresh == 0 ? minutes : -1;
    }

    private static int[][] copy(int[][] g) {
        int[][] c = new int[g.length][];
        for (int i = 0; i < g.length; i++) c[i] = g[i].clone();
        return c;
    }
}
