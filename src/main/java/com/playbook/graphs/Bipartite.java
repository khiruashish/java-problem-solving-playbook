package com.playbook.graphs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Is an undirected graph bipartite (2-colourable: every edge joins the two colour classes)?
 * Equivalently: it has no odd-length cycle. The graph may be disconnected.
 */
public final class Bipartite {
    private Bipartite() {}

    /** BFS 2-colouring per component: neighbours get the opposite colour; a same-colour edge means "no". O(V + E). */
    public static boolean bfs(int n, int[][] edges) {
        List<List<Integer>> adj = Graph.undirected(n, edges);
        int[] color = new int[n];                            // 0 = uncoloured, 1 / -1 = the two sides
        Deque<Integer> q = new ArrayDeque<>();
        for (int s = 0; s < n; s++) {
            if (color[s] != 0) continue;                     // already coloured by an earlier component
            color[s] = 1;
            q.offer(s);
            while (!q.isEmpty()) {
                int u = q.poll();
                for (int v : adj.get(u)) {
                    if (color[v] == 0) {
                        color[v] = -color[u];
                        q.offer(v);
                    } else if (color[v] == color[u]) {
                        return false;                        // edge inside one side: an odd cycle exists
                    }
                }
            }
        }
        return true;
    }

    /** DFS 2-colouring (iterative stack, so deep graphs can't overflow the call stack). O(V + E). */
    public static boolean dfs(int n, int[][] edges) {
        List<List<Integer>> adj = Graph.undirected(n, edges);
        int[] color = new int[n];
        Deque<Integer> st = new ArrayDeque<>();
        for (int s = 0; s < n; s++) {
            if (color[s] != 0) continue;
            color[s] = 1;
            st.push(s);
            while (!st.isEmpty()) {
                int u = st.pop();
                for (int v : adj.get(u)) {
                    if (color[v] == 0) {
                        color[v] = -color[u];
                        st.push(v);
                    } else if (color[v] == color[u]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Union-Find: all neighbours of u must end up on the same side, opposite to u. Union each neighbour with u's
     * first neighbour; if u ever shares a set with a neighbour, it's not bipartite. O(E α(V)).
     */
    public static boolean unionFind(int n, int[][] edges) {
        List<List<Integer>> adj = Graph.undirected(n, edges);
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        for (int u = 0; u < n; u++) {
            List<Integer> nb = adj.get(u);
            for (int v : nb) {
                if (find(parent, u) == find(parent, v)) return false;   // u and its neighbour forced together
                parent[find(parent, v)] = find(parent, nb.get(0));       // all neighbours share one side
            }
        }
        return true;
    }

    /** Brute force for tests (n ≤ 15): try all 2^n colourings. */
    public static boolean bruteForce(int n, int[][] edges) {
        for (int mask = 0; mask < (1 << n); mask++) {
            boolean ok = true;
            for (int[] e : edges) {
                if (((mask >> e[0]) & 1) == ((mask >> e[1]) & 1)) { ok = false; break; }
            }
            if (ok) return true;
        }
        return false;                                    // no colouring works (n = 0 returns true above: mask 0)
    }

    private static int find(int[] p, int x) {
        while (p[x] != x) x = p[x] = p[p[x]];
        return x;
    }

    /** Returns one valid side assignment (1 / -1 per node), or null if none — handy for "split into two teams". */
    public static int[] twoColouring(int n, int[][] edges) {
        List<List<Integer>> adj = Graph.undirected(n, edges);
        int[] color = new int[n];
        Deque<Integer> q = new ArrayDeque<>();
        for (int s = 0; s < n; s++) {
            if (color[s] != 0) continue;
            color[s] = 1;
            q.offer(s);
            while (!q.isEmpty()) {
                int u = q.poll();
                for (int v : adj.get(u)) {
                    if (color[v] == 0) { color[v] = -color[u]; q.offer(v); }
                    else if (color[v] == color[u]) return null;
                }
            }
        }
        return color;
    }
}
