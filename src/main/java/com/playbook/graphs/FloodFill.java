package com.playbook.graphs;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Flood fill (the paint-bucket tool): recolour the 4-connected region of the start pixel's colour.
 * Returns a NEW image; the input is not modified.
 */
public final class FloodFill {
    private FloodFill() {}

    private static final int[] DR = {1, -1, 0, 0}, DC = {0, 0, 1, -1};

    /** Recursive DFS. O(m·n) time, O(m·n) recursion depth in the worst case (a snake-shaped region). */
    public static int[][] dfs(int[][] image, int sr, int sc, int color) {
        int[][] img = copy(image);
        int old = img[sr][sc];
        if (old != color) fill(img, sr, sc, old, color);   // same colour: nothing to do (and avoids infinite recursion)
        return img;
    }

    private static void fill(int[][] img, int r, int c, int old, int color) {
        if (r < 0 || c < 0 || r >= img.length || c >= img[0].length || img[r][c] != old) return;
        img[r][c] = color;                                   // recolouring doubles as the "visited" mark
        for (int d = 0; d < 4; d++) fill(img, r + DR[d], c + DC[d], old, color);
    }

    /** Iterative BFS with a queue — no recursion depth limit. O(m·n) time and space. */
    public static int[][] bfs(int[][] image, int sr, int sc, int color) {
        int[][] img = copy(image);
        int old = img[sr][sc];
        if (old == color) return img;
        Deque<int[]> q = new ArrayDeque<>();
        img[sr][sc] = color;                                 // mark when ENQUEUED, not when dequeued
        q.offer(new int[]{sr, sc});
        while (!q.isEmpty()) {
            int[] p = q.poll();
            for (int d = 0; d < 4; d++) {
                int r = p[0] + DR[d], c = p[1] + DC[d];
                if (r >= 0 && c >= 0 && r < img.length && c < img[0].length && img[r][c] == old) {
                    img[r][c] = color;
                    q.offer(new int[]{r, c});
                }
            }
        }
        return img;
    }

    /** Union-Find "brute force": label all 4-connected components, then recolour the start's component. */
    public static int[][] unionFind(int[][] image, int sr, int sc, int color) {
        int rows = image.length, cols = image[0].length;
        int[] parent = new int[rows * cols];
        for (int i = 0; i < parent.length; i++) parent[i] = i;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                if (r + 1 < rows && image[r + 1][c] == image[r][c]) union(parent, r * cols + c, (r + 1) * cols + c);
                if (c + 1 < cols && image[r][c + 1] == image[r][c]) union(parent, r * cols + c, r * cols + c + 1);
            }
        int[][] img = copy(image);
        int root = find(parent, sr * cols + sc);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) if (find(parent, r * cols + c) == root) img[r][c] = color;
        return img;
    }

    private static int find(int[] p, int x) {
        while (p[x] != x) x = p[x] = p[p[x]];               // path halving
        return x;
    }

    private static void union(int[] p, int a, int b) {
        p[find(p, a)] = find(p, b);
    }

    private static int[][] copy(int[][] a) {
        int[][] b = new int[a.length][];
        for (int i = 0; i < a.length; i++) b[i] = a[i].clone();
        return b;
    }
}
