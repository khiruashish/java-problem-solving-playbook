# Strongly connected components (Kosaraju & Tarjan)

<span class="badge expert">Expert</span> <span class="badge tag">Kosaraju</span> <span class="badge tag">Tarjan low-link</span> <span class="badge tag">Condensation</span>

## Problem
In a **directed** graph, a strongly connected component (SCC) is a maximal set of nodes where every node can reach every other.
Label every node with its component.

## Example
```
0 → 1 → 2 → 0      SCC A = {0, 1, 2}
        2 → 3
        3 → 4 → 3  SCC B = {3, 4}
5                  SCC C = {5}
```

## Clarifying questions
- Output format? Here, `comp[v]` is a component id. Different algorithms number components differently, so the tests compare **canonical** labels.
- Size? Linear time matters for 10⁵ nodes. Use iterative DFS to avoid stack overflow.

## Thinking process
<div class="steps" markdown>

1. **Definition-driven brute force**: u and v share an SCC iff `reach(u, v) && reach(v, u)`. One BFS per node: O(V · (V + E)).
2. **Collapse each SCC to one node** and you get a **DAG** (the *condensation*). Some SCC is a **source** in that DAG.
3. **Kosaraju's insight**: in a DFS, the node that **finishes last** lies in a source SCC. **Reverse every edge**, and that source SCC becomes a **sink**.
   A DFS started inside a sink can't leave it, so it visits exactly one SCC. Repeat in decreasing finish time.
4. **Tarjan's insight (one pass)**: give each node a discovery time `disc` and track `low`, the earliest `disc` reachable through the
   DFS subtree plus one back edge to a node **still on the stack**. If `low[u] == disc[u]`, nothing below u escapes above it, so u is the root of
   an SCC. Pop the stack down to u.

</div>

## Kosaraju on the example
| pass | what happens |
|------|--------------|
| 1: DFS on G from 0 | finish order: 4, 3, 2, 1, 0, then 5 |
| 2: reversed G, latest finish first | from 5: {5} · from 0: {0, 2, 1} (3 is not reachable backwards) · from 3: {3, 4} |

## Tarjan trace (start at 0)
| node | disc | low after exploring | root? |
|------|------|---------------------|-------|
| 0 | 0 | 0 | yes → pops {2, 1, 0}… |
| 1 | 1 | 0 (via 2 → 0) | no |
| 2 | 2 | 0 | no |
| 3 | 3 | 3 | yes → pops {4, 3} |
| 4 | 4 | 3 (back edge to 3) | no |

3 and 4 are popped as their own SCC **before** control returns to 2, so they are never swept into the {0, 1, 2} component when 0 is popped later.

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/StronglyConnectedComponents.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Reachability brute force | O(V · (V + E)) | O(V²) |
| Kosaraju (2 DFS + reversed graph) | **O(V + E)** | O(V + E) for the reversed graph |
| Tarjan (1 DFS) | **O(V + E)** | O(V) |

## Edge cases & pitfalls
- In Tarjan, a **cross edge** to a node that is already in a finished SCC (not on the stack) must be ignored. Using its `disc` merges two SCCs.
- In Kosaraju, the second pass must run on the **reversed** graph and in **decreasing** finish order. Get either wrong and the components leak into each other.
- Deep graphs: a 20,000-node cycle is tested with the iterative Kosaraju.

## Where it is used
2-SAT · "minimum edges to make the graph strongly connected" · deadlock detection · condensing a graph into a DAG and then running DP on it.

## Related
[Topological sort](topological-sort.md) (only for DAGs. SCCs are what you get when cycles are allowed) · [Cycle detection](cycle-detection.md) · [Course schedule](course-schedule.md).
