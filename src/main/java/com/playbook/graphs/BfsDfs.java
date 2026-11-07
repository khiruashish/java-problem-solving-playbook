package com.playbook.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/** Breadth-first and depth-first traversal of an adjacency list, plus unweighted shortest distances. */
public final class BfsDfs {
    private BfsDfs() {}

    /** BFS: visit by distance from start, using a queue. Mark visited WHEN ENQUEUING so nothing is queued twice. O(V+E). */
    public static List<Integer> bfs(List<List<Integer>> adj, int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] seen = new boolean[adj.size()];
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(start);
        seen[start] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : adj.get(u)) {
                if (!seen[v]) {
                    seen[v] = true;
                    q.offer(v);
                }
            }
        }
        return order;
    }

    /** DFS, recursive: go as deep as possible before backtracking. O(V+E), O(V) stack. */
    public static List<Integer> dfsRecursive(List<List<Integer>> adj, int start) {
        List<Integer> order = new ArrayList<>();
        dfs(adj, start, new boolean[adj.size()], order);
        return order;
    }

    private static void dfs(List<List<Integer>> adj, int u, boolean[] seen, List<Integer> order) {
        seen[u] = true;
        order.add(u);
        for (int v : adj.get(u)) if (!seen[v]) dfs(adj, v, seen, order);
    }

    /**
     * DFS, iterative with an explicit stack. Push neighbours in REVERSE so the first neighbour is explored first,
     * and mark visited when POPPING — this reproduces the recursive order exactly.
     */
    public static List<Integer> dfsIterative(List<List<Integer>> adj, int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] seen = new boolean[adj.size()];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (seen[u]) continue;                           // a node can be pushed several times; process once
            seen[u] = true;
            order.add(u);
            List<Integer> nbrs = adj.get(u);
            for (int i = nbrs.size() - 1; i >= 0; i--) if (!seen[nbrs.get(i)]) stack.push(nbrs.get(i));
        }
        return order;
    }

    /** Fewest edges from start to every node (-1 if unreachable): BFS reaches each node first by a shortest path. */
    public static int[] shortestDistances(List<List<Integer>> adj, int start) {
        int[] dist = new int[adj.size()];
        Arrays.fill(dist, -1);
        dist[start] = 0;
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(start);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj.get(u)) {
                if (dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    q.offer(v);
                }
            }
        }
        return dist;
    }

    /** Number of connected components (undirected): start a traversal from every unvisited node. */
    public static int components(List<List<Integer>> adj) {
        boolean[] seen = new boolean[adj.size()];
        int count = 0;
        for (int s = 0; s < adj.size(); s++) {
            if (!seen[s]) {
                count++;
                dfs(adj, s, seen, new ArrayList<>());
            }
        }
        return count;
    }
}
