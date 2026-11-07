# Zig-zag level order

<span class="badge hard">Hard</span> <span class="badge tag">BFS</span> <span class="badge tag">Deque</span> <span class="badge tag">Two stacks</span>

## Problem
Level order, but alternate the direction: left to right, then right to left, and so on.
`[3,9,20,null,null,15,7]` → `[[3],[20,9],[15,7]]`.

## Thinking process
<div class="steps" markdown>

1. **Simplest correct idea**: a normal [level order](level-order.md), reversing every odd level. O(n), but it does extra work.
2. **Cleaner**: keep the BFS order unchanged, but build each level with a **deque**. On left-to-right levels use
   `addLast`, and on right-to-left levels use `addFirst`. No reversal needed.
3. **Two stacks (classic GfG)**: pop from the `current` stack and push children onto `next`, **left then right** on
   left-to-right levels and **right then left** otherwise. The LIFO order produces the zig-zag naturally. Swap the stacks at
   the end of each level.

</div>

## Two stacks, traced
| Level | current (pop order) | Children pushed onto next | Output |
|-------|---------------------|---------------------------|--------|
| 0 → | 3 | 9, 20 | [3] |
| 1 ← | 20, 9 | 20: 7, 15 · 9: none | [20, 9] |
| 2 → | 15, 7 | | [15, 7] |

## Code
The deque version (`zigzag`) and the two-stack version (`zigzagTwoStacks`) are both in `LevelOrder`:
```java
--8<-- "src/main/java/com/playbook/trees/LevelOrder.java"
```

## Complexity
O(n) time and O(width) space for both.

## Testing note
The two versions are compared with each other on 200 random trees.
