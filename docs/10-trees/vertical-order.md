# Vertical order traversal

<span class="badge hard">Hard</span> <span class="badge tag">BFS + column index</span> <span class="badge tag">TreeMap</span> <span class="badge tag">Tie-breaking</span>

## Problem
Give the root column 0. A left child is one column left (`col − 1`) and a right child one column right (`col + 1`).
Return the node values **column by column, from left to right**. Inside a column, list top to bottom.

Two common variants differ only in how they break ties when two nodes share the same row and column:

- **BFS order** (LeetCode 314, GfG): left before right, as BFS meets them.
- **Sorted** (LeetCode 987): the smaller **value** first.

## Example
```
        3
      /   \
     9     8          columns: -2 → [4], -1 → [9], 0 → [3, 0, 1], 1 → [8], 2 → [7]
    / \   / \
   4   0 1   7        (0 and 1 share row 2, column 0)
```

## Clarifying questions
- Which tie rule? Ask. It changes the answer for trees like `[1,2,3,4,6,5,7]`: BFS gives column 0 as `[1, 6, 5]`, sorted gives `[1, 5, 6]`.
- Output format: a list of columns, left to right.

## Thinking process
<div class="steps" markdown>

1. **Attach a column to every node** during the traversal: root 0, left −1, right +1.
2. **Group by column** in a `TreeMap`, so the columns come out sorted left to right.
3. **Pick the traversal that gives the right order inside a column.** BFS visits top to bottom and left to right, which is
   exactly "BFS order", so appending in BFS order is already correct.
4. **Why not plain DFS?** DFS goes deep first, so it can record a deep left node before a shallow node in the same column. Fix it by storing each node's
   **row** and sorting each column by (row, pre-order sequence). Pre-order visits nodes of the same row left to right.
5. **Sorted variant**: store (row, value) and sort each column by row, then value.

</div>

## Dry run (BFS)
| dequeue | col | column map after |
|---------|-----|------------------|
| 3 | 0 | {0: [3]} |
| 9 | −1 | {−1: [9], 0: [3]} |
| 8 | 1 | {−1: [9], 0: [3], 1: [8]} |
| 4 | −2 | … −2: [4] |
| 0 | 0 | 0: [3, 0] |
| 1 | 0 | 0: [3, 0, 1] |
| 7 | 2 | 2: [7] |

## Code
```java
--8<-- "src/main/java/com/playbook/trees/VerticalOrder.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| BFS + TreeMap | O(n log w), w = number of columns | O(n) |
| DFS + per-column sort | O(n log n) | O(n) |
| Sorted variant | O(n log n) | O(n) |

You can get O(n) for the BFS variant with a `HashMap` while tracking the min and max column, then reading columns min..max.

## Edge cases & pitfalls
- A DFS without row information gives the wrong order. The random tests compare DFS-with-row-sort against BFS on 800 trees.
- An empty tree → an empty list.

## Related
[Tree views](tree-views.md) (the top and bottom views are the first and last node of each vertical column) · [Level order](level-order.md).
