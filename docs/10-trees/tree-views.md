# Left, right, top & bottom view

<span class="badge medium">Medium</span> <span class="badge tag">BFS</span> <span class="badge tag">Horizontal distance</span>

## Problem
Return the nodes visible when looking at the tree from each side.

```
            1                hd: −2 −1  0  1  2
          /   \
         2     3             left:   1 2 4 8
        / \   / \            right:  1 3 7 8
       4   5 6   7           top:    4 2 1 3 7
        \                    bottom: 4 8 6 3 7   (GfG rule: on a tie, the later/right node wins)
         8
```

## Thinking process
<div class="steps" markdown>

1. **Left and right views are per-level questions**, so use BFS: the **first** node of each level is the left view and the
   **last** is the right view.
2. **Recursive right view**: DFS visiting **right before left**. The first node you reach at each new depth is visible.
3. **Top and bottom views are per-column questions**. Give each node a **horizontal distance** (root 0, left child −1, right child +1).
    - **Top**: the **first** node BFS meets in each column (`putIfAbsent`). BFS meets shallower nodes first.
    - **Bottom**: the **last** node BFS meets in each column (`put` overwrites).
4. A `TreeMap<hd, value>` returns the columns sorted from left to right with no extra sort.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/trees/TreeViews.java"
```

## Complexity
O(n) for the left and right views. O(n log n) for the top and bottom views because of the `TreeMap`. Tracking the min and max
hd in a `HashMap` would make it O(n).

## Why BFS and not DFS for the top view?
A DFS can reach a deep node in a column **before** a shallower one in the same column, so it would need depth comparisons.
BFS guarantees level order for free.

## Related
Vertical order traversal (group every node by hd, then sort by (depth, value)) · Boundary traversal (left edge + leaves + reversed right edge).
