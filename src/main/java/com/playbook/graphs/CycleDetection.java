package com.playbook.graphs;

import java.util.List;

/** Does a graph contain a cycle? Undirected and directed graphs need DIFFERENT logic. */
public final class CycleDetection {
    private CycleDetection() {}

    /** Undirected, DFS: a visited neighbour that is NOT the node we came from closes a cycle. O(V+E). */
    public static boolean undirectedDfs(int n, int[][] edges) {
        List<List<Integer>> adj = Graph.undirected(n, edges);
        boolean[] seen = new boolean[n];
        for (int s = 0; s < n; s++) if (!seen[s] && dfsUndirected(adj, s, -1, seen)) return true;
        return false;
    }

    private static boolean dfsUndirected(List<List<Integer>> adj, int u, int parent, boolean[] seen) {
        seen[u] = true;
        boolean skippedParent = false;
        for (int v : adj.get(u)) {
            if (v == parent && !skippedParent) {             // skip the tree edge back ONCE (a parallel edge is a cycle)
                skippedParent = true;
                continue;
            }
            if (seen[v] || dfsUndirected(adj, v, u, seen)) return true;
        }
        return false;
    }

    /** Undirected, Union-Find: an edge whose endpoints are already connected closes a cycle. O(E·α). */
    public static boolean undirectedUnionFind(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        for (int[] e : edges) if (!uf.union(e[0], e[1])) return true;
        return false;
    }

    private static final int WHITE = 0, GREY = 1, BLACK = 2;

    /**
     * Directed, DFS with three colours: WHITE unvisited, GREY on the current recursion path, BLACK finished.
     * An edge to a GREY node is a back edge → cycle. (Plain "visited" is wrong for directed graphs: reaching a
     * finished node through another path is not a cycle.) O(V+E).
     */
    public static boolean directedDfs(int n, int[][] edges) {
        List<List<Integer>> adj = Graph.directed(n, edges);
        int[] colour = new int[n];
        for (int s = 0; s < n; s++) if (colour[s] == WHITE && dfsDirected(adj, s, colour)) return true;
        return false;
    }

    private static boolean dfsDirected(List<List<Integer>> adj, int u, int[] colour) {
        colour[u] = GREY;
        for (int v : adj.get(u)) {
            if (colour[v] == GREY) return true;
            if (colour[v] == WHITE && dfsDirected(adj, v, colour)) return true;
        }
        colour[u] = BLACK;
        return false;
    }

    /** Directed, Kahn's algorithm: a cycle exists iff topological sort cannot output every node. */
    public static boolean directedKahn(int n, int[][] edges) {
        return TopologicalSort.kahn(n, edges).length < n;
    }
}
