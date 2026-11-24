# Flood fill

<span class="badge easy">Easy</span> <span class="badge tag">Grid DFS / BFS</span> <span class="badge tag">Implicit graph</span>

## Problem
This is the paint-bucket tool. Starting from pixel `(sr, sc)`, recolour every pixel **4-connected** to it that has the **same original colour**, then return the image.

## Example
```
1 1 1          2 2 2
1 1 0   →      2 2 0      start (1, 1), new colour 2
1 0 1          2 0 1      (the bottom-right 1 is not connected)
```

## Clarifying questions
- 4-connected or 8-connected? 4 here (up, down, left, right).
- Modify the input or return a copy? A copy here, and the tests check that the input is unchanged.
- What if the new colour equals the old one? Return the image unchanged. Naive recursion would loop forever here.

## Thinking process
<div class="steps" markdown>

1. **The grid is a graph**: pixels are nodes, and edges join same-coloured neighbours. "Everything connected to the start" is a single DFS/BFS.
2. **No visited array needed**: recolouring a pixel *is* the visited mark, because it no longer matches the old colour.
3. **That trick breaks when new == old**: nothing changes, so pixels never stop matching. Handle that case first.
4. **DFS vs BFS**: recursive DFS is the shortest to write, but a 1000 × 1000 snake-shaped region overflows the Java stack. BFS with a queue has no depth limit.

</div>

## Dry run (BFS) on the example
| dequeue | recolour neighbours |
|---------|---------------------|
| (1,1) | (0,1), (1,0) |
| (0,1) | (0,0), (0,2) |
| (1,0) | (2,0) |
| (0,0), (0,2), (2,0) | none left |

The pixel at (2,2) is never reached: its neighbours are 0s.

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/FloodFill.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Recursive DFS | O(m·n) | O(m·n) recursion in the worst case |
| BFS | O(m·n) | O(m·n) queue in the worst case |
| Union-Find labelling (test oracle) | O(m·n · α) | O(m·n) |

## Edge cases & pitfalls
- In BFS, mark (recolour) a pixel when you **enqueue** it, not when you dequeue it. Otherwise the same pixel can be queued several times.
- The new colour equals the old colour (see above).
- A 1 × 1 image.

## Related
[Number of islands](number-of-islands.md) (flood fill once per unvisited land cell) · [Rotting oranges](rotting-oranges.md) (multi-source BFS) · [Rat in a maze](../04-recursion/rat-in-a-maze.md).
