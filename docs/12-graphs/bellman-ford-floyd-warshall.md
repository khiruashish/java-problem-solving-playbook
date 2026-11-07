# Bellman-Ford & Floyd-Warshall

<span class="badge expert">Expert</span> <span class="badge tag">Negative weights</span> <span class="badge tag">DP</span> <span class="badge tag">All pairs</span>

## Problem
1. **Bellman-Ford**: shortest paths from one source when edges can be **negative**. Report a negative cycle if one is reachable.
2. **Floyd-Warshall**: shortest paths between **every pair** of nodes.

## Thinking process: Bellman-Ford
<div class="steps" markdown>

1. A shortest path without cycles has at most **V − 1 edges**.
2. Relax **every edge**, V − 1 times. After round k, every shortest path that uses ≤ k edges is correct, by induction.
3. **Negative cycle check**: run one more round. If anything still improves, a reachable negative cycle exists, and "shortest"
   is undefined (you could loop forever), so return `null`.
4. **Early exit**: if a round changes nothing, the distances are final.

</div>

## Thinking process: Floyd-Warshall
<div class="steps" markdown>

1. **DP over intermediate nodes**: `d[i][j]` = the shortest path from i to j using only nodes `0..k` in the middle.
2. Adding node k as a possible stop gives `d[i][j] = min(d[i][j], d[i][k] + d[k][j])`.
3. **k must be the outer loop**. Putting it inside is a classic bug, because then `d[i][k]` isn't final yet.
4. If `d[i][i] < 0` for some i, there's a negative cycle.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/BellmanFordFloydWarshall.java"
```

## Complexity & choice
| Algorithm | Time | Space | Negative edges | Detects negative cycles |
|-----------|------|-------|----------------|-------------------------|
| [Dijkstra](dijkstra.md) | O((V+E) log V) | O(V) | ❌ | ❌ |
| Bellman-Ford | O(V·E) | O(V) | ✅ | ✅ |
| Floyd-Warshall | O(V³) | O(V²) | ✅ | ✅ |

## Implementation detail worth mentioning
`INF = Long.MAX_VALUE / 4` instead of `MAX_VALUE`, so `INF + weight` can never overflow and wrap into a negative "shortcut".
Always skip relaxing from an unreachable node.

## Related
Cheapest flights within K stops (Bellman-Ford limited to K + 1 rounds) · Currency arbitrage (a negative cycle on `−log(rate)`) ·
Find the city with the fewest reachable neighbours (Floyd-Warshall).

## Testing note
On non-negative random graphs, Bellman-Ford and Floyd-Warshall must match Dijkstra exactly. On graphs with negative edges,
they must match each other whenever no negative cycle exists.
