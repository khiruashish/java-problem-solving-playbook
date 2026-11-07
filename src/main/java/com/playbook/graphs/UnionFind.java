package com.playbook.graphs;

/**
 * Disjoint Set Union with path compression + union by rank: find/union in amortised O(α(n)) — effectively constant.
 */
public final class UnionFind {
    private final int[] parent, rank;
    private int sets;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        sets = n;
        for (int i = 0; i < n; i++) parent[i] = i;          // every element starts as its own root
    }

    /** Root of x's set; path compression points every node on the way directly at the root. */
    public int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];                   // path halving: cheap, iterative form of compression
            x = parent[x];
        }
        return x;
    }

    /** Merge the sets of a and b. Returns false if they were already together (useful for cycle detection). */
    public boolean union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return false;
        if (rank[ra] < rank[rb]) {                           // attach the shorter tree under the taller one
            int t = ra;
            ra = rb;
            rb = t;
        }
        parent[rb] = ra;
        if (rank[ra] == rank[rb]) rank[ra]++;
        sets--;
        return true;
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    public int sets() {
        return sets;
    }

    /** Number of provinces (LeetCode 547) from an adjacency MATRIX, using DSU. */
    public static int provinces(int[][] isConnected) {
        int n = isConnected.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) for (int j = i + 1; j < n; j++) if (isConnected[i][j] == 1) uf.union(i, j);
        return uf.sets();
    }
}
