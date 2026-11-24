# Rat in a maze

<span class="badge hard">Hard</span> <span class="badge tag">Grid backtracking</span> <span class="badge tag">Visited marking</span>

## Problem
An n × n grid has `1` for open cells and `0` for walls. A rat starts at the top-left and must reach the bottom-right,
moving **D**own, **L**eft, **R**ight or **U**p, and never entering the same cell twice on one path. Return
**every** path as a string of moves, in lexicographic order.

## Example
```
1 0 0 0
1 1 0 1      →  ["DDRDRR", "DRDDRR"]
1 1 0 0
0 1 1 1
```

## Clarifying questions
- Can paths revisit a cell? No, they are **simple** paths. Otherwise there would be infinitely many.
- Start or end blocked? Then there are no paths.
- Only "does a path exist"? That is a much cheaper problem. See below.

## Thinking process
<div class="steps" markdown>

1. **Restate**: enumerate all simple paths in a grid graph. You need *all* of them, so search exhaustively: backtracking.
2. **Choose / explore / un-choose**: mark the current cell as on-path, try the four moves, then **unmark** it. Unmarking is what lets a
   different path use the same cell later.
3. **Free sorting**: try moves in alphabetical order (D, L, R, U). DFS then emits paths already sorted, with no sort at the end.
4. **Existence only?** A cell that can't reach the target from one path can't from another either, so mark cells visited
   **permanently** and never unmark. That is plain DFS/BFS: O(n²) instead of exponential.

</div>

## Dry run (first path)
| at | tries | goes |
|----|-------|------|
| (0,0) | D | (1,0) |
| (1,0) | D | (2,0) |
| (2,0) | D blocked by wall, L out of grid, R | (2,1) |
| (2,1) | D | (3,1) |
| (3,1) | D out, L wall, R | (3,2) → R → (3,3) ✓ `DDRDRR` |

Then it backtracks to (1,0) and tries R, which produces `DRDDRR`.

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/RatInMaze.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| All paths (backtracking) | O(3^(n²)) worst case: each cell has at most 3 unvisited neighbours | O(n²) |
| Count paths | same search, no strings | O(n²) |
| Path exists (DFS, permanent visited) | **O(n²)** | O(n²) |

## Edge cases & pitfalls
- Forgetting to unmark loses paths that share cells with an earlier path.
- Marking the target as visited before checking it. Check the base case first.
- A 1 × 1 open maze has exactly one path, the empty string.

## Related
[Word search](word-search.md) (the same mark/unmark on a grid) · [Number of islands](../12-graphs/number-of-islands.md) (permanent marking) · [Unique paths](../13-dynamic-programming/unique-paths.md) (only right/down moves, so DP counts them).
