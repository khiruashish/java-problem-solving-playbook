# BFS & DFS

<span class="badge easy">Easy</span> <span class="badge tag">Queue</span> <span class="badge tag">Stack</span> <span class="badge tag">Recursion</span>

## Problem
Traverse a graph from a start node with breadth-first and depth-first search (recursive and iterative). Also compute the
fewest-edges distance to every node and count the connected components.

```
0 — 1
|   |       BFS from 0: 0 1 2 3 4      (by distance: 0 | 1 2 | 3 | 4)
2 — 3 — 4   DFS from 0: 0 1 3 2 4      (as deep as possible first)
            5 is isolated → 2 components
```

## Thinking process
<div class="steps" markdown>

1. **BFS** explores in **rings of distance**: all nodes 1 edge away, then 2, and so on. Use a FIFO **queue**. Because nodes are
   reached in distance order, BFS gives **shortest paths in unweighted graphs** for free.
2. **DFS** follows one path as deep as it can, then backtracks. Use **recursion** or a LIFO **stack**.
3. **Visited** prevents infinite loops on cycles. In BFS, mark nodes when **enqueuing**.
4. **Iterative DFS matching the recursive order**: push neighbours in **reverse** and mark nodes when **popping**. A node may
   sit on the stack more than once, and the visited check skips the extra copies.
5. **Components**: start a new traversal from every node that is still unvisited, and count the starts.

</div>

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/bfs.html"

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/BfsDfs.java"
```

## Complexity
O(V + E) time for every method. Space is O(V) for the visited array plus the queue or stack.

## BFS or DFS?
| Prefer BFS | Prefer DFS |
|------------|------------|
| shortest path (unweighted) | detecting cycles, topological order |
| level-by-level answers | exploring every path (backtracking) |
| a very deep graph (no recursion limit) | less memory on wide graphs |

## Testing note
The iterative DFS must produce **exactly** the recursive order on 200 random graphs, and BFS and DFS must reach the same set of nodes.
