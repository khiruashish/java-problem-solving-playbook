# Binary tree maximum path sum

<span class="badge expert">Expert</span> <span class="badge tag">Post-order</span> <span class="badge tag">Gain function</span>

## Problem
A path is any sequence of connected nodes, with no node repeated and at least one node. Return the maximum sum.
Values can be **negative**. `[−10,9,20,null,null,15,7]` → 42 (`15 → 20 → 7`).

## Thinking process
<div class="steps" markdown>

1. As in the [diameter](diameter.md) problem, every path has one **highest node** where it bends. At that node the sum is
   `node + (best chain going down-left) + (best chain going down-right)`.
2. Define **gain(n)** = the best sum of a chain that **starts at n and goes down one side**:
   `n.val + max(0, gain(left), gain(right))`.
3. **Drop negative chains** with `max(0, …)`: a chain is only worth taking if it adds something.
4. At each node, record the bending path in a global best. But **return only one side** to the parent, because a path that
   goes up can't use both children.
5. Initialise the best to `Integer.MIN_VALUE`, **not 0**. An all-negative tree must return its largest (least negative) node.

</div>

## Trace on `[−10, 9, 20, null, null, 15, 7]`
| Node | gainL | gainR | Bend here | Returns |
|------|-------|-------|-----------|---------|
| 9 | 0 | 0 | 9 | 9 |
| 15 | 0 | 0 | 15 | 15 |
| 7 | 0 | 0 | 7 | 7 |
| 20 | 15 | 7 | **42** | 35 |
| −10 | 9 | 35 | 34 | 25 |

## Code
```java
--8<-- "src/main/java/com/playbook/trees/MaxPathSum.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force (recompute chains at each bend) | O(n²) | O(h) |
| Gain function | O(n) | O(h) |

## Why it's "Expert"
Two values flow differently: the **bending** sum (recorded, but never returned) and the **one-sided** gain (returned).
Mixing them up, or initialising the best to 0, are the classic failures. The tests check both against brute force on 300
random trees with negative values.
