package com.playbook.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Single-source shortest paths with NON-NEGATIVE weights. edges[i] = {u, v, w}, directed.
 * Unreachable nodes get Long.MAX_VALUE.
 */
public final class Dijkstra {
    private Dijkstra() {}

    public static final long INF = Long.MAX_VALUE;

    /** Array version: pick the closest unfinished node by linear scan. O(V^2) — best for dense graphs. */
    public static long[] arrayScan(int n, int[][] edges, int src) {
        List<List<int[]>> adj = weighted(n, edges);
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;
        boolean[] done = new boolean[n];
        for (int iter = 0; iter < n; iter++) {
            int u = -1;
            for (int i = 0; i < n; i++) if (!done[i] && dist[i] != INF && (u == -1 || dist[i] < dist[u])) u = i;
            if (u == -1) break;                              // the rest is unreachable
            done[u] = true;
            for (int[] e : adj.get(u)) if (dist[u] + e[1] < dist[e[0]]) dist[e[0]] = dist[u] + e[1];
        }
        return dist;
    }

    /**
     * Heap version: min-heap of {dist, node}; pop the closest, relax its edges. Stale entries (a shorter distance was
     * found after pushing) are skipped instead of deleted ("lazy deletion"). O((V + E) log V).
     */
    public static long[] heap(int n, int[][] edges, int src) {
        List<List<int[]>> adj = weighted(n, edges);
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, src});
        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            int u = (int) top[1];
            if (top[0] > dist[u]) continue;                  // stale entry
            for (int[] e : adj.get(u)) {
                int v = e[0];
                long nd = dist[u] + e[1];
                if (nd < dist[v]) {                          // relaxation
                    dist[v] = nd;
                    pq.offer(new long[]{nd, v});
                }
            }
        }
        return dist;
    }

    static List<List<int[]>> weighted(int n, int[][] edges) {
        List<List<int[]>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            if (e[2] < 0) throw new IllegalArgumentException("Dijkstra needs non-negative weights");
            adj.get(e[0]).add(new int[]{e[1], e[2]});
        }
        return adj;
    }
}
