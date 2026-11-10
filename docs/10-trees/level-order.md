# Level-order traversal

<span class="badge easy">Easy</span> <span class="badge tag">BFS</span> <span class="badge tag">DFS with depth</span>

## Problem
Return the values grouped by level, left to right. `[3,9,20,null,null,15,7]` → `[[3],[9,20],[15,7]]`.

## Thinking process
<div class="steps" markdown>

1. "Level by level" means **BFS** with a queue.
2. **Knowing where a level ends**: at the start of each round, `size = queue.size()` is exactly the number of nodes on the
   current level. Poll that many, and push their children for the next round.
3. **DFS alternative**: pre-order carrying `depth`. The first time you reach a new depth, open a new list. Left is visited
   before right, so the order within a level is still left to right.

</div>

## Dry run
| Round | Queue at start | size | Level output |
|-------|----------------|------|--------------|
| 1 | 3 | 1 | [3] |
| 2 | 9, 20 | 2 | [9, 20] |
| 3 | 15, 7 | 2 | [15, 7] |

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/level-order.html"

## Code
```java
--8<-- "src/main/java/com/playbook/trees/LevelOrder.java"
```
(The same class also contains [zig-zag order](zigzag-level-order.md).)

## Complexity
O(n) time. O(width) queue space for BFS (up to n/2 for the last level), O(h) stack for DFS.

## Family
Bottom-up level order (reverse at the end) · Average of levels · Largest value per level ·
[Views](tree-views.md) · Connect next-right pointers.
