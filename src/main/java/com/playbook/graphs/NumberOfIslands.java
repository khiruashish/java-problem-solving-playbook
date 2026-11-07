package com.playbook.graphs;

import java.util.ArrayDeque;
import java.util.Deque;

/** Count islands of '1' (4-directionally connected) in a grid of '1'/'0'. The input grid is NOT modified. */
public final class NumberOfIslands {
    private NumberOfIslands() {}

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /** DFS flood fill: every unvisited land cell starts a new island; sink the whole island. O(R·C). */
    public static int dfs(char[][] grid) {
        char[][] g = copy(grid);
        int count = 0;
        for (int r = 0; r < g.length; r++)
            for (int c = 0; c < g[0].length; c++)
                if (g[r][c] == '1') {
                    count++;
                    sink(g, r, c);
                }
        return count;
    }

    private static void sink(char[][] g, int r, int c) {
        if (r < 0 || c < 0 || r >= g.length || c >= g[0].length || g[r][c] != '1') return;
        g[r][c] = '0';                                       // mark visited by sinking (on our copy)
        for (int[] d : DIRS) sink(g, r + d[0], c + d[1]);
    }

    /** BFS flood fill: same idea, explicit queue — no recursion depth risk on huge islands. */
    public static int bfs(char[][] grid) {
        char[][] g = copy(grid);
        int count = 0, rows = g.length, cols = rows == 0 ? 0 : g[0].length;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                if (g[r][c] != '1') continue;
                count++;
                g[r][c] = '0';
                Deque<int[]> q = new ArrayDeque<>();
                q.offer(new int[]{r, c});
                while (!q.isEmpty()) {
                    int[] cell = q.poll();
                    for (int[] d : DIRS) {
                        int nr = cell[0] + d[0], nc = cell[1] + d[1];
                        if (nr >= 0 && nc >= 0 && nr < rows && nc < cols && g[nr][nc] == '1') {
                            g[nr][nc] = '0';                 // mark when enqueuing
                            q.offer(new int[]{nr, nc});
                        }
                    }
                }
            }
        return count;
    }

    /** Union-Find: every land cell is a set; union with land neighbours; answer = number of land sets. */
    public static int unionFind(char[][] grid) {
        int rows = grid.length, cols = rows == 0 ? 0 : grid[0].length;
        UnionFind uf = new UnionFind(rows * cols);
        int land = 0;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != '1') continue;
                land++;
                if (r + 1 < rows && grid[r + 1][c] == '1' && uf.union(r * cols + c, (r + 1) * cols + c)) land--;
                if (c + 1 < cols && grid[r][c + 1] == '1' && uf.union(r * cols + c, r * cols + c + 1)) land--;
            }
        return land;                                         // each successful union merged two islands into one
    }

    private static char[][] copy(char[][] g) {
        char[][] c = new char[g.length][];
        for (int i = 0; i < g.length; i++) c[i] = g[i].clone();
        return c;
    }
}
