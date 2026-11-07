package com.playbook.graphs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Minimum spanning tree weight of a connected, undirected, weighted graph. edges[i] = {u, v, w}.
 * Returns -1 if the graph is not connected.
 */
public final class MinimumSpanningTree {
    private MinimumSpanningTree() {}

    /** Kruskal: sort edges by weight, add each edge that joins two different components (Union-Find). O(E log E). */
    public static long kruskal(int n, int[][] edges) {
        int[][] sorted = edges.clone();
        Arrays.sort(sorted, Comparator.comparingInt(e -> e[2]));
        UnionFind uf = new UnionFind(n);
        long total = 0;
        int used = 0;
        for (int[] e : sorted) {
            if (uf.union(e[0], e[1])) {                      // skip edges that would form a cycle
                total += e[2];
                if (++used == n - 1) break;
            }
        }
        return used == n - 1 || n <= 1 ? total : -1;
    }

    /** Prim: grow one tree from node 0, always adding the cheapest edge leaving it (min-heap). O(E log V). */
    public static long prim(int n, int[][] edges) {
        if (n <= 1) return 0;
        List<List<int[]>> adj = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new java.util.ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(new int[]{e[1], e[2]});
            adj.get(e[1]).add(new int[]{e[0], e[2]});
        }
        boolean[] inTree = new boolean[n];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x[1]));   // {node, weight}
        pq.offer(new int[]{0, 0});
        long total = 0;
        int added = 0;
        while (!pq.isEmpty() && added < n) {
            int[] top = pq.poll();
            if (inTree[top[0]]) continue;                    // stale: already connected more cheaply
            inTree[top[0]] = true;
            total += top[1];
            added++;
            for (int[] e : adj.get(top[0])) if (!inTree[e[0]]) pq.offer(new int[]{e[0], e[1]});
        }
        return added == n ? total : -1;
    }
}
