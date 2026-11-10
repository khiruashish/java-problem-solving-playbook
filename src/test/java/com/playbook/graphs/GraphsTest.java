package com.playbook.graphs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GraphsTest {
    private final Random rnd = new Random(1212);

    private int[][] randomEdges(int n, int m, boolean weighted, int minW, int maxW) {
        int[][] e = new int[m][];
        for (int i = 0; i < m; i++) {
            int u = rnd.nextInt(n), v = rnd.nextInt(n);
            e[i] = weighted ? new int[]{u, v, minW + rnd.nextInt(maxW - minW + 1)} : new int[]{u, v};
        }
        return e;
    }

    @Test void bfsDfs() {
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}, {3, 4}};
        List<List<Integer>> adj = Graph.undirected(6, edges);
        assertEquals(List.of(0, 1, 2, 3, 4), BfsDfs.bfs(adj, 0));
        assertEquals(List.of(0, 1, 3, 2, 4), BfsDfs.dfsRecursive(adj, 0));
        assertArrayEquals(new int[]{0, 1, 1, 2, 3, -1}, BfsDfs.shortestDistances(adj, 0));
        assertEquals(2, BfsDfs.components(adj));                 // node 5 is isolated
        for (int t = 0; t < 200; t++) {
            int n = 1 + rnd.nextInt(12);
            List<List<Integer>> g = Graph.undirected(n, randomEdges(n, rnd.nextInt(20), false, 0, 0));
            int s = rnd.nextInt(n);
            assertEquals(BfsDfs.dfsRecursive(g, s), BfsDfs.dfsIterative(g, s));
            assertEquals(new HashSet<>(BfsDfs.bfs(g, s)), new HashSet<>(BfsDfs.dfsRecursive(g, s)));
        }
    }

    @Test void islands() {
        char[][] grid = {"11000".toCharArray(), "11000".toCharArray(), "00100".toCharArray(), "00011".toCharArray()};
        assertEquals(3, NumberOfIslands.dfs(grid));
        assertEquals('1', grid[0][0], "input untouched");
        for (int t = 0; t < 200; t++) {
            int r = 1 + rnd.nextInt(8), c = 1 + rnd.nextInt(8);
            char[][] g = new char[r][c];
            for (char[] row : g) for (int j = 0; j < c; j++) row[j] = rnd.nextInt(10) < 5 ? '1' : '0';
            int expected = NumberOfIslands.dfs(g);
            assertEquals(expected, NumberOfIslands.bfs(g));
            assertEquals(expected, NumberOfIslands.unionFind(g));
        }
    }

    @Test void unionFind() {
        assertEquals(2, UnionFind.provinces(new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}));
        assertEquals(3, UnionFind.provinces(new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}));
        for (int t = 0; t < 200; t++) {
            int n = 1 + rnd.nextInt(15);
            int[][] e = randomEdges(n, rnd.nextInt(20), false, 0, 0);
            UnionFind uf = new UnionFind(n);
            for (int[] x : e) uf.union(x[0], x[1]);
            assertEquals(BfsDfs.components(Graph.undirected(n, e)), uf.sets());
        }
    }

    @Test void cycles() {
        assertTrue(CycleDetection.undirectedDfs(3, new int[][]{{0, 1}, {1, 2}, {2, 0}}));
        assertFalse(CycleDetection.undirectedDfs(4, new int[][]{{0, 1}, {1, 2}, {2, 3}}));
        assertTrue(CycleDetection.undirectedDfs(2, new int[][]{{0, 1}, {0, 1}}));     // parallel edge
        assertFalse(CycleDetection.directedDfs(3, new int[][]{{0, 1}, {0, 2}, {1, 2}}));   // diamond, NOT a cycle
        assertTrue(CycleDetection.directedDfs(3, new int[][]{{0, 1}, {1, 2}, {2, 0}}));
        assertTrue(CycleDetection.directedDfs(1, new int[][]{{0, 0}}));                  // self loop
        for (int t = 0; t < 300; t++) {
            int n = 1 + rnd.nextInt(8);
            int[][] e = randomEdges(n, rnd.nextInt(10), false, 0, 0);
            assertEquals(CycleDetection.undirectedUnionFind(n, e), CycleDetection.undirectedDfs(n, e), Arrays.deepToString(e));
            assertEquals(CycleDetection.directedKahn(n, e), CycleDetection.directedDfs(n, e), Arrays.deepToString(e));
        }
    }

    @Test void topoAndCourses() {
        int[][] dag = {{5, 2}, {5, 0}, {4, 0}, {4, 1}, {2, 3}, {3, 1}};
        assertTrue(TopologicalSort.isValidOrder(6, dag, TopologicalSort.kahn(6, dag)));
        assertTrue(TopologicalSort.isValidOrder(6, dag, TopologicalSort.dfs(6, dag)));
        assertTrue(CourseSchedule.canFinish(2, new int[][]{{1, 0}}));
        assertFalse(CourseSchedule.canFinish(2, new int[][]{{1, 0}, {0, 1}}));
        assertArrayEquals(new int[]{0, 1}, CourseSchedule.findOrder(2, new int[][]{{1, 0}}));
        for (int t = 0; t < 300; t++) {
            int n = 1 + rnd.nextInt(8);
            int[][] e = randomEdges(n, rnd.nextInt(10), false, 0, 0);
            boolean cyclic = CycleDetection.directedDfs(n, e);
            int[] k = TopologicalSort.kahn(n, e), d = TopologicalSort.dfs(n, e);
            assertEquals(!cyclic, TopologicalSort.isValidOrder(n, e, k));
            assertEquals(!cyclic, TopologicalSort.isValidOrder(n, e, d));
            if (cyclic) assertEquals(0, d.length);
        }
    }

    @Test void rottingOranges() {
        assertEquals(4, RottingOranges.multiSourceBfs(new int[][]{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}}));
        assertEquals(-1, RottingOranges.multiSourceBfs(new int[][]{{2, 1, 1}, {0, 1, 1}, {1, 0, 1}}));
        assertEquals(0, RottingOranges.multiSourceBfs(new int[][]{{0, 2}}));
        for (int t = 0; t < 300; t++) {
            int r = 1 + rnd.nextInt(6), c = 1 + rnd.nextInt(6);
            int[][] g = new int[r][c];
            for (int[] row : g) for (int j = 0; j < c; j++) row[j] = rnd.nextInt(3);
            assertEquals(RottingOranges.simulate(g), RottingOranges.multiSourceBfs(g));
        }
    }

    @Test void shortestPaths() {
        int[][] e = {{0, 1, 4}, {0, 2, 1}, {2, 1, 2}, {1, 3, 1}, {2, 3, 5}};
        assertArrayEquals(new long[]{0, 3, 1, 4}, Dijkstra.heap(4, e, 0));
        assertArrayEquals(new long[]{0, 3, 1, 4}, BellmanFordFloydWarshall.bellmanFord(4, e, 0));
        assertNull(BellmanFordFloydWarshall.bellmanFord(3, new int[][]{{0, 1, 1}, {1, 2, -2}, {2, 1, 1}}, 0));
        assertNull(BellmanFordFloydWarshall.floydWarshall(3, new int[][]{{0, 1, 1}, {1, 2, -2}, {2, 1, 1}}));
        assertThrows(IllegalArgumentException.class, () -> Dijkstra.heap(2, new int[][]{{0, 1, -1}}, 0));
        for (int t = 0; t < 300; t++) {
            int n = 1 + rnd.nextInt(9);
            int[][] g = randomEdges(n, rnd.nextInt(25), true, 0, 20);
            int src = rnd.nextInt(n);
            long[] dh = norm(Dijkstra.heap(n, g, src), Dijkstra.INF);
            assertArrayEquals(dh, norm(Dijkstra.arrayScan(n, g, src), Dijkstra.INF));
            assertArrayEquals(dh, norm(BellmanFordFloydWarshall.bellmanFord(n, g, src), BellmanFordFloydWarshall.INF));
            assertArrayEquals(dh, norm(BellmanFordFloydWarshall.floydWarshall(n, g)[src], BellmanFordFloydWarshall.INF));
        }
        for (int t = 0; t < 300; t++) {                          // negative weights: BF and FW must agree
            int n = 1 + rnd.nextInt(7);
            int[][] g = randomEdges(n, rnd.nextInt(15), true, -3, 10);
            long[][] fw = BellmanFordFloydWarshall.floydWarshall(n, g);
            for (int s = 0; s < n; s++) {
                long[] bf = BellmanFordFloydWarshall.bellmanFord(n, g, s);
                if (fw != null && bf != null) assertArrayEquals(fw[s], bf);
                if (fw == null) continue;                        // some cycle exists; BF from s may or may not reach it
                assertNotNull(bf, "no negative cycle anywhere → BF must succeed");
            }
        }
    }

    private static long[] norm(long[] d, long inf) {
        return Arrays.stream(d).map(x -> x >= inf ? -1 : x).toArray();
    }

    @Test void wordLadder() {
        List<String> dict = List.of("hot", "dot", "dog", "lot", "log", "cog");
        assertEquals(5, WordLadder.bfs("hit", "cog", dict));
        assertEquals(5, WordLadder.bidirectional("hit", "cog", dict));
        assertEquals(0, WordLadder.bfs("hit", "cog", List.of("hot", "dot", "dog", "lot", "log")));
        assertEquals(0, WordLadder.bidirectional("hit", "cog", List.of("hot", "dot", "dog", "lot", "log")));
        String alphabet = "abc";
        for (int t = 0; t < 200; t++) {
            Set<String> words = new HashSet<>();
            for (int i = 0; i < 12; i++) words.add(randomWord(alphabet));
            List<String> d = new ArrayList<>(words);
            String begin = randomWord(alphabet), end = d.get(rnd.nextInt(d.size()));
            assertEquals(WordLadder.bfs(begin, end, d), WordLadder.bidirectional(begin, end, d), begin + "->" + end + " " + d);
        }
    }

    private String randomWord(String alphabet) {
        char[] w = new char[3];
        for (int i = 0; i < 3; i++) w[i] = alphabet.charAt(rnd.nextInt(alphabet.length()));
        return new String(w);
    }

    @Test void mst() {
        int[][] e = {{0, 1, 10}, {0, 2, 6}, {0, 3, 5}, {1, 3, 15}, {2, 3, 4}};
        assertEquals(19, MinimumSpanningTree.kruskal(4, e));
        assertEquals(19, MinimumSpanningTree.prim(4, e));
        assertEquals(-1, MinimumSpanningTree.kruskal(3, new int[][]{{0, 1, 1}}));
        assertEquals(-1, MinimumSpanningTree.prim(3, new int[][]{{0, 1, 1}}));
        for (int t = 0; t < 300; t++) {
            int n = 1 + rnd.nextInt(10);
            int[][] g = randomEdges(n, rnd.nextInt(25), true, 1, 30);
            assertEquals(MinimumSpanningTree.kruskal(n, g), MinimumSpanningTree.prim(n, g), Arrays.deepToString(g));
        }
    }

    @Test void unionFindConnected() {
        UnionFind uf = new UnionFind(5);
        assertFalse(uf.connected(0, 1));
        assertTrue(uf.connected(3, 3));                         // every element is connected to itself
        assertTrue(uf.union(0, 1));
        assertTrue(uf.union(1, 2));
        assertTrue(uf.connected(0, 2));                         // transitively, through 1
        assertFalse(uf.connected(0, 4));
        assertFalse(uf.union(2, 0));                            // already together → false (cycle signal)
        assertEquals(3, uf.sets());
    }
}
