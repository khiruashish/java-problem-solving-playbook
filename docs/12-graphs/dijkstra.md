# Dijkstra's shortest path

<span class="badge hard">Hard</span> <span class="badge tag">Min-heap</span> <span class="badge tag">Greedy</span> <span class="badge tag">Relaxation</span>

## Problem
Shortest distance from `src` to every node in a directed graph with **non-negative** weights. Unreachable nodes report ∞.
| Edge | Weight |
|------|--------|
| 0 → 1 | 4 |
| 0 → 2 | 1 |
| 2 → 1 | 2 |
| 1 → 3 | 1 |
| 2 → 3 | 5 |

Distances from 0: **[0, 3, 1, 4]**. The cheapest way to 1 is 0 → 2 → 1 (cost 3), not the direct edge (cost 4).

## Thinking process
<div class="steps" markdown>

1. BFS fails here: fewest **edges** isn't the same as the smallest **weight**.
2. **Greedy insight**: the unfinished node with the **smallest tentative distance** is final, because every other route to it
   goes through nodes that are already farther away, and weights can't be negative.
3. **Relaxation**: once u is final, for each edge u → v with weight w, try `dist[u] + w < dist[v]`.
4. **Picking the closest node**: a linear scan costs O(V) per pick (O(V²) total, good for dense graphs). A **min-heap** costs
   O(log V) per operation.
5. **Lazy deletion**: `PriorityQueue` can't decrease a key. Push a new entry instead, and skip popped entries whose distance is
   stale (`d > dist[u]`).

</div>

## Trace (heap)
| Pop (dist, node) | Relax | dist after |
|------------------|-------|------------|
| (0, 0) | 1 → 4, 2 → 1 | [0, 4, 1, ∞] |
| (1, 2) | 1 → 3 ✅, 3 → 6 | [0, 3, 1, 6] |
| (3, 1) | 3 → 4 ✅ | [0, 3, 1, 4] |
| (4, 1) stale → skip | | |
| (4, 3) | | final |

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/Dijkstra.java"
```

## Complexity
| Version | Time | Best for |
|---------|------|----------|
| Array scan | O(V²) | dense graphs (E ≈ V²) |
| Binary heap | O((V + E) log V) | sparse graphs (most interviews) |

## Why negative weights break it
A node marked final could become cheaper later through a negative edge. The code rejects negative weights with an
exception. For those graphs, use [Bellman-Ford](bellman-ford-floyd-warshall.md).

## Testing note
On 300 random graphs, both Dijkstra versions, Bellman-Ford and Floyd-Warshall must produce **identical** distances.
