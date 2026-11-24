package com.playbook.graphs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GraphsBatch3Test {

    @Test void floodFill() {
        int[][] img = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}};
        int[][] want = {{2, 2, 2}, {2, 2, 0}, {2, 0, 1}};
        assertArrayEquals(want, FloodFill.dfs(img, 1, 1, 2));
        assertArrayEquals(want, FloodFill.bfs(img, 1, 1, 2));
        assertArrayEquals(want, FloodFill.unionFind(img, 1, 1, 2));
        assertArrayEquals(new int[][]{{1, 1, 1}, {1, 1, 0}, {1, 0, 1}}, img, "input must not change");
        assertArrayEquals(img, FloodFill.bfs(img, 0, 0, 1), "same colour → unchanged");
        Random rnd = new Random(91);
        for (int t = 0; t < 600; t++) {
            int rows = 1 + rnd.nextInt(6), cols = 1 + rnd.nextInt(6);
            int[][] a = new int[rows][cols];
            for (int[] row : a) for (int c = 0; c < cols; c++) row[c] = rnd.nextInt(3);
            int sr = rnd.nextInt(rows), sc = rnd.nextInt(cols), color = rnd.nextInt(3);
            int[][] w = FloodFill.unionFind(a, sr, sc, color);
            assertArrayEquals(w, FloodFill.dfs(a, sr, sc, color));
            assertArrayEquals(w, FloodFill.bfs(a, sr, sc, color));
        }
    }

    @Test void bipartite() {
        int[][] square = {{0, 1}, {1, 2}, {2, 3}, {3, 0}};
        int[][] triangle = {{0, 1}, {1, 2}, {2, 0}};
        assertTrue(Bipartite.bfs(4, square));
        assertFalse(Bipartite.bfs(3, triangle));
        assertFalse(Bipartite.dfs(5, new int[][]{{0, 1}, {2, 3}, {3, 4}, {4, 2}}), "odd cycle in a second component");
        assertTrue(Bipartite.unionFind(3, new int[0][]));
        assertFalse(Bipartite.bfs(2, new int[][]{{1, 1}}), "self-loop");
        int[] sides = Bipartite.twoColouring(4, square);
        for (int[] e : square) assertNotEquals(sides[e[0]], sides[e[1]]);
        assertNull(Bipartite.twoColouring(3, triangle));
        Random rnd = new Random(92);
        for (int t = 0; t < 1500; t++) {
            int n = 1 + rnd.nextInt(8);
            int[][] edges = randomEdges(rnd, n, rnd.nextInt(n + 3), true);
            boolean want = Bipartite.bruteForce(n, edges);
            assertEquals(want, Bipartite.bfs(n, edges));
            assertEquals(want, Bipartite.dfs(n, edges));
            assertEquals(want, Bipartite.unionFind(n, edges));
        }
    }

    @Test void stronglyConnectedComponents() {
        // 0→1→2→0 is one SCC, 2→3, 3→4→3 another, 5 alone
        int[][] edges = {{0, 1}, {1, 2}, {2, 0}, {2, 3}, {3, 4}, {4, 3}};
        int[] want = {0, 0, 0, 1, 1, 2};
        assertArrayEquals(want, StronglyConnectedComponents.canonical(StronglyConnectedComponents.kosaraju(6, edges)));
        assertArrayEquals(want, StronglyConnectedComponents.canonical(StronglyConnectedComponents.tarjan(6, edges)));
        assertArrayEquals(want, StronglyConnectedComponents.canonical(StronglyConnectedComponents.bruteForce(6, edges)));
        assertEquals(3, StronglyConnectedComponents.count(StronglyConnectedComponents.tarjan(6, edges)));
        Random rnd = new Random(93);
        for (int t = 0; t < 1500; t++) {
            int n = 1 + rnd.nextInt(9);
            int[][] e = randomEdges(rnd, n, rnd.nextInt(2 * n + 2), false);
            int[] ref = StronglyConnectedComponents.canonical(StronglyConnectedComponents.bruteForce(n, e));
            assertArrayEquals(ref, StronglyConnectedComponents.canonical(StronglyConnectedComponents.kosaraju(n, e)));
            assertArrayEquals(ref, StronglyConnectedComponents.canonical(StronglyConnectedComponents.tarjan(n, e)));
        }
        // a long chain: iterative Kosaraju handles depth that would stress recursion
        int n = 20_000;
        int[][] chain = new int[n][];
        for (int i = 0; i < n; i++) chain[i] = new int[]{i, (i + 1) % n};
        assertEquals(1, StronglyConnectedComponents.count(StronglyConnectedComponents.kosaraju(n, chain)));
    }

    private static int[][] randomEdges(Random rnd, int n, int m, boolean allowSelfLoop) {
        List<int[]> es = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int u = rnd.nextInt(n), v = rnd.nextInt(n);
            if (u == v && (!allowSelfLoop || rnd.nextInt(6) != 0)) continue;
            es.add(new int[]{u, v});
        }
        return es.toArray(new int[0][]);
    }
}
