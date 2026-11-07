# Number of islands

<span class="badge medium">Medium</span> <span class="badge tag">Grid DFS/BFS</span> <span class="badge tag">Union-Find</span>

## Problem
Count the groups of `'1'` cells connected up/down/left/right.
```
1 1 0 0 0
1 1 0 0 0      → 3 islands
0 0 1 0 0
0 0 0 1 1
```

## Thinking process
<div class="steps" markdown>

1. A grid is a graph: cells are nodes, and the four neighbours are edges. An island is a **connected component** of land.
2. Scan every cell. When you hit unvisited land, **count one island** and flood-fill it, marking the whole island visited so
   it's never counted again.
3. **DFS flood fill** is the shortest code. **BFS** avoids stack overflow on huge islands (a 1000×1000 all-land grid is 10⁶ frames deep).
4. **Union-Find**: start with `count = land cells`. Each union that merges two different sets decrements the count.
   It suits the follow-up where land is **added over time** (LeetCode 305, Number of Islands II).
5. **Don't destroy the input**: "sink" visited land on a **copy**, or keep a `visited` array. Mutating a caller's grid is a
   code-review red flag.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/NumberOfIslands.java"
```

## Complexity
O(R·C) time for all three. Space is O(R·C) in the worst case for the recursion, queue or DSU.

## Family
Max area of an island · Surrounded regions (flood fill from the border) · Pacific Atlantic water flow ·
Number of distinct islands (encode each island's shape) · [Rotting oranges](rotting-oranges.md).
