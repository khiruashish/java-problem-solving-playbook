package com.playbook.graphs;

import java.util.Arrays;

/**
 * Shortest paths that allow NEGATIVE weights. edges[i] = {u, v, w}, directed.
 * Bellman-Ford: single source, detects negative cycles. Floyd-Warshall: all pairs.
 */
public final class BellmanFordFloydWarshall {
    private BellmanFordFloydWarshall() {}

    public static final long INF = Long.MAX_VALUE / 4;       // headroom so INF + w never overflows

    /**
     * Bellman-Ford: relax EVERY edge V-1 times (a shortest path has at most V-1 edges). If a V-th round still improves
     * something, a negative cycle is reachable → return null. O(V·E).
     */
    public static long[] bellmanFord(int n, int[][] edges, int src) {
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;
        for (int round = 0; round < n - 1; round++) {
            boolean changed = false;
            for (int[] e : edges) {
                if (dist[e[0]] != INF && dist[e[0]] + e[2] < dist[e[1]]) {
                    dist[e[1]] = dist[e[0]] + e[2];
                    changed = true;
                }
            }
            if (!changed) break;                             // early exit: already optimal
        }
        for (int[] e : edges) if (dist[e[0]] != INF && dist[e[0]] + e[2] < dist[e[1]]) return null;
        return dist;
    }

    /**
     * Floyd-Warshall: dist[i][j] using intermediate nodes {0..k}. For each k, try going i → k → j.
     * O(V^3) time, O(V^2) space. A negative dist[i][i] reveals a negative cycle → return null.
     */
    public static long[][] floydWarshall(int n, int[][] edges) {
        long[][] d = new long[n][n];
        for (long[] row : d) Arrays.fill(row, INF);
        for (int i = 0; i < n; i++) d[i][i] = 0;
        for (int[] e : edges) d[e[0]][e[1]] = Math.min(d[e[0]][e[1]], e[2]);   // keep the cheapest parallel edge
        for (int k = 0; k < n; k++)                          // k MUST be the outer loop
            for (int i = 0; i < n; i++) {
                if (d[i][k] == INF) continue;
                for (int j = 0; j < n; j++)
                    if (d[k][j] != INF && d[i][k] + d[k][j] < d[i][j]) d[i][j] = d[i][k] + d[k][j];
            }
        for (int i = 0; i < n; i++) if (d[i][i] < 0) return null;
        return d;
    }
}
