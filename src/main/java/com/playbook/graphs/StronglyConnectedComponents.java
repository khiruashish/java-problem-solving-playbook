package com.playbook.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Strongly connected components of a directed graph: maximal sets where every node reaches every other.
 * All methods return comp[v] = a component id; two nodes share an id iff they are in the same SCC.
 * (Ids may differ between methods; compare with {@link #canonical}.)
 */
public final class StronglyConnectedComponents {
    private StronglyConnectedComponents() {}

    /** Brute force: u, v share an SCC iff u reaches v AND v reaches u. One BFS per node: O(V·(V+E)). */
    public static int[] bruteForce(int n, int[][] edges) {
        List<List<Integer>> adj = Graph.directed(n, edges);
        boolean[][] reach = new boolean[n][];
        for (int s = 0; s < n; s++) reach[s] = reachableFrom(s, adj);
        int[] comp = new int[n];
        Arrays.fill(comp, -1);
        int id = 0;
        for (int u = 0; u < n; u++) {
            if (comp[u] != -1) continue;
            for (int v = 0; v < n; v++) if (reach[u][v] && reach[v][u]) comp[v] = id;
            id++;
        }
        return comp;
    }

    /**
     * Kosaraju: (1) DFS on G, record nodes by FINISH time; (2) DFS on the REVERSED graph in decreasing finish time —
     * each tree found is one SCC. The node finishing last lies in a "source" SCC of the condensation; on the reversed
     * graph that SCC becomes a sink, so the second DFS can't leak out of it. O(V + E). Iterative DFS throughout.
     */
    public static int[] kosaraju(int n, int[][] edges) {
        List<List<Integer>> adj = Graph.directed(n, edges);
        List<List<Integer>> radj = new ArrayList<>();
        for (int i = 0; i < n; i++) radj.add(new ArrayList<>());
        for (int[] e : edges) radj.get(e[1]).add(e[0]);

        // pass 1: finish order on G
        boolean[] seen = new boolean[n];
        int[] order = new int[n];
        int k = 0;
        int[] it = new int[n];                                // next neighbour index per node (iterative DFS)
        Deque<Integer> st = new ArrayDeque<>();
        for (int s = 0; s < n; s++) {
            if (seen[s]) continue;
            seen[s] = true;
            st.push(s);
            while (!st.isEmpty()) {
                int u = st.peek();
                if (it[u] < adj.get(u).size()) {
                    int v = adj.get(u).get(it[u]++);
                    if (!seen[v]) { seen[v] = true; st.push(v); }
                } else {
                    order[k++] = st.pop();                    // all descendants done: u finishes
                }
            }
        }

        // pass 2: reversed graph, decreasing finish time
        int[] comp = new int[n];
        Arrays.fill(comp, -1);
        int id = 0;
        for (int i = n - 1; i >= 0; i--) {
            int s = order[i];
            if (comp[s] != -1) continue;
            comp[s] = id;
            st.push(s);
            while (!st.isEmpty()) {
                int u = st.pop();
                for (int v : radj.get(u)) if (comp[v] == -1) { comp[v] = id; st.push(v); }
            }
            id++;
        }
        return comp;
    }

    /**
     * Tarjan: one DFS. disc[u] = discovery time, low[u] = smallest disc reachable from u's subtree using at most one
     * back edge to a node still ON THE STACK. When low[u] == disc[u], u is the root of an SCC: pop it off. O(V + E).
     * (Recursive for readability — fine for interview-sized graphs.)
     */
    public static int[] tarjan(int n, int[][] edges) {
        List<List<Integer>> adj = Graph.directed(n, edges);
        int[] disc = new int[n], low = new int[n], comp = new int[n];
        Arrays.fill(disc, -1);
        Arrays.fill(comp, -1);
        boolean[] onStack = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();
        int[] time = {0}, id = {0};
        for (int s = 0; s < n; s++) if (disc[s] == -1) strongConnect(s, adj, disc, low, onStack, stack, comp, time, id);
        return comp;
    }

    private static void strongConnect(int u, List<List<Integer>> adj, int[] disc, int[] low, boolean[] onStack,
                                      Deque<Integer> stack, int[] comp, int[] time, int[] id) {
        disc[u] = low[u] = time[0]++;
        stack.push(u);
        onStack[u] = true;
        for (int v : adj.get(u)) {
            if (disc[v] == -1) {                              // tree edge
                strongConnect(v, adj, disc, low, onStack, stack, comp, time, id);
                low[u] = Math.min(low[u], low[v]);
            } else if (onStack[v]) {                          // back edge into the current SCC candidate
                low[u] = Math.min(low[u], disc[v]);
            }                                                 // cross edge to a finished SCC: ignore
        }
        if (low[u] == disc[u]) {                              // u is the root: everything above it is its SCC
            int v;
            do {
                v = stack.pop();
                onStack[v] = false;
                comp[v] = id[0];
            } while (v != u);
            id[0]++;
        }
    }

    /** Number of SCCs = number of distinct ids. */
    public static int count(int[] comp) {
        return (int) Arrays.stream(comp).distinct().count();
    }

    /** Relabel ids by first appearance so different algorithms' outputs can be compared directly. */
    public static int[] canonical(int[] comp) {
        int[] map = new int[comp.length + 1];
        Arrays.fill(map, -1);
        int[] out = new int[comp.length];
        int next = 0;
        for (int i = 0; i < comp.length; i++) {
            if (map[comp[i]] == -1) map[comp[i]] = next++;
            out[i] = map[comp[i]];
        }
        return out;
    }

    private static boolean[] reachableFrom(int s, List<List<Integer>> adj) {
        boolean[] seen = new boolean[adj.size()];
        Deque<Integer> q = new ArrayDeque<>();
        seen[s] = true;
        q.offer(s);
        while (!q.isEmpty()) for (int v : adj.get(q.poll())) if (!seen[v]) { seen[v] = true; q.offer(v); }
        return seen;
    }
}
