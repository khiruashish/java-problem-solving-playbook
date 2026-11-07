package com.playbook.graphs;

import java.util.ArrayList;
import java.util.List;

/** Helpers to turn an edge list into an adjacency list (the representation most problems want). */
public final class Graph {
    private Graph() {}

    /** Undirected adjacency list for nodes 0..n-1. O(V + E) space. */
    public static List<List<Integer>> undirected(int n, int[][] edges) {
        List<List<Integer>> adj = empty(n);
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }
        return adj;
    }

    /** Directed adjacency list: edge {u, v} means u → v. */
    public static List<List<Integer>> directed(int n, int[][] edges) {
        List<List<Integer>> adj = empty(n);
        for (int[] e : edges) adj.get(e[0]).add(e[1]);
        return adj;
    }

    private static List<List<Integer>> empty(int n) {
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        return adj;
    }
}
