# Is the graph bipartite?

<span class="badge medium">Medium</span> <span class="badge tag">2-colouring</span> <span class="badge tag">Odd cycle</span> <span class="badge tag">Union-Find</span>

## Problem
An undirected graph is **bipartite** if its nodes can be split into two groups so that every edge goes **between** the groups.
Return whether it is. The graph may be **disconnected**.

## Examples
| Graph | Bipartite? | Why |
|-------|------------|-----|
| square 0-1-2-3-0 | yes | {0, 2} and {1, 3} |
| triangle 0-1-2-0 | no | odd cycle |
| path 0-1 plus triangle 2-3-4 | no | the second component has an odd cycle |
| a self-loop u-u | no | u would need to differ from itself |

## Clarifying questions
- Connected? Not necessarily, so start a traversal from every uncoloured node.
- Self-loops or parallel edges? A self-loop makes it non-bipartite. Parallel edges change nothing.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: try all 2ⁿ ways to assign the two sides. Only usable for tiny n (it is the test oracle).
2. **Greedy colouring has no choices to get wrong**: once one node of a connected component has a side, every neighbour *must*
   take the other side, then their neighbours the first side, and so on. So colour by BFS/DFS. If an edge ever joins two nodes of the same colour, give up.
3. **Why that is exact**: a conflict means you walked an **odd cycle**, and a graph is bipartite **iff it has no odd cycle** (König's theorem).
4. **Union-Find view**: all neighbours of u must be on the same side (the side opposite u). Union them together. If u is ever in the same set as one of its neighbours, the graph is not bipartite.

</div>

## Dry run (BFS) on the triangle 0-1-2
| visit | colour | neighbours |
|-------|--------|------------|
| 0 | + | 1 → −, 2 → − |
| 1 | − | 0 (+, fine), 2 (−, **same colour**) → **not bipartite** |

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/Bipartite.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Try all colourings | O(2ⁿ · E) | O(1) |
| BFS / DFS colouring | **O(V + E)** | O(V) |
| Union-Find | O(E · α(V)) | O(V) |

## Edge cases & pitfalls
- Running a single BFS from node 0 misses odd cycles in other components.
- A recursive DFS overflows on deep graphs. The DFS here uses an explicit stack.
- Isolated nodes are fine: give them any colour.

## Follow-ups
**Possible bipartition** ("can n people who dislike each other be split into two groups?") is exactly this problem. `twoColouring` returns the actual groups.

## Related
[BFS & DFS](bfs-dfs.md) · [Cycle detection](cycle-detection.md) · [Union-Find](union-find.md).
