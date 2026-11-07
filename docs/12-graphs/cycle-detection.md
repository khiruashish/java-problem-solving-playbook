# Detect a cycle (undirected & directed)

<span class="badge medium">Medium</span> <span class="badge tag">DFS</span> <span class="badge tag">Three colours</span> <span class="badge tag">Union-Find</span>

## Problem
Return true if the graph contains a cycle. The answer and the algorithm **differ** for undirected and directed graphs.

## Thinking process
<div class="steps" markdown>

1. **Undirected, DFS**: every edge appears twice (u→v and v→u). Going back to the node you **came from** isn't a cycle,
   so ignore the parent **once**. Any **other** already-visited neighbour closes a loop.
   (Ignoring the parent only once means a parallel edge u–v counts as a cycle, which is correct.)
2. **Undirected, Union-Find**: process the edges one at a time. If an edge joins two nodes that are **already connected**,
   it closes a cycle.
3. **Directed, the trap**: a plain "visited" flag gives **false positives**. In the diamond `0→1, 0→2, 1→2`, node 2 is reached
   twice, but there's no cycle.
4. **Directed, three colours**: WHITE = unvisited, GREY = **on the current DFS path**, BLACK = finished. Only an edge to a
   **GREY** node (a back edge) means a cycle.
5. **Directed, Kahn**: topological sort outputs every node **iff** the graph has no cycle.

</div>

## Diamond vs. cycle
```
  0 → 1          0 → 1
  ↓   ↓          ↑   ↓
  2 ←─┘          └─ 2
 no cycle         cycle (2 is GREY when 1 → 2 → 0 → … )
```

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/CycleDetection.java"
```

## Complexity
O(V + E) for the DFS and Kahn approaches, and O(E·α(V)) for Union-Find.

## Testing note
On 300 random graphs (including self-loops and parallel edges), DFS and Union-Find must agree for undirected graphs, and the
three-colour DFS and Kahn must agree for directed graphs.
