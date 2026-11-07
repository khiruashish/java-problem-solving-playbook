package com.playbook.graphs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Order the nodes of a DAG so every edge u → v has u before v. If there is a cycle, no order exists:
 * both methods then return an array SHORTER than n (Kahn) or an empty array (DFS).
 */
public final class TopologicalSort {
    private TopologicalSort() {}

    /** Kahn (BFS): repeatedly output a node with in-degree 0 and remove its outgoing edges. O(V+E). */
    public static int[] kahn(int n, int[][] edges) {
        List<List<Integer>> adj = Graph.directed(n, edges);
        int[] indegree = new int[n];
        for (int[] e : edges) indegree[e[1]]++;
        Deque<Integer> ready = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indegree[i] == 0) ready.offer(i);
        int[] order = new int[n];
        int k = 0;
        while (!ready.isEmpty()) {
            int u = ready.poll();
            order[k++] = u;
            for (int v : adj.get(u)) if (--indegree[v] == 0) ready.offer(v);   // last prerequisite done
        }
        return java.util.Arrays.copyOf(order, k);            // k < n means a cycle blocked some nodes
    }

    /** DFS: a node is finished only after all its descendants; reverse finishing order is a topological order. */
    public static int[] dfs(int n, int[][] edges) {
        List<List<Integer>> adj = Graph.directed(n, edges);
        int[] state = new int[n];                            // 0 new, 1 in progress, 2 done
        Deque<Integer> finished = new ArrayDeque<>();
        for (int s = 0; s < n; s++) if (state[s] == 0 && !visit(adj, s, state, finished)) return new int[0];
        return finished.stream().mapToInt(Integer::intValue).toArray();
    }

    private static boolean visit(List<List<Integer>> adj, int u, int[] state, Deque<Integer> finished) {
        state[u] = 1;
        for (int v : adj.get(u)) {
            if (state[v] == 1) return false;                 // back edge: cycle
            if (state[v] == 0 && !visit(adj, v, state, finished)) return false;
        }
        state[u] = 2;
        finished.push(u);                                    // push to FRONT → reversed finishing order
        return true;
    }

    /** Validate an order: a permutation of 0..n-1 that respects every edge (used by tests). */
    public static boolean isValidOrder(int n, int[][] edges, int[] order) {
        if (order.length != n) return false;
        int[] pos = new int[n];
        java.util.Arrays.fill(pos, -1);
        for (int i = 0; i < n; i++) {
            if (order[i] < 0 || order[i] >= n || pos[order[i]] != -1) return false;
            pos[order[i]] = i;
        }
        for (int[] e : edges) if (pos[e[0]] >= pos[e[1]]) return false;
        return true;
    }
}
