# 12 · Graphs

## Concept primer

- A graph is **V vertices and E edges**. It can be directed or undirected, weighted or unweighted, and may contain cycles.
- **Representation**: an adjacency **list** (`List<List<Integer>>`) takes O(V + E) space and is the default. An adjacency **matrix**
  takes O(V²) and suits dense graphs or Floyd-Warshall.
- Grids are graphs too: every cell is a vertex, and its up/down/left/right neighbours are its edges.

```java
--8<-- "src/main/java/com/playbook/graphs/Graph.java"
```

## Pick the algorithm from the question

| The question asks… | Algorithm | Cost |
|--------------------|-----------|------|
| reachability, components, flood fill | BFS / DFS / Union-Find | O(V + E) |
| fewest edges (unweighted shortest path) | **BFS** | O(V + E) |
| shortest path, weights ≥ 0 | **Dijkstra** | O((V + E) log V) |
| shortest path, negative weights | **Bellman-Ford** | O(V·E) |
| all-pairs shortest paths, small V | **Floyd-Warshall** | O(V³) |
| ordering with dependencies | **Topological sort** | O(V + E) |
| cheapest way to connect everything | **MST** (Kruskal / Prim) | O(E log E) |
| "are these two connected?", many times | **Union-Find** | ~O(1) each |

!!! warning "Always mark visited"
    Without a visited set, any cycle loops forever. In BFS, mark when **enqueuing**, not when dequeuing, or the same node
    gets queued many times.

## Problems (Easy → Expert)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [BFS & DFS](bfs-dfs.md) | <span class="badge easy">Easy</span> | Queue vs stack |
| 2 | [Number of islands](number-of-islands.md) | <span class="badge medium">Medium</span> | Grid flood fill |
| 3 | [Detect a cycle](cycle-detection.md) | <span class="badge medium">Medium</span> | Parent check / three colours |
| 4 | [Topological sort](topological-sort.md) | <span class="badge medium">Medium</span> | Kahn / DFS finish order |
| 5 | [Rotting oranges](rotting-oranges.md) | <span class="badge medium">Medium</span> | Multi-source BFS |
| 6 | [Course schedule](course-schedule.md) | <span class="badge hard">Hard</span> | Topological sort on prerequisites |
| 7 | [Dijkstra's shortest path](dijkstra.md) | <span class="badge hard">Hard</span> | Min-heap + relaxation |
| 8 | [Union-Find / number of provinces](union-find.md) | <span class="badge hard">Hard</span> | Path compression + rank |
| 9 | [Word ladder](word-ladder.md) | <span class="badge expert">Expert</span> | BFS on an implicit graph |
| 10 | [Minimum spanning tree](minimum-spanning-tree.md) | <span class="badge expert">Expert</span> | Kruskal / Prim |
| 11 | [Bellman-Ford & Floyd-Warshall](bellman-ford-floyd-warshall.md) | <span class="badge expert">Expert</span> | Relaxation rounds |
