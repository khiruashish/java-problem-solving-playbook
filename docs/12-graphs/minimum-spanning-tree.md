# Minimum spanning tree (Kruskal & Prim)

<span class="badge expert">Expert</span> <span class="badge tag">Greedy</span> <span class="badge tag">Union-Find</span> <span class="badge tag">Min-heap</span>

## Problem
In a connected, undirected, weighted graph, choose V − 1 edges that connect every vertex with the **minimum total weight**.
Return −1 if the graph is disconnected.
```
   0 ──10── 1
   | \      |
   6   5    15        MST = (2–3: 4) + (0–3: 5) + (0–1: 10) = 19
   |     \  |
   2 ──4─── 3
```

## Thinking process
<div class="steps" markdown>

1. **Cut property** (why the greedy choice works): for any split of the vertices into two groups, the **cheapest edge crossing
   the split** belongs to some MST.
2. **Kruskal (edge-centric)**: sort all edges by weight. Take each edge **unless it would make a cycle**, which
   [Union-Find](union-find.md) checks in about O(1). Stop at V − 1 edges.
3. **Prim (vertex-centric)**: grow a single tree from any vertex. A min-heap holds the edges leaving the tree, and you always
   add the cheapest one that reaches a **new** vertex. Skip stale heap entries whose target is already in the tree.
4. **Disconnected graph**: fewer than V − 1 edges get used (Kruskal), or fewer than V vertices are reached (Prim).

</div>

## Kruskal trace
| Edge (w) | Union? | Total |
|----------|--------|-------|
| 2–3 (4) | ✅ | 4 |
| 0–3 (5) | ✅ | 9 |
| 0–2 (6) | ❌ 0 and 2 are already connected | 9 |
| 0–1 (10) | ✅ | **19** (3 edges, done) |

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/MinimumSpanningTree.java"
```

## Complexity
| Algorithm | Time | Best for |
|-----------|------|----------|
| Kruskal | O(E log E) | sparse graphs, edges given as a list |
| Prim (binary heap) | O(E log V) | dense graphs, adjacency lists |

## Testing note
On 300 random multigraphs (parallel edges, self-loops, sometimes disconnected), Kruskal and Prim must return the same total.

## Real-world uses
Network cabling, clustering (stop Kruskal early to get k clusters), approximating the travelling salesman problem.
